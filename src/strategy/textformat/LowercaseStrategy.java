package strategy.textformat;

import javax.swing.*;

public class LowercaseStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        textArea.replaceSelection(textArea.getSelectedText().toLowerCase());
    }
}