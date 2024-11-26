package strategy.export;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportToLatexStrategy implements TextExportStrategy {
    @Override
    public void exportText(JTextArea textArea) {
        String text = textArea.getText();

        // Basic LaTeX formatting (for simplicity)
        String latexText = "\\documentclass{article}\n" +
                "\\begin{document}\n" +
                "\\title{Exported Document}\n" +
                "\\maketitle\n" +
                "\\section{Introduction}\n" +
                text.replace("\n", "\n\\par\n") +  // Adding LaTeX paragraph breaks
                "\\end{document}";

        // Prompt the user for a filename using an input dialog
        String filename = JOptionPane.showInputDialog(null, "Enter the filename:", "Save as LaTeX", JOptionPane.QUESTION_MESSAGE);

        if (filename != null && !filename.trim().isEmpty()) {
            // Ensure the filename has a .tex extension
            if (!filename.endsWith(".tex")) {
                filename += ".tex";
            }

            // Let the user choose the file location and save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save as LaTeX");
            fileChooser.setSelectedFile(new java.io.File(filename));

            int userChoice = fileChooser.showSaveDialog(null);

            if (userChoice == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();

                try (FileOutputStream out = new FileOutputStream(selectedFile)) {
                    out.write(latexText.getBytes());
                    JOptionPane.showMessageDialog(null, "Text exported as LaTeX");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No filename entered. Export canceled.");
        }
    }
}
