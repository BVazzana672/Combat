package com.etudes.combat.gameobjects;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.*;

import com.etudes.combat.main.Game;
import com.etudes.combat.utils.ResourceLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank {

    public static final int DIMENSION = 48;

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
    private int score;

    private boolean movingUp;
    private boolean movingDown;
    private boolean rotatingCw;
    private boolean rotatingCc;
    private boolean destroyed;

    private BufferedImage tankImage;

    private Game game;
    private Color tankColor;
    private Bullet bullet;
    private Map map;

    public Tank(int x, int y, Color tankColor, Map map, Game game) {
        this.x = x;
        this.y = y;
        this.tankColor = tankColor;
        this.game = game;
        this.map = map;

        movingUp = false;
        movingDown = false;
        rotatingCw = false;
        rotatingCc = false;
        destroyed = false;

        bullet = null;

        score = 0;

        if(tankColor == Color.BLUE) {
            tankImage = ResourceLoader.loadImage("images/BlueTank.png");
            upKey = VK_W;
            downKey = VK_S;
            cwKey = VK_D;
            ccKey = VK_A;
            fireKey = VK_SPACE;
            angle = 90;
        } else if(tankColor == Color.GREEN) {
            tankImage = ResourceLoader.loadImage("images/GreenTank.png");
            upKey = VK_UP;
            downKey = VK_DOWN;
            cwKey = VK_RIGHT;
            ccKey = VK_LEFT;
            fireKey = VK_ENTER;
            angle = -90;
        } else tankImage = null;

        vx = 0.0;
        vy = 0.0;
        rotVel = 0.0;
    }

    public void update() {
        if(!destroyed) {
            x += vx;
            y += vy;
            angle += rotVel;
            if (movingUp || movingDown) {
                updateVelocity();
            }

            if (bullet != null) bullet.update();

            // collision code
            double right = x + DIMENSION;
            double bottom = y + DIMENSION;
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (right > game.getWidth()) x = game.getWidth() - DIMENSION;
            if (bottom > game.getHeight()) y = game.getHeight() - DIMENSION;
        }
    }

    public void render(Graphics g) {

        if(!destroyed) {
            Graphics2D g2 = (Graphics2D) g;
            double r = toRadians(angle);
            g2.rotate(r, (DIMENSION / 2.0) + x, (DIMENSION / 2.0) + y);
            g2.drawImage(tankImage, (int) x, (int) y, game);
            g2.rotate(-r, (DIMENSION / 2.0) + x, (DIMENSION / 2.0) + y);

            if (bullet != null) bullet.render(g);
        }

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
        } else if(key == fireKey) {
            // bunch of complicated math stuff to get bullet to spawn in the middle of the tank
            double r = toRadians(angle);
            double bx = (x + (DIMENSION / 2.0) - Bullet.RADIUS) + cos(r);
            double by = (y + (DIMENSION / 2.0) - Bullet.RADIUS) + sin(r);
            double bvx = 2 * (sin(r));
            double bvy = 2 * (-cos(r));
            bullet = new Bullet(bx, by, bvx, bvy, tankColor, this, map, game);
        }
    }

    public void destroyBullet() {
        bullet = null;
    }

    public boolean checkBulletCollision(Tank tank) {
        // uses the called tank's bullet and checks its collision with the tank passed in
        if(bullet == null) {
            return false; // can't collide if there's no bullet
        } else {
            Shape tankShape = tank.getBounds();
            Rectangle bulletBounds = bullet.getBounds();
            return tankShape.intersects(bulletBounds);
        }
    }

    public boolean checkMapCollision(Map map) {
        // change position to check the next tick's collision
        x += vx;
        y += vy;
        angle += rotVel;
        Shape shape = getBounds();
        // goes through each rectangle in the map and checks collision
        for(Rectangle rect : map.getPieces()) {
            if(shape.intersects(rect)) {
                // undo position change since it was read-only
                x -= vx;
                y -= vy;
                angle -= rotVel;
                return true;
            }
        }
        x -= vx;
        y -= vy;
        angle -= rotVel;
        return false;
    }

    public Shape getBounds() {
        AffineTransform at = new AffineTransform();
        at.rotate(toRadians(angle), x + (DIMENSION / 2.0), y + (DIMENSION / 2.0));

        Rectangle rect = new Rectangle((int) x, (int) y, DIMENSION, DIMENSION);
        return at.createTransformedShape(rect); // create shape based on rect with rotations set in the affinetransform
    }

    public void destroy() {
        destroyed = true;
    }

    public void respawn(int x, int y, int angle) {
        destroyed = false;
        this.x = x;
        this.y = y;
        this.angle = angle;
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
            r += PI / 2; // pi divided by 2 is 90 degrees in radians
            double cos = cos(r);
            double sin = sin(r);
            vx = -cos;
            vy = sin;
        }

    }

    public String getScoreString() {
        return "" + score;
    }

    public void addScore() {
        score += 1;
    }

}
