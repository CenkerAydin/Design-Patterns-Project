package factory;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import editor.PluginTextEditor;

public class LaTeXPlugin implements Plugin {
    private PluginTextEditor editor;

    public LaTeXPlugin(PluginTextEditor textEditor) {
        this.editor = textEditor;
    }

    @Override
    public void enablePreview(){
        editor.getContentPane().removeAll();

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

        editor.add(splitPane,BorderLayout.CENTER);
        editor.add(exit,BorderLayout.SOUTH);
        editor.setJMenuBar(editor.getJMenuBar());
        editor.revalidate();
        editor.repaint();
    }

    @Override
    public void updatePreview(JTextArea textArea, JTextArea previewArea){
        String text = textArea.getText();
        String html = convertLaTeXToHtml(text);
        previewArea.setText(html);
    }

    @Override
    public void exitPreview(){
        editor.getContentPane().removeAll();
        editor.initializeUI();// to initialize the UI
        editor.revalidate();
        editor.repaint();
    }




    public String convertLaTeXToHtml(String latex){
        String htmlContent = "<html>\n" +
        "<head>\n" +
        "<script type=\"text/javascript\" async " +
        "src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/MathJax.js?config=TeX-MML-AM_CHTML\"></script>\n" +
        "</head>\n" +
        "<body>\n" +
        "<div id=\"math\">" + latex + "</div>\n" +  // LaTeX içerik burada olacak
        "</body>\n" +
        "</html>";

        return htmlContent;
    }
}
