package factory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;

import editor.PluginTextEditor;

public class MarkdownPlugin implements Plugin {
    private JFrame frame;
    private PluginTextEditor editor;

    public MarkdownPlugin(JFrame frame , PluginTextEditor textEditor) {
        this.frame = frame;
        this.editor = textEditor;
    }

    @Override
    public void enablePreview() {
        frame.getContentPane().removeAll();

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScrollPane = new JScrollPane(textArea);

        JTextArea previewArea = new JTextArea();    
        previewArea.setEditable(false);
        JScrollPane previewScrollPane = new JScrollPane(previewArea);

        textArea.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                updatePreview(textArea,previewArea);
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                updatePreview(textArea,previewArea);
            }
            @Override
            public void changedUpdate(DocumentEvent e){
                updatePreview(textArea,previewArea);
            }
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textScrollPane,previewScrollPane);
        splitPane.setDividerLocation(400);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> exitPreview());
        frame.add(splitPane,BorderLayout.CENTER);
        frame.add(exit,BorderLayout.SOUTH);
        frame.setJMenuBar(frame.getJMenuBar());
        frame.revalidate();
        frame.repaint();
        
    }
    @Override
    public void updatePreview(JTextArea textArea, JTextArea previewArea) {
        String text = textArea.getText();
        String formattedText = convertMarkdownToHtml(text); // Markdown'u HTML'e dönüştürme
        previewArea.setText(formattedText);
    }

    @Override
    public void exitPreview() {
        frame.getContentPane().removeAll();
        editor.initializeUI();// to initialize the UI
        frame.revalidate();
        frame.repaint();
    }

    private String convertMarkdownToHtml(String markdownText) {
        // Başlıklar
        markdownText = markdownText.replaceAll("###### (.*)", "<h6>$1</h6>");
        markdownText = markdownText.replaceAll("##### (.*)", "<h5>$1</h5>");
        markdownText = markdownText.replaceAll("#### (.*)", "<h4>$1</h4>");
        markdownText = markdownText.replaceAll("### (.*)", "<h3>$1</h3>");
        markdownText = markdownText.replaceAll("## (.*)", "<h2>$1</h2>");
        markdownText = markdownText.replaceAll("# (.*)", "<h1>$1</h1>");

        // Kalın ve italik metin
        markdownText = markdownText.replaceAll("\\*\\*([^*]+)\\*\\*", "<b>$1</b>"); // **kalın**
        markdownText = markdownText.replaceAll("__(.+?)__", "<b>$1</b>"); // __kalın__
        markdownText = markdownText.replaceAll("\\*([^*]+)\\*", "<i>$1</i>"); // *italik*
        markdownText = markdownText.replaceAll("_(.+?)_", "<i>$1</i>"); // _italik_

        // Satır içi kod
        markdownText = markdownText.replaceAll("`([^`]+)`", "<code>$1</code>"); // `kod`

        // Bağlantılar
        markdownText = markdownText.replaceAll("\\[(.+?)\\]\\((https?://[^\\s]+)\\)", "<a href='$2'>$1</a>");

        // Yatay çizgi
        markdownText = markdownText.replaceAll("(?m)^---$", "<hr>");

        // Sırasız listeler
        markdownText = markdownText.replaceAll("(?m)^\\* (.*)", "<ul><li>$1</li></ul>");
        markdownText = markdownText.replaceAll("(?m)^- (.*)", "<ul><li>$1</li></ul>");

        // Sıralı listeler
        markdownText = markdownText.replaceAll("(?m)^\\d+\\. (.*)", "<ol><li>$1</li></ol>");

        // Yeni satırları <br> ile değiştirme
        markdownText = markdownText.replaceAll("\n", "<br>");

        // Listeleri düzgün göstermek için gereksiz <ul> veya <ol> etiketlerini düzeltme
        markdownText = markdownText.replaceAll("</ul><ul>", "");
        markdownText = markdownText.replaceAll("</ol><ol>", "");

        return markdownText;
    }
}
