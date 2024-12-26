package io.github.some_example_name.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import io.github.some_example_name.snake.game.Main;

/** Launches the GWT application. */


// 这是你的主游戏类


public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig () {
        // 设置窗口大小，您可以根据需要调整
        return new GwtApplicationConfiguration(800, 600);
    }

    @Override
    public com.badlogic.gdx.ApplicationListener createApplicationListener () {
        // 返回 Main 类的实例，用于启动游戏
        return new Main();
    }
}
