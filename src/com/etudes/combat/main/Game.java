package com.etudes.combat.main;

import com.etudes.combat.gameobjects.Map;
import com.etudes.combat.gameobjects.Tank;
import com.etudes.combat.input.KeyInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Combat";

    private boolean running;
    private Thread thread;

    private Tank blueTank;
    private Tank greenTank;

    private Map map;

    public Game() {
        running = false;
        setSize(new Dimension(WIDTH, HEIGHT));
        new Window(TITLE, this);
        addKeyListener(new KeyInput(this));

        int tankY = (HEIGHT / 2) - (Tank.DIMENSION / 2) + 30;
        blueTank = new Tank(60, tankY, Color.BLUE, this);
        greenTank = new Tank(WIDTH - 60 - Tank.DIMENSION, tankY, Color.GREEN, this);

        map = new Map(this);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        int frames = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                delta--;
            }

            if(running) {
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                //System.out.println("[FPS] " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public void update() {

        greenTank.update();
        blueTank.update();

        if(greenTank.checkBulletCollision(blueTank)) {
            greenTank.destroyBullet();
            blueTank.destroy();
        }
        if(blueTank.checkBulletCollision(greenTank)) {
            blueTank.destroyBullet();
            greenTank.destroy();
        }

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.PINK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        greenTank.render(g);
        blueTank.render(g);

        g.setColor(Color.YELLOW);

        map.render(g);

        g.dispose();
        bs.show();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized  void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int key) {
        blueTank.keyPressed(key);
        greenTank.keyPressed(key);
    }

    public void keyReleased(int key) {
        blueTank.keyReleased(key);
        greenTank.keyReleased(key);
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
