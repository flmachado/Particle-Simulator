package com.me.gravity;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Gravity_simulator";
		cfg.useGL20 = false;
		cfg.width = 500;
		cfg.height = 800;
		
		new LwjglApplication(new Gravity(), cfg);
	}
}
