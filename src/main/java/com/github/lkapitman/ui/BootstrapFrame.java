package com.github.lkapitman.ui;

import com.github.lkapitman.utils.Constants;
import com.github.lkapitman.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class BootstrapFrame extends JFrame {

    public BootstrapFrame()  {
        this.setTitle(Constants.NAME + "-Bootstrap | V-" + Constants.VERSION);
        this.setSize(Constants.WITH, Constants.HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        try {
            this.setIconImage(ImageIO.read(Main.class.getResourceAsStream("/img/icon.png")));
        } catch (IOException e) {
            Constants.LOGGER.log(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage(),"Ошибка-" + Constants.NAME, JOptionPane.ERROR_MESSAGE);
        }
        this.setContentPane(new BootstrapPanel());
        this.setVisible(true);
    }

}
