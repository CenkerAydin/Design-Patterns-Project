package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import editor.PluginTextEditor;

public class OpenFileCommand implements Command{
    private PluginTextEditor editor;
    private File previousFile;
    private String previousContent;

    public OpenFileCommand(PluginTextEditor editor){
        this.editor = editor;
    }

    @Override
    public void execute(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt","text","tex","log","md","rtf","odt","java","py","js","ts","json"));
        int option = fileChooser.showOpenDialog(editor);

        if(option == JFileChooser.APPROVE_OPTION){
           File selectedFile = fileChooser.getSelectedFile();

           try(BufferedReader reader = new BufferedReader(new FileReader(selectedFile))){
                previousFile = editor.getCurrentFile();
                previousContent = editor.getTextArea().getText();

                editor.getTextArea().read(reader,null);
                editor.setCurrentFile(selectedFile);
                System.out.println("File opened: "+selectedFile.getName());
           }catch(IOException e){
                System.out.println("IO Exception: "+e.getMessage());
                JOptionPane.showMessageDialog(editor, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }
    }

    @Override
    public void undo() {
        if(previousFile != null && previousContent != null && !previousContent.isEmpty()){
            try{
                editor.getTextArea().setText(previousContent);
                editor.setCurrentFile(previousFile);
                System.out.println("Undo open file: "+previousFile.getName());
            }catch(Exception e){
                System.out.println("Something went wrong: "+e.getMessage());
                JOptionPane.showMessageDialog(editor, "Error opening file!", "Undo Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            System.out.println("No previous file to undo to.");
        }
    }
    
}
