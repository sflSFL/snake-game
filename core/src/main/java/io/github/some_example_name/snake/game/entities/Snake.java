package io.github.some_example_name.snake.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import io.github.some_example_name.snake.game.Direction;
import io.github.some_example_name.snake.game.asset.Asset;
import io.github.some_example_name.snake.game.runnable.GameInfo;

import static io.github.some_example_name.snake.game.runnable.GameInfo.*;
//mport static snake.game.runnable.GameInfo.*;

public class Snake {

    private static final int INITIAL_BODY_COUNT = 3;
    private static final int LIFE = 5;

    private LinkedList<Cell> snakeBody;
    private Stack<GameObject> lives;
    private TextureAtlas atlas;
    private Direction dir;
    private Cell head;
    private Cell tail;

    public Snake(TextureAtlas atlas) {
        this.dir = Direction.RIGHT;
        this.atlas = atlas;
        lives = new Stack<GameObject>();
        snakeBody = new LinkedList<Cell>();
        restoreHealth(true);
        init();
    }

    public void restoreHealth(boolean init) {
        //int j = 0;
        if (init){
            for (int i = 0; i < LIFE; i++){
                drawLifeSpan(i, true);
            }
        }else{
            for (int i = 0; i < newLife(countLife()); i++){
                drawLifeSpan(i, false);
            }
        }
    }

    private void init() {
        snakeBody.clear();
        for (int i = INITIAL_BODY_COUNT; i > 0; i--) {
            Cell body = new Cell(Asset.instance().getSprite(getBodyType(i)), SCALE * i, 0);
            snakeBody.add(body);
        }
        dir = Direction.RIGHT;
        head = snakeBody.getFirst().originCenter();
        tail = snakeBody.getLast().originCenter();

    }

    private String getBodyType(int index) {
        if (index == INITIAL_BODY_COUNT)
            return "snake_head";
        if (index == 0)
            return "snake_tail";
        else
            return "snake_body";
    }

    public void moveBody() {
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            Cell nextBody = snakeBody.get(i - 1);
            Cell body = snakeBody.get(i);
            body.setPosition(nextBody.getX(), nextBody.getY());
        }
        head.setDirection(dir);
        checkWallCollision();
    }

    public void handleEvents() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && dir != Direction.DOWN)
            dir = Direction.UP;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && dir != Direction.UP)
            dir = Direction.DOWN;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && dir != Direction.RIGHT)
            dir = Direction.LEFT;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && dir != Direction.LEFT)
            dir = Direction.RIGHT;
    }

    public void render(SpriteBatch batch) {
        for (Cell body : snakeBody) {
            body.draw(batch);
        }
        for (GameObject life : lives) {
            life.draw(batch);
        }
    }

    public boolean isCrash() {
        for (int i = 1; i < snakeBody.size(); i++) {
            if (head.isCollide(snakeBody.get(i)))
                return true;
        }
        return false;
    }

    private void checkWallCollision() {
        if (head.getY() > GameInfo.SCREEN_HEIGHT)
            head.setY(0);
        if (head.getY() < 0)
            head.setY(GameInfo.SCREEN_HEIGHT);
        if (head.getX() > GameInfo.SCREEN_WIDTH)
            head.setX(0);
        if (head.getX() < 0)
            head.setX(GameInfo.SCREEN_WIDTH);
    }

    public boolean isFoodTouch(GameObject food) {
        return this.snakeBody.getFirst().isCollide(food);
    }

    public void grow() {
        snakeBody.getLast().sprite.setRegion(atlas.getRegions().get(12));
        Cell body = new Cell(atlas.createSprite("snake_tail"), tail.getX(), tail.getY());
        snakeBody.add(body);
        tail = snakeBody.getLast().originCenter();
        System.out.println(snakeBody.size());
    }

    public boolean hasLive() {
        return !lives.isEmpty();
    }

    public LinkedList<Cell> getBody() {
        return snakeBody;
    }

    public void popLife() {
        lives.pop();
    }

    public void reset() {
        init();
    }

    public int countLife(){
        int count = 0;
        Iterator<GameObject> it = lives.iterator();
        while (it.hasNext()) {
            GameObject live = it.next();
            count++;
        }
        //System.out.println(count);
        return count;
    }

    public int newLife(int life){
        if (isCrash()){
            return life-1;
        }else
            return life;
    }

    private void drawLifeSpan(int i, boolean init){
        GameObject life = new GameObject(Asset.instance().getSprite("heart"));
        life.setSize(25, 25);
        life.setPosition((int) ((SCREEN_WIDTH - 25) - life.getWidth() * (i * 1.2f)),
                (int) (SCREEN_HEIGHT - life.getHeight() - 10));
        lives.add(life);
        if (!init){
            clearLife();
        }
    }

    public void clearLife(){
       popLife();
    }

    public boolean hasCoveredAllCoordinates() {
        // 创建一个二维数组，记录每个坐标是否被蛇头经过
        boolean[][] visited = new boolean[SCREEN_WIDTH][SCREEN_HEIGHT];

        // 遍历蛇头的每个位置，标记已访问
        int x = head.getX();
        int y = head.getY();

        if (x >= 0 && x < SCREEN_WIDTH && y >= 0 && y < SCREEN_HEIGHT) {
            visited[x][y] = true;
        }

        // 检查是否所有的坐标都被蛇头经过
        for (int i = 0; i < SCREEN_WIDTH; i++) {
            for (int j = 0; j < SCREEN_HEIGHT; j++) {
                if (!visited[i][j]) {
                    return false; // 如果有未经过的坐标，返回 false
                }
            }
        }

        return true; // 如果所有坐标都被蛇头经过，返回 true
    }
}
