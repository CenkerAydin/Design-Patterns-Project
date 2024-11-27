package command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import editor.PluginTextEditor;

public class PasteCommand implements Command {
    private PluginTextEditor editor;
    private String previousText;

    public PasteCommand(PluginTextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute(){
        try{
            previousText = editor.getTextArea().getText();

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String clipboardText = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);

            if(clipboardText != null && !clipboardText.isEmpty()){
                int caretPosition = editor.getTextArea().getCaretPosition();
                editor.getTextArea().insert(clipboardText, caretPosition);
                System.out.println("Pasted: "+clipboardText);
            }else {
                System.out.println("Unable to paste : ! Clipboard is empty !");
            }
        }catch(Exception e){
            System.out.println("Error while pasting: "+e.getMessage());
        }
    }

    @Override
    public void undo(){
        if(previousText != null){
            editor.getTextArea().setText(previousText);
            System.out.println("Undo paste");
        }else{
            System.out.println("No pasted string to undo");
        }
    }
}
