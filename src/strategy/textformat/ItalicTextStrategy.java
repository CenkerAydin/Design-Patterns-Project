package strategy.textformat;

import javax.swing.*;
import java.awt.*;

public class ItalicTextStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        Font font = textArea.getFont();
        if (font.isItalic()) {
            textArea.setFont(font.deriveFont(font.getStyle() & ~Font.ITALIC)); // Remove italic
        } else {
            textArea.setFont(font.deriveFont(font.getStyle() | Font.ITALIC)); // Add italic
        }
    }
}
