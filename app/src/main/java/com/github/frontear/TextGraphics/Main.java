package com.github.frontear.TextGraphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

public class Main {
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz"; //ABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~ ";
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
    
    // Create a temporary Graphics object, grabs a FontMetrics instance, and destroys. This is the only way to obtain a FontMetrics instance cleanly.
    private static FontMetrics getFontMetrics() {
        var image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        var graphics = image.createGraphics();
        var metrics = graphics.getFontMetrics(FONT);

        graphics.dispose();
        return metrics;
    }

    private static BufferedImage createTexture(String characters) {
        var len = characters.length();
        var pair = new int[] { len / 1, 1 };

        for (int i = 1 + 1; i <= len; ++i) {
            var old_abs = Math.abs(pair[0] - pair[1]);
            var cur_abs = Math.abs((len / i) - i);

            if (old_abs > cur_abs) {
                pair[0] = len / i;
                pair[1] = i;
            }
            else {
                break;
            }
        }

        var m = Math.max(pair[0], pair[1]);
        
        var w = m;
        var h = m;

        while (w * h < len) {
            w += 1;

            if (w * h < len) {
                h += 1;
            }
        }

        var metrics = getFontMetrics();
        
        var height = metrics.getHeight();
        var width = 0;

        for (var c : characters.toCharArray()) {
            width = Math.max(width, metrics.charWidth(c));
        }

        m = Math.max(width * w, height * h);

        return new BufferedImage(m, m, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * 1 2
     * 3 4
     * 
     * 1 2
     * 3
     * 
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * 
     * 1 2 3
     * 4 5 6
     * 7
     */
    public static void main(String[] args) {
        var metrics = getFontMetrics();
        var image = createTexture("abcdefghijklmnopqrstuvwxyz");

        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
    }
}