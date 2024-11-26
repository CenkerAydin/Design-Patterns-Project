package strategy.theme;

import javax.swing.*;
import java.awt.*;

public class LightThemeStrategy implements ThemeStrategy {
    @Override
    public void applyTheme(JTextArea textArea, JFrame frame) {
        // Light theme colors
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        frame.getContentPane().setBackground(Color.WHITE);
    }
}