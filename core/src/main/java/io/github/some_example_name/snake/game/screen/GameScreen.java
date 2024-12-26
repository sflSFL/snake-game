package io.github.some_example_name.snake.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.snake.game.Main;
import io.github.some_example_name.snake.game.runnable.SnakeGame;

public class GameScreen implements com.badlogic.gdx.Screen {
    private Main game;
    private SpriteBatch batch;
    private SnakeGame snakeGame;

    public GameScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.snakeGame = new SnakeGame();  // 创建一个新的 SnakeGame 实例，传入 Main
    }


    @Override
    public void show() {
        // 在这里进行屏幕的初始化工作，如果有的话
    }

    @Override
    public void render(float delta) {
        clearScreen();

        batch.begin();

        snakeGame.render(batch);  // 渲染游戏
        snakeGame.update(delta);  // 更新游戏状态

        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // 处理屏幕大小变化
    }

    @Override
    public void hide() {
        // 处理屏幕隐藏时的逻辑
    }

    @Override
    public void pause() {
        // 游戏暂停时的逻辑
    }

    @Override
    public void resume() {
        // 游戏恢复时的逻辑
    }

    @Override
    public void dispose() {
        // 释放资源
        //snakeGame.dispose();
    }
}
