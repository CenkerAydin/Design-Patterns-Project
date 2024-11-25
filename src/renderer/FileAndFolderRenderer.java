package renderer;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

public class FileAndFolderRenderer extends DefaultListCellRenderer {
    private Icon fileIcon;
    private Icon folderIcon;

    public FileAndFolderRenderer() {
        this.fileIcon = UIManager.getIcon("FileView.fileIcon");
        this.folderIcon = UIManager.getIcon("FileView.directoryIcon");
    }

    @Override
    public Component getListCellRendererComponent(
        JList<?> list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus
    ){
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        File file = new File((String) value);
        
        if(file.isDirectory()) {
            label.setIcon(folderIcon);
        } else {
            label.setIcon(fileIcon);
        }
        
        label.setText(file.getName());
        return label;
    }
}
