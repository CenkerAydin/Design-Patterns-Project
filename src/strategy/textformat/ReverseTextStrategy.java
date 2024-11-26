package strategy.textformat;

import javax.swing.*;

public class ReverseTextStrategy implements TextFormattingStrategy {
    @Override
    public void applyStyle(JTextArea textArea) {
        // Get the selected text
        String selectedText = textArea.getSelectedText();

        if (selectedText != null && !selectedText.isEmpty()) {
            // Reverse the selected text
            StringBuilder reversedText = new StringBuilder(selectedText).reverse();

            // Replace the selected text with the reversed text
            textArea.replaceSelection(reversedText.toString());
        }
    }
}