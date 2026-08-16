package com.tinsl.zambielib.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

/**
 * Drawing helpers for interfaces in the vanilla visual language: the classic
 * raised gray panel, dark advancement-style insets, creative-style scrollbar
 * thumbs, and experience-style progress bars. Client side only.
 */
public final class GuiKit {
    public static final int FRAME_FILL = 0xFFC6C6C6;
    public static final int FRAME_LIGHT = 0xFFFFFFFF;
    public static final int FRAME_DARK = 0xFF555555;
    public static final int OUTLINE = 0xFF000000;
    public static final int INSET_FILL = 0xFF212121;
    public static final int INSET_EDGE_DARK = 0xFF0F0F0F;
    public static final int INSET_EDGE_LIGHT = 0xFF505050;
    public static final int TITLE_GRAY = 0x404040;
    public static final int WHITE = 0xFFFFFF;
    public static final int GRAY = 0xAAAAAA;
    public static final int DARK_GRAY = 0x555555;
    public static final int YELLOW = 0xFFFF55;
    public static final int XP_GREEN = 0x80FF20;

    private GuiKit() {
    }

    /** The classic raised vanilla panel: black outline, bevel, gray fill. */
    public static void drawRaisedPanel(GuiGraphicsExtractor g, int x, int y, int w, int h) {
        g.fill(x + 2, y, x + w - 2, y + 1, OUTLINE);
        g.fill(x + 2, y + h - 1, x + w - 2, y + h, OUTLINE);
        g.fill(x, y + 2, x + 1, y + h - 2, OUTLINE);
        g.fill(x + w - 1, y + 2, x + w, y + h - 2, OUTLINE);
        g.fill(x + 1, y + 1, x + 2, y + 2, OUTLINE);
        g.fill(x + w - 2, y + 1, x + w - 1, y + 2, OUTLINE);
        g.fill(x + 1, y + h - 2, x + 2, y + h - 1, OUTLINE);
        g.fill(x + w - 2, y + h - 2, x + w - 1, y + h - 1, OUTLINE);
        g.fill(x + 1, y + 2, x + w - 1, y + h - 2, FRAME_FILL);
        g.fill(x + 2, y + 1, x + w - 2, y + 2, FRAME_FILL);
        g.fill(x + 2, y + h - 2, x + w - 2, y + h - 1, FRAME_FILL);
        g.fill(x + 2, y + 1, x + w - 3, y + 3, FRAME_LIGHT);
        g.fill(x + 1, y + 2, x + 3, y + h - 3, FRAME_LIGHT);
        g.fill(x + 3, y + h - 3, x + w - 2, y + h - 1, FRAME_DARK);
        g.fill(x + w - 3, y + 3, x + w - 1, y + h - 2, FRAME_DARK);
    }

    /** A sunken dark window, like the inside of the advancement screen. */
    public static void drawInset(GuiGraphicsExtractor g, int x, int y, int w, int h) {
        g.fill(x, y, x + w - 1, y + 1, INSET_EDGE_DARK);
        g.fill(x, y, x + 1, y + h - 1, INSET_EDGE_DARK);
        g.fill(x + 1, y + h - 1, x + w, y + h, INSET_EDGE_LIGHT);
        g.fill(x + w - 1, y + 1, x + w, y + h, INSET_EDGE_LIGHT);
        g.fill(x + 1, y + 1, x + w - 1, y + h - 1, INSET_FILL);
    }

    /** A one pixel rectangle outline, the server-list selection style. */
    public static void drawOutline(GuiGraphicsExtractor g, int x, int y, int w, int h, int color) {
        g.fill(x, y, x + w, y + 1, color);
        g.fill(x, y + h - 1, x + w, y + h, color);
        g.fill(x, y, x + 1, y + h, color);
        g.fill(x + w - 1, y, x + w, y + h, color);
    }

    /** A creative-inventory style scrollbar thumb, five pixels wide. */
    public static void drawScrollThumb(GuiGraphicsExtractor g, int x, int y, int h) {
        g.fill(x, y, x + 5, y + h, 0xFF8B8B8B);
        g.fill(x, y, x + 4, y + h - 1, FRAME_FILL);
        g.fill(x, y, x + 1, y + h - 1, FRAME_LIGHT);
    }

    /** An experience-style progress bar with a black border. */
    public static void drawXpBar(GuiGraphicsExtractor g, int x, int y, int w, float progress) {
        g.fill(x - 1, y - 1, x + w + 1, y + 4, 0xFF000000);
        g.fill(x, y, x + w, y + 3, 0xFF3E3E3E);
        g.fill(x, y, x + (int) (w * Math.max(0.0f, Math.min(1.0f, progress))), y + 3, 0xFF000000 | XP_GREEN);
    }

    /**
     * Draws text wrapped to a width and returns the height used. Pass
     * draw as false to measure without drawing.
     */
    public static int drawWrapped(GuiGraphicsExtractor g, Font font, Component text, int x, int y,
                                  int width, int color, boolean draw) {
        List<FormattedCharSequence> lines = font.split(text, width);
        if (draw) {
            int lineY = y;
            for (FormattedCharSequence line : lines) {
                g.text(font, line, x, lineY, color, false);
                lineY += 10;
            }
        }
        return lines.size() * 10;
    }
}
