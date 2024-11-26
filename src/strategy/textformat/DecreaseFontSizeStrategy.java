package strategy.textformat;

import javax.swing.*;
import java.awt.*;

public class DecreaseFontSizeStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        Font font = textArea.getFont();
        textArea.setFont(font.deriveFont(Math.max(8f, font.getSize() - 2f)));
    }
}