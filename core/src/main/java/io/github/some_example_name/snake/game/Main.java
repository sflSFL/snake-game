package io.github.some_example_name.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import snake.game.asset.Asset;
import io.github.some_example_name.snake.game.asset.Asset;
import io.github.some_example_name.snake.game.screen.MenuScreen;

public class Main extends Game {
	private SpriteBatch batch;

	@Override
	public void create() {
		Asset.instance().loadAsset();
		batch = new SpriteBatch();

		// 设置初始屏幕为 GameScreen
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		Asset.instance().dispose();
	}
}

