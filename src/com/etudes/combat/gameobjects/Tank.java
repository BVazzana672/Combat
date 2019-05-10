package com.etudes.combat.gameobjects;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.*;

import com.etudes.combat.main.Game;
import com.etudes.combat.utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank {

    private double x;
    private double y;
    private static final int DIMENSION = 48;
    private double vx;
    private double vy;
    private double angle;
    private double rotVel;

    private boolean movingUp;
    private boolean movingDown;
    private boolean rotatingCw;
    private boolean rotatingCc;

    private BufferedImage tankImage;

    private Game game;
    private Color tankColor;

    public Tank(int x, int y, Color tankColor, Game game) {
        this.x = x;
        this.y = y;
        this.tankColor = tankColor;
        this.game = game;

        movingUp = false;
        movingDown = false;
        rotatingCw = false;
        rotatingCc = false;

        if(tankColor == Color.BLUE) {
            tankImage = ResourceLoader.loadImage("images/BlueTank.png");
        } else if(tankColor == Color.GREEN) {
            tankImage = ResourceLoader.loadImage("images/GreenTank.png");
        } else tankImage = null;

        vx = 0.0;
        vy = 0.0;
        angle = 0.0;
        rotVel = 0.0;
    }

    public void update() {
        x += vx;
        y += vy;
        angle += rotVel;
        if(movingUp || movingDown) {
            updateVelocity();
        }
    }

    public void render(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        double r = toRadians(angle);
        g2.rotate(r, (DIMENSION / 2.0) + x, (DIMENSION / 2.0) + y);
        g2.drawImage(tankImage, (int) x, (int) y, game);

    }

    public void keyPressed(int key) {
        if(key == VK_W) {
            movingUp = true;
            movingDown = false;
        } else if(key == VK_S) {
            movingDown = true;
            movingUp = false;
        } else if(key == VK_D) {
            rotatingCw = true;
            rotVel = 1;
        } else if(key == VK_A) {
            rotVel = -1;
            rotatingCc = true;
        }
    }

    public void keyReleased(int key) {
        if(key == VK_W && !movingDown) {
            movingUp = false;
            vy = 0;
            vx = 0;
        } else if(key == VK_S && !movingUp) {
            movingDown = false;
            vy = 0;
            vx = 0;
        } else if(key == VK_A && !rotatingCw) {
            rotatingCc = false;
            rotVel = 0;
        } else if(key == VK_D && !rotatingCc) {
            rotVel = 0;
            rotatingCw = false;
        }
    }

    public void updateVelocity() {

        if(movingUp) {
            double r = toRadians(angle);
            r = -r;
            r += PI / 2;
            double cos = cos(r);
            double sin = sin(r);
            vx = cos;
            vy = -sin;
        }
        if(movingDown) {
            double r = toRadians(angle);
            r = -r;
            r += PI / 2;
            double cos = cos(r);
            double sin = sin(r);
            vx = -cos;
            vy = sin;
        }

    }

}
