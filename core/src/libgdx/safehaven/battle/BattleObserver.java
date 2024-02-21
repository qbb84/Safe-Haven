package libgdx.safehaven.battle;

import libgdx.safehaven.Entity;

public interface BattleObserver {
	void onNotify(final Entity enemyEntity, BattleEvent event);

	enum BattleEvent {
		OPPONENT_ADDED,
		OPPONENT_HIT_DAMAGE,
		OPPONENT_DEFEATED,
		OPPONENT_TURN_DONE,
		PLAYER_HIT_DAMAGE,
		PLAYER_RUNNING,
		PLAYER_TURN_DONE,
		PLAYER_TURN_START,
		PLAYER_USED_MAGIC,
		NONE
	}
}
