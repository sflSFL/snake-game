package io.github.some_example_name.snake.game.runnable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.snake.game.Scorer;
import io.github.some_example_name.snake.game.asset.Asset;
import io.github.some_example_name.snake.game.asset.SoundPlayer;
import io.github.some_example_name.snake.game.entities.Board;
import io.github.some_example_name.snake.game.entities.GameObject;
import io.github.some_example_name.snake.game.entities.Snake;
//import snake.game.screen.EasterEggScreen;

public class SnakeGame {

    private static final int WIDTH = Gdx.graphics.getWidth();
    private static final int HEIGHT = Gdx.graphics.getHeight();

    private Board board;
    private Snake snake;
    private float timeState;
    private BitmapFont font;

    private GameObject food;
    private boolean isGameOver;

    private boolean isEasterEgg;// 用来控制是否显示彩蛋页面
    private Texture easterEggImage;

    public SnakeGame() {
        TextureAtlas atlas = Asset.instance().get(Asset.SNAKE_PACK);
        snake = new Snake(atlas);
        board = new Board(snake, WIDTH, HEIGHT);
        food = board.generateFood();
        font = new BitmapFont();
        init();
    }

    private void init() {
        SoundPlayer.init();
        SoundPlayer.playMusic(Asset.MEMO_SOUND, true);
    }

    public void update(float delta) {
        if (snake.hasLive()) {
            timeState += delta;
            snake.handleEvents();
            if (timeState >= .09f) {
                snake.moveBody();
                timeState = 0;
            }
            if (snake.isCrash()) {
                snake.reset();
                snake.popLife();
                snake.restoreHealth(false);
                SoundPlayer.playSound(Asset.CRASH_SOUND, false);
            }
            if (snake.isFoodTouch(food)) {
                SoundPlayer.playSound(Asset.EAT_FOOD_SOUND, false);
                Scorer.score();
                snake.grow();
                food = board.generateFood();
            }
        } else {
            gameOver();
            if (snake.hasCoveredAllCoordinates()) {
                isEasterEgg = true; // 激活彩蛋页面
                System.out.println("surprise");
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                start();
        }
    }

    private void gameOver() {
        isGameOver = true;
        SoundPlayer.stopMusic(Asset.MEMO_SOUND);
        SoundPlayer.playMusic(Asset.GAME_OVER_SOUND, false);
    }

    private void start() {
        SoundPlayer.playMusic(Asset.MEMO_SOUND, false);
        SoundPlayer.stopMusic(Asset.GAME_OVER_SOUND);

        isGameOver = false;
        snake.reset();
        snake.restoreHealth(true);
        food = board.generateFood();
        Scorer.reset();
    }

    public void render(SpriteBatch batch) {
        if (isEasterEgg) {
            renderEasterEgg(batch);
        } else {
            board.render(batch);
            food.draw(batch);
            snake.render(batch);

            font.setColor(Color.WHITE);

            if (isGameOver) {
                font.draw(batch, "GAME OVER", (WIDTH - 100) / 2, (HEIGHT + 100) / 2);
                font.draw(batch, "Press SPACE key to continue", (WIDTH - 200) / 2, (HEIGHT + 50) / 2);
            }

            font.draw(batch, "Score: " + Scorer.getScore(), GameInfo.SCALE / 2,
                    GameInfo.SCREEN_HEIGHT - 10);
            font.draw(batch, "Size: " + snake.getBody().size(), GameInfo.SCALE / 2,
                    GameInfo.SCREEN_HEIGHT - 40);
        }
    }

    // 显示彩蛋页面的渲染方法
    private void renderEasterEgg(SpriteBatch batch) {
        init_pic();

        // 渲染文字，确保文字显示在图片之前
        font.setColor(Color.GOLD);
        font.draw(batch, "Congratulations! You found the Easter Egg!", (WIDTH - 300) / 2, HEIGHT / 2);

        font.setColor(Color.WHITE);
        font.draw(batch, "Press SPACE to return to the game", (WIDTH - 250) / 2, HEIGHT / 2 - 30);

        // 获取图片的宽度和高度
        float imageWidth = easterEggImage.getWidth();
        float imageHeight = easterEggImage.getHeight();

        // 计算图片的居中位置
        float x = (WIDTH - imageWidth) / 2;
        float y = (HEIGHT - imageHeight) / 2;

        // 渲染图片，确保文字在图片之前被绘制
        batch.draw(easterEggImage, x, y);

        // 检查 SPACE 键按下事件
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isEasterEgg = false;  // 返回游戏
        }
    }


    public void init_pic() {
        // 在初始化时加载图片
        easterEggImage = new Texture("images/christmas.png");  // 加载图片，确保图片路径正确
    }

}
