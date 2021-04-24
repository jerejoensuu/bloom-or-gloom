package fi.tuni.tamk.tiko.depressionaut;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tuni.tamk.tiko.depressionaut.Screens.GameScreen;
import fi.tuni.tamk.tiko.depressionaut.Screens.SplashScreen;

public class MyGdxGame extends Game {
	// DEBUG ->
	public static final boolean DEBUG = false;
	// <- DEBUG

	public SpriteBatch gameBatch;
	public SpriteBatch hudBatch;
	public Navigation navigation;
	public ScoreCounter score;
	public Inventory inventory;

	public final static float SCREEN_WIDTH = 1080;
	public final static float SCREEN_HEIGHT = 1920;

	public OrthographicCamera camera;
	public Preferences prefs;
	public Items items;

	@Override
	public void create () {
		if(DEBUG) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
		}

		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		prefs = Gdx.app.getPreferences("general");
		score = new ScoreCounter();
		navigation = new Navigation(this);
		inventory = new Inventory();
		items = new Items();
		//items.preloadTextures(inventory);

		if(DEBUG) {
			navigation.setActive(Navigation.Screen.GAME);
			setScreen(new GameScreen(this));
		} else {
			setScreen(new SplashScreen(this));
		}
	}

	@Override
	public void render () {
		hudBatch.setProjectionMatrix(camera.combined);
		// Uses the render method from current screen.
		super.render();

		hudBatch.begin();
		navigation.draw(hudBatch);
		hudBatch.end();
	}
	
	@Override
	public void dispose () {
		gameBatch.dispose();
		hudBatch.dispose();
	}
}
