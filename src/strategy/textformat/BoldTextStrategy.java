package strategy.textformat;

import javax.swing.*;
import java.awt.*;

public class BoldTextStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        Font font = textArea.getFont();
        if (font.isBold()) {
            textArea.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD)); // Remove bold
        } else {
            textArea.setFont(font.deriveFont(font.getStyle() | Font.BOLD)); // Add bold
        }
    }
}

