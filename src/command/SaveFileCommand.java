package command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import editor.PluginTextEditor;

public class SaveFileCommand implements Command {
    private PluginTextEditor editor;

    public SaveFileCommand(PluginTextEditor editor) {
        this.editor = editor;
    }
    @Override
    public void execute(){
        File currentFile = editor.getCurrentFile();

        if(currentFile == null){
            JFileChooser chooser = new JFileChooser(editor.getFilesPath());
            int option = chooser.showSaveDialog(editor);
            if(option == JFileChooser.APPROVE_OPTION){
                currentFile = chooser.getSelectedFile();
                editor.setCurrentFile(currentFile);
                System.out.println("File saved as: " + currentFile.getName());
            }else{
                JOptionPane.showMessageDialog(editor, "Save cancelled","Info",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Save cancelled");
            }
        }
        try{
            String content = editor.getTextArea().getText();
            try(FileWriter writer = new FileWriter(currentFile)){
                writer.write(content);
                JOptionPane.showMessageDialog(editor, "File " + currentFile.getName() + " saved successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("File " + currentFile.getName() + " saved successfully");
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(editor, "An error occurred while saving the file","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("An error occurred while saving the file");
        }
    }
    @Override
    public void undo(){
        System.out.println("Undo is not supported for saving files");
    }
}
