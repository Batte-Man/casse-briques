package gui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public final class UIConfig {

    // Fonts par défaut, initialisées dans le bloc statique
    public static final Font BUTTON_FONT;
    public static final Font TITLE_FONT;
    //Dark-Teal & Coral theme
    public static final Color BG_COLOR_PRIMARY = Color.decode("#1E282C"); // Arrière-plan principal
    public static final Color BG_COLOR_SECONDARY = Color.decode("#2E3B3F"); // Panneaux secondaires
    public static final Color FG_COLOR_PRIMARY = Color.decode("#ECE7E1"); // Texte principal
    public static final Color FG_COLOR_SECONDARY = Color.decode("#A5A39E"); // Texte secondaire
    public static final Color ACCENT_COLOR_1 = Color.decode("#3CAEA3"); // Boutons principaux
    public static final Color ACCENT_COLOR_2 = Color.decode("#F6D55C"); // Boutons secondaires
    public static final Color DANGER_COLOR = Color.decode("#ED553B"); // Actions dangereuses
    public static final Insets PADDING = new Insets(10, 10, 10, 10);

    static {
        Font arcade = null;
        try {
            InputStream in = UIConfig.class.getResourceAsStream("/fonts/PressStart2P.ttf");
            arcade = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (Exception e) {
            arcade = new Font("Arial", Font.BOLD, 24);
            System.err.println("Impossible de charger la police arcade, police fallback utilisée");
        }
        BUTTON_FONT = arcade.deriveFont(Font.BOLD, 20f);
        TITLE_FONT = arcade.deriveFont(Font.BOLD, 36f);
    }

    private UIConfig() throws IOException, FontFormatException {
    }
}
