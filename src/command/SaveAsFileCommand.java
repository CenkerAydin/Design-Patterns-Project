package command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import editor.PluginTextEditor;

public class SaveAsFileCommand implements Command {
    private PluginTextEditor editor;
    
    public SaveAsFileCommand(PluginTextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt","text","tex","log","md","rtf","odt","java","py","js","ts","json"));
        int option = fileChooser.showSaveDialog(editor);

        if(option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try{
                try(FileWriter writer = new FileWriter(selectedFile)){
                    writer.write(editor.getTextArea().getText());
                    JOptionPane.showMessageDialog(editor, "File " + selectedFile.getName() + " saved successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                }
                editor.setCurrentFile(selectedFile);
            }catch(IOException e){
                System.out.println("An error occurred while saving the file : " + e.getMessage());
            }
        }else {
            JOptionPane.showMessageDialog(editor, "Save as operation cancelled","Info",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Save as operation cancelled");
        } 
    }

    @Override
    public void undo() {
        System.out.println("Undo is not supported for saving files");
    }

}
