package libgdx.safehaven.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import libgdx.safehaven.SafeHaven;
import libgdx.safehaven.Utility;
import libgdx.safehaven.audio.AudioObserver;

public class GameOverScreen extends GameScreen {
	private static final String DEATH_MESSAGE = "You have fought bravely, but alas, you have fallen during your epic" +
		" " +
		"struggle.";
	private static final String GAMEOVER = "Game Over";
	private final Stage _stage;
	private final SafeHaven _game;

	public GameOverScreen(SafeHaven game) {
		_game = game;

		//create
		_stage = new Stage();
		TextButton continueButton = new TextButton("Continue", Utility.STATUSUI_SKIN);
		TextButton mainMenuButton = new TextButton("Main Menu", Utility.STATUSUI_SKIN);
		Label messageLabel = new Label(DEATH_MESSAGE, Utility.STATUSUI_SKIN);
		messageLabel.setWrap(true);

		Label gameOverLabel = new Label(GAMEOVER, Utility.STATUSUI_SKIN);
		gameOverLabel.setAlignment(Align.center);

		Table table = new Table();

		//Layout
		table.setFillParent(true);
		table.add(messageLabel).pad(50, 50, 50, 50).expandX().fillX().row();
		table.add(gameOverLabel);
		table.row();
		table.add(continueButton).pad(50, 50, 10, 50);
		table.row();
		table.add(mainMenuButton).pad(10, 50, 50, 50);

		_stage.addActor(table);

		//Listeners
		continueButton.addListener(new ClickListener() {
									   @Override
									   public boolean touchDown(InputEvent event, float x, float y, int pointer,
																int button) {
										   return true;
									   }

									   @Override
									   public void touchUp(InputEvent event, float x, float y, int pointer,
														   int button) {
										   _game.setScreen(_game.getScreenType(SafeHaven.ScreenType.LoadGame));
									   }

								   }
		);

		mainMenuButton.addListener(new ClickListener() {

									   @Override
									   public boolean touchDown(InputEvent event, float x, float y, int pointer,
																int button) {
										   return true;
									   }

									   @Override
									   public void touchUp(InputEvent event, float x, float y, int pointer,
														   int button) {
										   _game.setScreen(_game.getScreenType(SafeHaven.ScreenType.MainMenu));
									   }
								   }
		);

		notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.MUSIC_TITLE);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(_stage);
		notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, AudioObserver.AudioTypeEvent.MUSIC_TITLE);
	}

	@Override
	public void render(float delta) {
		if (delta == 0) {
			return;
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		_stage.act(delta);
		_stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		_stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		_stage.clear();
		_stage.dispose();
	}
}
