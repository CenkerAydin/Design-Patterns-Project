package factory;

import javax.swing.JTextArea;

public interface Plugin {
    void enablePreview();
    void updatePreview(JTextArea textArea, JTextArea previewArea);
    void exitPreview();
}
