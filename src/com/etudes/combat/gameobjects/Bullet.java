package com.etudes.combat.gameobjects;

import com.etudes.combat.main.Game;

import java.awt.*;

class Bullet {

    static final double RADIUS = 5.0;

    private double x;
    private double y;
    private double vx;
    private double vy;

    private int nCollisions;

    private Game game;
    private Tank tank;
    private Color bulletColor;

    Bullet(double x, double y, double vx, double vy, Color bulletColor, Tank tank, Game game) {
        this.x = x;
        this.y = y;
        this.bulletColor = bulletColor;
        this.tank = tank;
        this.game = game;
        // Getting velocities based on tank direction
        this.vx = 3 * vx;
        this.vy = 3 * vy;
    }

    void update() {
        // Collision
        final double right = x + (RADIUS * 2);
        final double bottom = y + (RADIUS * 2);
        if(x <= 0) {
            vx = -vx;
            x = 0;
            nCollisions++;
        } else if(right >= game.getWidth()) {
            vx = -vx;
            x = game.getWidth() - (RADIUS * 2);
            nCollisions++;
        }
        if(y <= 0) {
            vy = -vy;
            y = 0;
            nCollisions++;
        } else if(bottom >= game.getHeight()) {
            vy = -vy;
            y = game.getHeight() - (RADIUS * 2);
            nCollisions++;
        }

        if(nCollisions == 5) {
            tank.destroyBullet();
        }

        x += vx;
        y += vy;
    }

    Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)(RADIUS * 2), (int)(RADIUS * 2));
    }

    void render(Graphics g) {
        g.setColor(bulletColor);
        g.fillArc((int) x, (int) y, (int) RADIUS * 2, (int) RADIUS * 2, 0, 360);
    }

}
