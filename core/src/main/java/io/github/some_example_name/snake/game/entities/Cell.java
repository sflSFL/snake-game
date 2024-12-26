package io.github.some_example_name.snake.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.some_example_name.snake.game.Direction;

import static io.github.some_example_name.snake.game.runnable.GameInfo.SCALE;
//import static snake.game.runnable.GameInfo.SCALE;

public class Cell extends GameObject {

    public Cell(Sprite sprite, int x, int y) {
        super(sprite, x, y);
        setSize(SCALE, SCALE);
    }

    public void setDirection(Direction dir) {
        if (dir == Direction.UP) {
            sprite.setRotation(90);
            setPosition(x, y + SCALE);
        } else if (dir == Direction.DOWN) {
            sprite.setRotation(-90);
            setPosition(x, y - SCALE);
        } else if (dir == Direction.LEFT) {
            sprite.setRotation(180);
            setPosition(x - SCALE, y);
        } else if (dir == Direction.RIGHT) {
            sprite.setRotation(0);
            setPosition(x + SCALE, y);
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x= " + x +
                ", y= " + y +
                '}';
    }

    public Cell originCenter() {
        sprite.setOriginCenter();
        return this;
    }
}
