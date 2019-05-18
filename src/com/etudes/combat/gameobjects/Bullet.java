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
    private Map map;

    Bullet(double x, double y, double vx, double vy, Color bulletColor, Tank tank, Map map, Game game) {
        this.x = x;
        this.y = y;
        this.bulletColor = bulletColor;
        this.tank = tank;
        this.game = game;
        this.map = map;
        // Getting velocities based on tank direction
        this.vx = 3 * vx;
        this.vy = 3 * vy;
    }

    void update() {
        // Collision
        final int centerX = (int)x + (int)RADIUS;
        final int centerY = (int)y + (int)RADIUS;
        for(Rectangle mapRect : map.getPieces()) {
            final int mapRight = mapRect.x + mapRect.width;
            final double mapBottom = mapRect.y + mapRect.height;

            if(getBounds().intersects(mapRect)) {
                // these if statements check for x and y collisions

                // if the center is between the two rectangle sides, it's likely a y collision
                if (centerX > mapRect.x && centerX < mapRight) {
                    vy = -vy;
                }
                // if the center is between the top and bottom, it's likely an x collision
                else if (centerY > mapRect.y && centerY < mapBottom) {
                    vx = -vx;
                }
                nCollisions++;
            }
        }

        if(nCollisions == 10) {
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
