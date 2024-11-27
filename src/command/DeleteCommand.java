package command;

import editor.PluginTextEditor;

public class DeleteCommand implements Command {
    private PluginTextEditor editor;;
    private String previousText;
    private int start;
    private int end;

    public DeleteCommand(PluginTextEditor pluginTextEditor) {
        this.editor = pluginTextEditor;
    }

    @Override
    public void execute(){
        String selected = editor.getTextArea().getSelectedText();

        if(selected != null && !selected.isEmpty()){
            previousText = selected;
            start = editor.getTextArea().getSelectionStart();
            end = editor.getTextArea().getSelectionEnd();
            editor.getTextArea().replaceRange("", start, end);
            System.out.println("Deleted: "+selected);
        }else {
            System.out.println("Unable to delete : ! No text selected !");
        }
    }

    @Override
    public void undo(){
        if(previousText != null && !previousText.isEmpty()){
            editor.getTextArea().insert(previousText, start);
            System.out.println("Undo delete");
        }else{
            System.out.println("No deleted string to undo");
        }
    }

}
