package strategy.theme;

import javax.swing.*;
import java.awt.*;

public class SolarizedThemeStrategy implements ThemeStrategy {
    @Override
    public void applyTheme(JTextArea textArea, JFrame frame) {
        // Solarized theme colors
        textArea.setBackground(new Color(253, 246, 227));  // Base3 (light background)
        textArea.setForeground(new Color(0, 43, 54));       // Base00 (text color)
        frame.getContentPane().setBackground(new Color(253, 246, 227));
    }
}