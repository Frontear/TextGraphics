package com.github.frontear.TextGraphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
     * Greater emphasis is put on the pair diff then the summation diff, and greater emphasis is put on the diff diffs as well. This is so confusing lmao
     * 
     * 26: 5*5, as 5-5=0 and 26-(5*5)=1 where 0 and 1 are the smallest differences
     * 27: 5*5, as 5-5=0 and 27-(5*5)=2 where 0 and 2 are the smallest differences
     * 28: 6*5, as 6-5=1 and 28-(6*5)=2 where 1 and 2 are the smallest differences (5-5=0 and 28-(6*5)=2, 0 and 2 diff is larger than 1 and 2)
     * 29: 6*5, as 6-5=1 and 29-(6*5)=1 where 1 and 1 are the smallest differences (5-5=0 and 29-(6*5)=1, 0 and 1 diff is larger than 1 and 1)
     * 30: 6*5, as 6-5=1 and 30-(6*5)=0 where 1 and 0 are the smallest differences (5-5=0 and 30-(6*5)=0, 0 and 4 diff is larger than 1 and 1)
     */
    public static void main(String[] args) {
        var len = 27;
        var diff_list = new ArrayList<int[]>();

        for (var i = 1; i <= Math.sqrt(len); ++i) {
            for (var j = 1; j <= len / i; ++j) {
                System.out.println(i + " " + j + ", " + Math.abs(i - j) + " " + Math.abs(len - i * j) + ", " + Math.abs(Math.abs(i - j) - Math.abs(len - i * j)));
            }
        }
    }
}