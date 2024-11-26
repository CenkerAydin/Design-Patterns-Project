package strategy.textformat;


import javax.swing.*;

public class UppercaseStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        textArea.replaceSelection(textArea.getSelectedText().toUpperCase());
    }
}