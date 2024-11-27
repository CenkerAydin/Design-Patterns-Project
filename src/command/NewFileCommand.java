package command;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import editor.PluginTextEditor;

public class NewFileCommand implements Command{
    private PluginTextEditor editor;
    private File previousFile;
    private String previousContent;

    public NewFileCommand(PluginTextEditor editor){
        this.editor = editor;
    }

    @Override
    public void execute(){
        previousContent = editor.getTextArea().getText();
        previousFile = editor.getCurrentFile();

        String fileName = JOptionPane.showInputDialog(editor, "Enter the new file name:", "New File", JOptionPane.PLAIN_MESSAGE);
        if(fileName == null || fileName.trim().isEmpty()){
            JOptionPane.showMessageDialog(editor, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File(editor.getFilesPath(), fileName.trim());
        if(file.exists()){
            JOptionPane.showMessageDialog(editor, "File already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            if(file.createNewFile()){
                editor.getTextArea().setText("");
                editor.setCurrentFile(file);
                JOptionPane.showMessageDialog(editor, "File created successfully ", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(editor, "Failed to create file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(editor, "Failed to create file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void undo(){
        if(previousContent != null || previousFile != null){
            editor.getTextArea().setText(previousContent);
            editor.setCurrentFile(previousFile);
            JOptionPane.showMessageDialog(editor, "Undo successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Undo successful, file opened : " + previousFile.getName());
        }else{
            JOptionPane.showMessageDialog(editor, "Nothing to undo!", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Nothing to undo!");
        }
    }
}
