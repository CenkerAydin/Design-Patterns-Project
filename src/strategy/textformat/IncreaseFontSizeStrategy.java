package strategy.textformat;

import javax.swing.*;
import java.awt.*;

public class IncreaseFontSizeStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        Font font = textArea.getFont();
        textArea.setFont(font.deriveFont(font.getSize() + 2f));
    }
}
