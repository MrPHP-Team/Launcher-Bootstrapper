package com.github.lkapitman;

import com.github.lkapitman.ui.BootstrapFrame;
import com.github.lkapitman.utils.Constants;

public class Main {

    public static void main(String[] args) {
        Constants.LOGGER.log("Bootstrap запущен.\n");
        new BootstrapFrame();
    }

}
