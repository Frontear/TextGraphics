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
        var len = "abcdefghijklmnopqrstuvwxyz".length();
        System.out.println(len); // 26, the most efficient multiply pair is 4 * 6 + 2

        var i_diff = -1;
        var j_diff = -1;
        var ij_diff = -1;
        var mul_diff = -1;

        for (var i = 1; i <= len; ++i) {
            for (var j = 1; j <= len / i; ++j) {
                var ij = Math.abs(i - j);
                var mul = Math.abs((i * j) - len);

                if (i_diff == -1 || j_diff == -1 || ij_diff == -1 || mul_diff == -1) {
                    i_diff = i;
                    
                    if (j_diff == -1) {
                        j_diff = j;
                    }
                    if (ij_diff == -1) {
                        ij_diff = ij;
                    }
                    if (mul_diff == -1) {
                        mul_diff = mul;
                    }

                    continue;
                }
                else if (ij_diff != -1 && mul_diff != -1) {
                    if (ij_diff - ij < 0 && mul_diff - mul < 0) {
                        System.out.println(String.format("%d * %d, %d %d", i_diff, j_diff, ij_diff, mul_diff));
                        return;
                    }
                    else {
                        i_diff = i;
                        j_diff = j;
                        ij_diff = ij;
                        mul_diff = mul;
                    }
                }
            }
        }
    }
}