package libgdx.safehaven.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import libgdx.safehaven.Entity;
import libgdx.safehaven.EntityConfig;
import libgdx.safehaven.UI.InventoryObserver;
import libgdx.safehaven.profile.ProfileManager;

public class BattleState extends BattleSubject implements InventoryObserver {
	private static final String TAG = BattleState.class.getSimpleName();
	private final int _chanceOfAttack = 25;
	private final int _chanceOfEscape = 40;
	private final int _criticalChance = 90;
	private final Timer.Task _playerAttackCalculations;
	private final Timer.Task _opponentAttackCalculations;
	private final Timer.Task _checkPlayerMagicUse;
	private Entity _currentOpponent;
	private int _currentZoneLevel = 0;
	private int _currentPlayerAP;
	private int _currentPlayerDP;
	private int _currentPlayerWandAPPoints = 0;

	public BattleState() {
		_playerAttackCalculations = getPlayerAttackCalculationTimer();
		_opponentAttackCalculations = getOpponentAttackCalculationTimer();
		_checkPlayerMagicUse = getPlayerMagicUseCheckTimer();
	}

	private Timer.Task getPlayerAttackCalculationTimer() {
		return new Timer.Task() {
			@Override
			public void run() {
				int currentOpponentHP =
					Integer.parseInt(_currentOpponent.getEntityConfig().getPropertyValue(EntityConfig.EntityProperties.ENTITY_HEALTH_POINTS.toString()));
				int currentOpponentDP =
					Integer.parseInt(_currentOpponent.getEntityConfig().getPropertyValue(EntityConfig.EntityProperties.ENTITY_DEFENSE_POINTS.toString()));

				int damage = MathUtils.clamp(_currentPlayerAP - currentOpponentDP, 0, _currentPlayerAP);

				Gdx.app.debug(TAG, "ENEMY HAS " + currentOpponentHP + " hit with damage: " + damage);

				currentOpponentHP = MathUtils.clamp(currentOpponentHP - damage, 0, currentOpponentHP);
				_currentOpponent.getEntityConfig().setPropertyValue(EntityConfig.EntityProperties.ENTITY_HEALTH_POINTS.toString(), String.valueOf(currentOpponentHP));

				Gdx.app.debug(TAG, "Player attacks " + _currentOpponent.getEntityConfig().getEntityID() + " leaving " +
					"it" +
					" with HP: " + currentOpponentHP);

				_currentOpponent.getEntityConfig().setPropertyValue(EntityConfig.EntityProperties.ENTITY_HIT_DAMAGE_TOTAL.toString(), String.valueOf(damage));
				if (damage > 0) {
					BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.OPPONENT_HIT_DAMAGE);
				}

				if (currentOpponentHP == 0) {
					BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.OPPONENT_DEFEATED);
				}

				BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_TURN_DONE);
			}
		};
	}

	private Timer.Task getOpponentAttackCalculationTimer() {
		return new Timer.Task() {
			@Override
			public void run() {
				int currentOpponentHP =
					Integer.parseInt(_currentOpponent.getEntityConfig().getPropertyValue(EntityConfig.EntityProperties.ENTITY_HEALTH_POINTS.toString()));

				if (currentOpponentHP <= 0) {
					BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.OPPONENT_TURN_DONE);
					return;
				}

				int currentOpponentAP =
					Integer.parseInt(_currentOpponent.getEntityConfig().getPropertyValue(EntityConfig.EntityProperties.ENTITY_ATTACK_POINTS.toString()));
				int damage = MathUtils.clamp(currentOpponentAP - _currentPlayerDP, 0, currentOpponentAP);
				int hpVal = ProfileManager.getInstance().getProperty("currentPlayerHP", Integer.class);
				hpVal = MathUtils.clamp(hpVal - damage, 0, hpVal);
				ProfileManager.getInstance().setProperty("currentPlayerHP", hpVal);

				if (damage > 0) {
					BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_HIT_DAMAGE);
				}

				Gdx.app.debug(TAG,
					"Player HIT for " + damage + " BY " + _currentOpponent.getEntityConfig().getEntityID() + " " +
						"leaving" +
						" player with HP: " + hpVal);

				BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.OPPONENT_TURN_DONE);
			}
		};
	}

	private Timer.Task getPlayerMagicUseCheckTimer() {
		return new Timer.Task() {
			@Override
			public void run() {
				int mpVal = ProfileManager.getInstance().getProperty("currentPlayerMP", Integer.class);
				mpVal -= _currentPlayerWandAPPoints;
				ProfileManager.getInstance().setProperty("currentPlayerMP", mpVal);
				BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_USED_MAGIC);
			}
		};
	}

	public void resetDefaults() {
		Gdx.app.debug(TAG, "Resetting defaults...");
		_currentZoneLevel = 0;
		_currentPlayerAP = 0;
		_currentPlayerDP = 0;
		_currentPlayerWandAPPoints = 0;
		_playerAttackCalculations.cancel();
		_opponentAttackCalculations.cancel();
		_checkPlayerMagicUse.cancel();
	}

	public int getCurrentZoneLevel() {
		return _currentZoneLevel;
	}

	public void setCurrentZoneLevel(int zoneLevel) {
		_currentZoneLevel = zoneLevel;
	}

	public boolean isOpponentReady() {
		if (_currentZoneLevel == 0) return false;
		int randomVal = MathUtils.random(1, 100);

		//Gdx.app.debug(TAG, "CHANGE OF ATTACK: " + _chanceOfAttack + " randomval: " + randomVal);

		if (_chanceOfAttack > randomVal) {
			setCurrentOpponent();
			return true;
		} else {
			return false;
		}
	}

	public void setCurrentOpponent() {
		Gdx.app.debug(TAG, "Entered BATTLE ZONE: " + _currentZoneLevel);
		Entity entity = MonsterFactory.getInstance().getRandomMonster(_currentZoneLevel);
		if (entity == null) return;
		this._currentOpponent = entity;
		notify(entity, BattleObserver.BattleEvent.OPPONENT_ADDED);
	}

	public void playerAttacks() {
		if (_currentOpponent == null) {
			return;
		}

		//Check for magic if used in attack; If we don't have enough MP, then return
		int mpVal = ProfileManager.getInstance().getProperty("currentPlayerMP", Integer.class);
		notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_TURN_START);

		if (_currentPlayerWandAPPoints == 0) {
			if (!_playerAttackCalculations.isScheduled()) {
				Timer.schedule(_playerAttackCalculations, 1);
			}
		} else if (_currentPlayerWandAPPoints > mpVal) {
			BattleState.this.notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_TURN_DONE);
		} else {
			if (!_checkPlayerMagicUse.isScheduled() && !_playerAttackCalculations.isScheduled()) {
				Timer.schedule(_checkPlayerMagicUse, .5f);
				Timer.schedule(_playerAttackCalculations, 1);
			}
		}
	}

	public void playerRuns() {
		int randomVal = MathUtils.random(1, 100);
		if (_chanceOfEscape > randomVal) {
			notify(_currentOpponent, BattleObserver.BattleEvent.PLAYER_RUNNING);
		} else if (randomVal > _criticalChance) {
			opponentAttacks();
		} else {
		}
	}

	public void opponentAttacks() {
		if (_currentOpponent == null) {
			return;
		}

		if (!_opponentAttackCalculations.isScheduled()) {
			Timer.schedule(_opponentAttackCalculations, 1);
		}
	}

	@Override
	public void onNotify(String value, InventoryEvent event) {
		switch (event) {
			case UPDATED_AP:
				int apVal = Integer.valueOf(value);
				_currentPlayerAP = apVal;
				//Gdx.app.debug(TAG, "APVAL: " + _currentPlayerAP);
				break;
			case UPDATED_DP:
				int dpVal = Integer.valueOf(value);
				_currentPlayerDP = dpVal;
				//Gdx.app.debug(TAG, "DPVAL: " + _currentPlayerDP);
				break;
			case ADD_WAND_AP:
				int wandAP = Integer.valueOf(value);
				_currentPlayerWandAPPoints += wandAP;
				Gdx.app.debug(TAG, "WandAP: " + _currentPlayerWandAPPoints);
				break;
			case REMOVE_WAND_AP:
				int removeWandAP = Integer.valueOf(value);
				_currentPlayerWandAPPoints -= removeWandAP;
				Gdx.app.debug(TAG, "WandAP: " + _currentPlayerWandAPPoints);
				break;
			default:
				break;
		}
	}
}
