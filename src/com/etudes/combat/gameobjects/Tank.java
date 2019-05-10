package com.etudes.combat.gameobjects;

import com.etudes.combat.main.Game;
import com.etudes.combat.utils.ResourceLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank {

    private int x;
    private int y;
    private static final int DIMENSION = 48;
    private int vx;
    private int vy;
    private double angle;
    private double rotVel;

    private BufferedImage tankImage;

    private Game game;
    private Color tankColor;

    public Tank(int x, int y, Color tankColor, Game game) {
        this.x = x;
        this.y = y;
        this.tankColor = tankColor;
        this.game = game;

        if(tankColor == Color.BLUE) {
            tankImage = ResourceLoader.loadImage("images/BlueTank.png");
        } else if(tankColor == Color.GREEN) {
            tankImage = ResourceLoader.loadImage("images/GreenTank.png");
        } else tankImage = null;

        vx = 0;
        vy = 0;
        angle = 0;
        rotVel = 0;
    }

    public void update() {
        x += vx;
        y += vy;
        angle += rotVel;
    }

    public void render(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        double r = Math.toRadians(angle);
        g2.rotate(r, (DIMENSION / 2) + x, (DIMENSION / 2) + y);
        g2.drawImage(tankImage, x, y, game);

    }

}
