package command;


import editor.PluginTextEditor;

public class CutCommand implements Command {
    private PluginTextEditor editor;
    private String previousText;
    private int start;
    private int end;

    public CutCommand(PluginTextEditor editor, int start, int end) {
        this.editor = editor;
        this.start = start;
        this.end = end;
    }
    @Override
    public void execute() {
        previousText = editor.getTextArea().getText();
        String text = editor.getTextArea().getText();
        editor.getTextArea().setText(text.substring(0, start) + text.substring(end));
        System.out.println("Cut : "+text.substring(start, end));
    }
    @Override
    public void undo() {
        editor.getTextArea().setText(previousText);
        System.out.println("Undo Cut : "+previousText);
    }
    
}
