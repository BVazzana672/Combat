package com.etudes.combat.gameobjects;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.*;

import com.etudes.combat.main.Game;
import com.etudes.combat.utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank {

    private static final int DIMENSION = 48;

    private double x;
    private double y;
    private double vx;
    private double vy;
    private double angle;
    private double rotVel;

    private int upKey;
    private int downKey;
    private int cwKey;
    private int ccKey;
    private int fireKey;

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
            upKey = VK_W;
            downKey = VK_S;
            cwKey = VK_D;
            ccKey = VK_A;
            fireKey = VK_SPACE;
        } else if(tankColor == Color.GREEN) {
            tankImage = ResourceLoader.loadImage("images/GreenTank.png");
            upKey = VK_UP;
            downKey = VK_DOWN;
            cwKey = VK_RIGHT;
            ccKey = VK_LEFT;
            fireKey = VK_ENTER;
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
        g2.rotate(-r, (DIMENSION / 2.0) + x, (DIMENSION / 2.0) + y);

    }

    public void keyPressed(int key) {
        if(key == upKey) {
            movingUp = true;
            movingDown = false;
        } else if(key == downKey) {
            movingDown = true;
            movingUp = false;
        } else if(key == cwKey) {
            rotatingCw = true;
            rotatingCc = false;
            rotVel = 1;
        } else if(key == ccKey) {
            rotatingCc = true;
            rotatingCw = false;
            rotVel = -1;
        }
    }

    public void keyReleased(int key) {
        if(key == upKey && !movingDown) {
            movingUp = false;
            vy = 0;
            vx = 0;
        } else if(key == downKey && !movingUp) {
            movingDown = false;
            vy = 0;
            vx = 0;
        } else if(key == cwKey && !rotatingCc) {
            rotVel = 0;
            rotatingCw = false;
        } else if(key == ccKey && !rotatingCw) {
            rotatingCc = false;
            rotVel = 0;
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
