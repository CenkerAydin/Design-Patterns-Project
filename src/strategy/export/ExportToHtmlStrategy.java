package strategy.export;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class ExportToHtmlStrategy implements TextExportStrategy {
    @Override
    public void exportText(JTextArea textArea) {
        String text = textArea.getText();
        String htmlText = "<html><body><p>" + text.replace("\n", "<br>") + "</p></body></html>";

        // Prompt user to input a filename
        String fileName = JOptionPane.showInputDialog(null, "Enter the name of the HTML file:", "document.html");

        // If user cancels or doesn't provide a name, return
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Export cancelled or invalid file name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ensure the filename ends with .html
        if (!fileName.toLowerCase().endsWith(".html")) {
            fileName += ".html";
        }

        // Get the path to the desktop
        String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop", fileName).toString();

        try (PrintWriter out = new PrintWriter(desktopPath)) {
            out.println(htmlText);
            JOptionPane.showMessageDialog(null, "Text successfully exported as HTML to the Desktop");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting text as HTML", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
