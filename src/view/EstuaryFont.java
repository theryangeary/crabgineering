package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A enum class to handle custom/non standard fonts; basically a wrapper for Font
 * that handles loading custom Fonts
 */
public enum EstuaryFont {
    MINECRAFT("Minecraft.ttf");

    private static final String FONT_DIR = "src/resources/fonts/";

    //the font loaded in
    private Font font;

    /**
     * Loads in the font and registers it with the computer's graphics environment
     * @param fileName The name of the file containing the font
     */
    EstuaryFont(String fileName){
        try {
            //load in font
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_DIR + fileName));

            //lets the local graphics environment know about this font
            //so that it can actually be drawn
            GraphicsEnvironment graphicsEnvironment =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(font);
        } catch (IOException e){
            e.printStackTrace();
        } catch (FontFormatException e){
            e.printStackTrace();
        }
    }

    /**
     * @return The custom Font object enclosed by this EstuaryFont
     */
    public Font getFont() {
        return font;
    }
}
