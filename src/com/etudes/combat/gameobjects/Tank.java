package com.etudes.combat.gameobjects;

import com.etudes.combat.main.Game;

import java.awt.*;

public class Tank {

    private int x;
    private int y;
    private static final int DIMENSION = 48;
    private int vx;
    private int vy;
    private int rotVel;

    private Game game;

    public Tank(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;

        vx = 0;
        vy = 0;
        rotVel = 0;
    }

    public void update() {

    }

    public void render(Graphics g) {

    }

}
