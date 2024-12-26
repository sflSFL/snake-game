package io.github.some_example_name.snake.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import io.github.some_example_name.snake.game.Main;

public class MenuScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture startButtonTexture;
    private boolean isClicked = false;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        startButtonTexture = new Texture("images/start.png");  // 假设你有一个名为 start_button.png 的按钮图片
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setColor(Color.WHITE);

        // 绘制标题文字
        font.draw(batch, "Welcome to Snake Game!", 200, 300);

        // 绘制按钮
        batch.draw(startButtonTexture, 100, 100);

        // 检测点击事件
        if (Gdx.input.isTouched() && !isClicked) {
            isClicked = true;
        }

        if (!Gdx.input.isTouched() && isClicked) {
            // 判断是否点击了按钮区域
            if (Gdx.input.getX() >= 100 && Gdx.input.getX() <= 400 && Gdx.input.getY() >= 100 && Gdx.input.getY() <= 200) {
                // 跳转到 GameStartScreen
                game.setScreen(new GameStartScreen(game));
            }
            isClicked = false;  // 重置点击状态
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        startButtonTexture.dispose();
        font.dispose();
    }
}
