package command;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import editor.PluginTextEditor;

public class FindCommand implements Command{
    private PluginTextEditor editor;
    private Highlighter.Highlight[] previousHighlighters;

    public FindCommand(PluginTextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        String requestString = JOptionPane.showInputDialog(editor,"Enter text to find: ","Find",JOptionPane.PLAIN_MESSAGE);

        if(requestString == null || requestString.isEmpty()) {
            JOptionPane.showMessageDialog(editor, "Invalid text or cancelled", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Invalid text or cancelled");
            return;
        }
        
        String content = editor.getTextArea().getText();
        Highlighter highlighter = editor.getTextArea().getHighlighter();

        previousHighlighters = highlighter.getHighlights();
        highlighter.removeAllHighlights();

        int index =0, match = 0;
        
        while((index = content.toLowerCase().indexOf(requestString.toLowerCase(),index)) >= 0){
            try{
                int endIndex = index + requestString.length();
                highlighter.addHighlight(index,endIndex,new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
                index = endIndex;
                match++;
            }catch(BadLocationException e){
                System.out.println("Error while highlighting text: " + e.getMessage());
            }
        }
        if(match == 0){
            JOptionPane.showMessageDialog(editor, "No match found", "Find", JOptionPane.INFORMATION_MESSAGE);
        }else{
            System.out.println("Found " + match + " matches for " + requestString);
        }
    }

    @Override
    public void undo() {
        if(previousHighlighters == null){
            System.out.println("No previous highlighters to undo");
        }
        Highlighter highlighter = editor.getTextArea().getHighlighter();
        highlighter.removeAllHighlights();

        for(Highlighter.Highlight h : previousHighlighters){
            try{
                highlighter.addHighlight(h.getStartOffset(),h.getEndOffset(),h.getPainter());
            }catch(BadLocationException e){
                System.out.println("Error while highlighting text: " + e.getMessage());
            }
        }

    }
}
