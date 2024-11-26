package strategy.textformat;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class UnderlineTextStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        Font font = textArea.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());

        // Toggle underline
        if (TextAttribute.UNDERLINE_ON.equals(attributes.get(TextAttribute.UNDERLINE))) {
            attributes.remove(TextAttribute.UNDERLINE); // Remove underline
        } else {
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // Add underline
        }

        textArea.setFont(font.deriveFont(attributes));
    }
}