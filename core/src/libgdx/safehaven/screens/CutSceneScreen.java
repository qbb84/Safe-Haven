package libgdx.safehaven.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import libgdx.safehaven.*;
import libgdx.safehaven.UI.AnimatedImage;
import libgdx.safehaven.audio.AudioObserver;
import libgdx.safehaven.battle.MonsterFactory;
import libgdx.safehaven.profile.ProfileManager;
import libgdx.safehaven.sfx.ScreenTransitionAction;
import libgdx.safehaven.sfx.ScreenTransitionActor;

public class CutSceneScreen extends MainGameScreen {
	private final SafeHaven _game;
	private final Stage _stage;
	private final Viewport _viewport;
	private final Stage _UIStage;
	private final Viewport _UIViewport;
	private final Dialog _messageBoxUI;
	private final Label _label;
	private final ScreenTransitionActor _transitionActor;
	private final Action _switchScreenAction;
	private final Action _setupScene01;
	private final Action _setupScene02;
	private final Action _setupScene03;
	private final Action _setupScene04;
	private final Action _setupScene05;
	private final AnimatedImage _animBlackSmith;
	private final AnimatedImage _animInnKeeper;
	private final AnimatedImage _animMage;
	private final AnimatedImage _animFire;
	private final AnimatedImage _animDemon;
	private Actor _followingActor;
	private boolean _isCameraFixed = true;
	private Action _introCutSceneAction;

	public CutSceneScreen(SafeHaven game) {
		super(game);

		_game = game;

		_viewport = new ScreenViewport(_camera);
		_stage = new Stage(_viewport);

		_UIViewport = new ScreenViewport(_hudCamera);
		_UIStage = new Stage(_UIViewport);

		_label = new Label("Test", Utility.STATUSUI_SKIN);
		_label.setWrap(true);

		_messageBoxUI = new Dialog("", Utility.STATUSUI_SKIN, "solidbackground");
		_messageBoxUI.setVisible(false);
		_messageBoxUI.getContentTable().add(_label).width(_stage.getWidth() / 2).pad(10, 10, 10, 0);
		_messageBoxUI.pack();
		_messageBoxUI.setPosition(_stage.getWidth() / 2 - _messageBoxUI.getWidth() / 2,
			_stage.getHeight() - _messageBoxUI.getHeight());

		_followingActor = new Actor();
		_followingActor.setPosition(0, 0);

		notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.MUSIC_INTRO_CUTSCENE);

		_animBlackSmith = getAnimatedImage(EntityFactory.EntityName.TOWN_BLACKSMITH);
		_animInnKeeper = getAnimatedImage(EntityFactory.EntityName.TOWN_INNKEEPER);
		_animMage = getAnimatedImage(EntityFactory.EntityName.TOWN_MAGE);
		_animFire = getAnimatedImage(EntityFactory.EntityName.FIRE);
		_animDemon = getAnimatedImage(MonsterFactory.MonsterEntityType.MONSTER042);

		//Actions
		_switchScreenAction = new RunnableAction() {
			@Override
			public void run() {
				_game.setScreen(_game.getScreenType(SafeHaven.ScreenType.MainMenu));
			}
		};

		_setupScene01 = new RunnableAction() {
			@Override
			public void run() {
				hideMessage();
				_mapMgr.loadMap(MapFactory.MapType.TOWN);
				_mapMgr.disableCurrentmapMusic();
				setCameraPosition(10, 16);

				_animBlackSmith.setVisible(true);
				_animInnKeeper.setVisible(true);
				_animMage.setVisible(true);

				_animBlackSmith.setPosition(10, 16);
				_animInnKeeper.setPosition(12, 15);
				_animMage.setPosition(11, 17);

				_animDemon.setVisible(false);
				_animFire.setVisible(false);
			}
		};

		_setupScene02 = new RunnableAction() {
			@Override
			public void run() {
				hideMessage();
				_mapMgr.loadMap(MapFactory.MapType.TOP_WORLD);
				_mapMgr.disableCurrentmapMusic();
				setCameraPosition(50, 30);

				_animBlackSmith.setPosition(50, 30);
				_animInnKeeper.setPosition(52, 30);
				_animMage.setPosition(50, 28);

				_animFire.setPosition(52, 28);
				_animFire.setVisible(true);
			}
		};

		_setupScene03 = new RunnableAction() {
			@Override
			public void run() {
				_animDemon.setPosition(52, 28);
				_animDemon.setVisible(true);
				hideMessage();
			}
		};

		_setupScene04 = new RunnableAction() {
			@Override
			public void run() {
				hideMessage();
				_animBlackSmith.setVisible(false);
				_animInnKeeper.setVisible(false);
				_animMage.setVisible(false);
				_animFire.setVisible(false);

				_mapMgr.loadMap(MapFactory.MapType.TOP_WORLD);
				_mapMgr.disableCurrentmapMusic();

				_animDemon.setVisible(true);
				_animDemon.setScale(1, 1);
				_animDemon.setSize(16 * Map.UNIT_SCALE, 16 * Map.UNIT_SCALE);
				_animDemon.setPosition(50, 40);

				followActor(_animDemon);
			}
		};

		_setupScene05 = new RunnableAction() {
			@Override
			public void run() {
				hideMessage();
				_animBlackSmith.setVisible(false);
				_animInnKeeper.setVisible(false);
				_animMage.setVisible(false);
				_animFire.setVisible(false);

				_mapMgr.loadMap(MapFactory.MapType.CASTLE_OF_DOOM);
				_mapMgr.disableCurrentmapMusic();
				followActor(_animDemon);

				_animDemon.setVisible(true);
				_animDemon.setPosition(15, 1);
			}
		};

		_transitionActor = new ScreenTransitionActor();

		//layout
		_stage.addActor(_animMage);
		_stage.addActor(_animBlackSmith);
		_stage.addActor(_animInnKeeper);
		_stage.addActor(_animFire);
		_stage.addActor(_animDemon);
		_stage.addActor(_transitionActor);

		_UIStage.addActor(_messageBoxUI);
	}

	private AnimatedImage getAnimatedImage(EntityFactory.EntityName entityName) {
		Entity entity = EntityFactory.getInstance().getEntityByName(entityName);
		return setEntityAnimation(entity);
	}

	private AnimatedImage getAnimatedImage(MonsterFactory.MonsterEntityType entityName) {
		Entity entity = MonsterFactory.getInstance().getMonster(entityName);
		return setEntityAnimation(entity);
	}

	public void hideMessage() {
		_messageBoxUI.setVisible(false);
	}

	public void setCameraPosition(float x, float y) {
		_camera.position.set(x, y, 0f);
		_isCameraFixed = true;
	}

	public void followActor(Actor actor) {
		_followingActor = actor;
		_isCameraFixed = false;
	}

	private AnimatedImage setEntityAnimation(Entity entity) {
		final AnimatedImage animEntity = new AnimatedImage();
		animEntity.setEntity(entity);
		animEntity.setSize(animEntity.getWidth() * Map.UNIT_SCALE, animEntity.getHeight() * Map.UNIT_SCALE);
		return animEntity;
	}

	@Override
	public void show() {
		_introCutSceneAction = getCutsceneAction();
		_stage.addAction(_introCutSceneAction);
		notify(AudioObserver.AudioCommand.MUSIC_STOP_ALL, AudioObserver.AudioTypeEvent.NONE);
		notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, AudioObserver.AudioTypeEvent.MUSIC_INTRO_CUTSCENE);
		ProfileManager.getInstance().removeAllObservers();
		if (_mapRenderer == null) {
			_mapRenderer = new OrthogonalTiledMapRenderer(_mapMgr.getCurrentTiledMap(), Map.UNIT_SCALE);
		}
	}

	private Action getCutsceneAction() {
		_setupScene01.reset();
		_setupScene02.reset();
		_setupScene03.reset();
		_setupScene04.reset();
		_setupScene05.reset();
		_switchScreenAction.reset();

		return Actions.sequence(
			Actions.addAction(_setupScene01),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_IN,
				3), _transitionActor),
			Actions.delay(3),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("BLACKSMITH: We have planned this long enough. The time is now! I have had " +
							"enough" +
							" " +
							"talk...");
					}
				}),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("MAGE: This is dark magic you fool. We must proceed with caution, or this could " +
							"end badly for all of us");
					}
				}),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("INNKEEPER: Both of you need to keep it down. If we get caught using black " +
							"magic," +
							" " +
							"we will all be hanged!");
					}
				}),
			Actions.delay(5),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT,
				3), _transitionActor),
			Actions.delay(3),
			Actions.addAction(_setupScene02),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_IN,
				3), _transitionActor),
			Actions.delay(3),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("BLACKSMITH: Now, let's get on with this. I don't like the cemeteries very much." +
							"." +
							".");
					}
				}
			),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("MAGE: I told you, we can't rush the spell. Bringing someone back to life isn't " +
							"simple!");
					}
				}
			),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("INNKEEPER: I know you loved your daughter, but this just isn't right...");
					}
				}
			),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("BLACKSMITH: You have never had a child of your own. You just don't understand!");
					}
				}
			),
			Actions.delay(7),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("MAGE: You both need to concentrate, wait...Oh no, something is wrong!!");
					}
				}
			),
			Actions.delay(7),
			Actions.addAction(_setupScene03),
			Actions.addAction(Actions.fadeOut(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.fadeIn(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.fadeOut(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.fadeIn(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.fadeOut(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.fadeIn(2), _animDemon),
			Actions.delay(2),
			Actions.addAction(Actions.scaleBy(40, 40, 5, Interpolation.linear), _animDemon),
			Actions.delay(5),
			Actions.addAction(Actions.moveBy(20, 0), _animDemon),
			Actions.delay(2),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("BLACKSMITH: What...What have we done...");
					}
				}
			),
			Actions.delay(3),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT,
				3), _transitionActor),
			Actions.delay(3),
			Actions.addAction(_setupScene04),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_IN,
				3), _transitionActor),
			Actions.addAction(Actions.moveTo(54, 65, 13, Interpolation.linear), _animDemon),
			Actions.delay(10),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT,
				3), _transitionActor),
			Actions.delay(3),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_IN,
				3), _transitionActor),
			Actions.addAction(_setupScene05),
			Actions.addAction(Actions.moveTo(15, 76, 15, Interpolation.linear), _animDemon),
			Actions.delay(15),
			Actions.run(
				new Runnable() {
					@Override
					public void run() {
						showMessage("DEMON: I will now send my legions of demons to destroy these sacks of meat!");
					}
				}
			),
			Actions.delay(5),
			Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT,
				3), _transitionActor),
			Actions.delay(5),
			Actions.after(_switchScreenAction)
		);

	}

	public void showMessage(String message) {
		_label.setText(message);
		_messageBoxUI.pack();
		_messageBoxUI.setVisible(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_mapRenderer.setView(_camera);

		_mapRenderer.getBatch().enableBlending();
		_mapRenderer.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		if (_mapMgr.hasMapChanged()) {
			_mapRenderer.setMap(_mapMgr.getCurrentTiledMap());
			_mapMgr.setMapChanged(false);
		}

		_mapRenderer.render();

		if (!_isCameraFixed) {
			_camera.position.set(_followingActor.getX(), _followingActor.getY(), 0f);
		}
		_camera.update();

		_UIStage.act(delta);
		_UIStage.draw();

		_stage.act(delta);
		_stage.draw();
	}

	@Override
	public void hide() {
		notify(AudioObserver.AudioCommand.MUSIC_STOP, AudioObserver.AudioTypeEvent.MUSIC_INTRO_CUTSCENE);
		ProfileManager.getInstance().removeAllObservers();
		Gdx.input.setInputProcessor(null);
	}


}
