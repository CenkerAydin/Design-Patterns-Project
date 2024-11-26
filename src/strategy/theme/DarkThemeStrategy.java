package strategy.theme;

import javax.swing.*;
import java.awt.*;

public class DarkThemeStrategy implements ThemeStrategy {
    @Override
    public void applyTheme(JTextArea textArea, JFrame frame) {
        // Dark theme colors
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
    }
}
