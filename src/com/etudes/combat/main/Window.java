package com.etudes.combat.main;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    public Window(String title, Game game) {
        JFrame frame = new JFrame(title);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
