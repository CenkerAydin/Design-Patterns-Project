package command;

import editor.PluginTextEditor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyCommand implements Command {
    private PluginTextEditor editor;
    private String previousText;

    public CopyCommand(PluginTextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute(){
        String selectedText = editor.getTextArea().getSelectedText();
        if(selectedText != null && !selectedText.isEmpty()){
            previousText = selectedText;
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            cb.setContents(new StringSelection(selectedText), null);
            System.out.println("Copied: "+previousText);
        }else{
            System.out.println("No text selected to copy!");
        }
    }
    @Override
    public void undo(){
        System.out.println("Undo not applicable for copy command!");
    }

}
