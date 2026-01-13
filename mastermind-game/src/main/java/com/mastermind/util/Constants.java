package com.mastermind.util;

import java.awt.Color;

public final class Constants {
    public static final int MAX_PEGS = 6;
    public static final int WELL_PLACED = -1;
    public static final int MISPLACED = 0;

    public static final String[] COLOR_NAMES = {
        "", "Rouge", "Jaune", "Vert", "Bleu", "Orange", "Noir", "Marron", "Fuchsia"
    };

    public static final Color[] COLORS = {
        null,
        new Color(220, 20, 60),
        new Color(255, 215, 0),
        new Color(50, 205, 50),
        new Color(30, 144, 255),
        new Color(255, 140, 0),
        new Color(40, 40, 40),
        new Color(139, 69, 19),
        new Color(255, 0, 255)
    };

    private Constants() {}
}
