package strategy.export;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportToMarkdownStrategy implements TextExportStrategy {
    @Override
    public void exportText(JTextArea textArea) {
        String text = textArea.getText();

        // Basic Markdown formatting (for simplicity, no advanced formatting)
        String markdownText = "# Exported Document\n\n" + text;

        // Let the user choose the filename and location using JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as Markdown");
        fileChooser.setSelectedFile(new java.io.File("document.md"));

        // Ensure the user selects a .md file
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Markdown Files", "md"));

        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            // Get the file chosen by the user
            java.io.File selectedFile = fileChooser.getSelectedFile();

            // If the file does not already have an extension, add .md
            if (!selectedFile.getName().endsWith(".md")) {
                selectedFile = new java.io.File(selectedFile.getAbsolutePath() + ".md");
            }

            try (FileOutputStream out = new FileOutputStream(selectedFile)) {
                out.write(markdownText.getBytes());
                JOptionPane.showMessageDialog(null, "Text exported as Markdown");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

