package io.github.some_example_name.snake.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.snake.game.runnable.GameInfo;

public class GameObject {

    protected Sprite sprite;
    protected int x;
    protected int y;

    public GameObject(Sprite sprite, int x, int y) {
        this.sprite = sprite;
        setPosition(x, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
        setSize(GameInfo.SCALE, GameInfo.SCALE);
    }

    public void setRotation(float degree) {
        sprite.setRotation(degree);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
    }

    public void setColor(Color color) {
        sprite.setColor(color.r, color.g, color.b, 255);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public boolean isCollide(GameObject object) {
        return x < object.x + object.getWidth() &&
                x + getWidth() > object.x &&
                y < object.y + object.getHeight() &&
                y + getHeight() > object.y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public void setRotation(int rotation) {
        sprite.setRotation(rotation);
    }
}
