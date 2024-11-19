import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PluginTextEditor extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> themeComboBox;
    private JList<String> pluginList;
    private JList<String> fileList;
    private DefaultListModel<String> pluginListModel;
    private DefaultListModel<String> filesListModel;
    private File currentFile;


    public PluginTextEditor() {
        setTitle("Plugin-Based Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menü Çubuğu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As");
        JMenuItem exportToPDF = new JMenuItem("Export to PDF");

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.add(exportToPDF);

        menuBar.add(fileMenu);

        openFile.addActionListener(e -> openFile());
        saveFile.addActionListener(e -> saveFile());
        saveAsFile.addActionListener(e -> saveFileAs());

        exportToPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
          //      exportToPDF();
            }
        });

        //Plugin
        JMenu pluginMenu = new JMenu("Plugins");
        JMenuItem loadPlugin = new JMenuItem("Load Plugin");
        loadPlugin.addActionListener(new LoadPluginActionListener());
        pluginMenu.add(loadPlugin);
        menuBar.add(pluginMenu);

        //Edit
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(delete);
        menuBar.add(editMenu);

        //Theme
        JMenu themeMenu = new JMenu("Themes");
        JMenuItem lightTheme = new JMenuItem("Light Theme");
        JMenuItem darkTheme = new JMenuItem("Dark Theme");
        JMenuItem solarizedTheme = new JMenuItem("Solarized Theme");
        themeMenu.add(lightTheme);
        themeMenu.add(darkTheme);
        themeMenu.add(solarizedTheme);
        menuBar.add(themeMenu);
        setJMenuBar(menuBar);

        lightTheme.addActionListener(new ThemeChangeActionListener("Default"));
        darkTheme.addActionListener(new ThemeChangeActionListener("Dark"));
        solarizedTheme.addActionListener(new ThemeChangeActionListener("Solarized"));


        // Ana Metin Alanı
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Sağ Panel (Plugin Listesi ve Files and Folders)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(200, 600));

        // Plugin List Panel
        JPanel pluginPanel = new JPanel();
        pluginPanel.setLayout(new BorderLayout());
        JLabel pluginLabel = new JLabel("Loaded Plugins:");
        pluginListModel = new DefaultListModel<>();
        pluginList = new JList<>(pluginListModel);
        JScrollPane pluginScrollPane = new JScrollPane(pluginList);
        pluginPanel.add(pluginLabel, BorderLayout.NORTH);
        pluginPanel.add(pluginScrollPane, BorderLayout.CENTER);
        rightPanel.add(pluginPanel);

        // Files and Folders Panel
        JPanel filesAndFoldersPanel = new JPanel();
        filesAndFoldersPanel.setLayout(new BorderLayout());
        JLabel filesAndFolderLabel = new JLabel("Files and Folders:");
        filesListModel = new DefaultListModel<>();
        fileList = new JList<>(filesListModel);
        JScrollPane filesListScrollPane = new JScrollPane(fileList);
        filesAndFoldersPanel.add(filesAndFolderLabel, BorderLayout.NORTH);
        filesAndFoldersPanel.add(filesListScrollPane, BorderLayout.CENTER);
        rightPanel.add(filesAndFoldersPanel);

        // Add rightPanel to the main frame
        add(rightPanel, BorderLayout.EAST);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "md", "tex"));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Save file method
    private void saveFile() {
        if (currentFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveFileAs();
        }
    }

    // Save As file method
    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "md", "tex"));
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Tema Değiştirme İşlemi
    private class ThemeChangeActionListener implements ActionListener {
        private final String theme;

        public ThemeChangeActionListener(String theme) {
            this.theme = theme;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("Dark".equals(theme)) {
                textArea.setBackground(Color.DARK_GRAY);
                textArea.setForeground(Color.WHITE);
            } else if ("Solarized".equals(theme)) {
                textArea.setBackground(new Color(0xEEE8D5));
                textArea.setForeground(new Color(0x073642));
            } else { // Default theme
                textArea.setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
            }
        }
    }

    // Plugin Yükleme İşlemi
    private class LoadPluginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pluginName = JOptionPane.showInputDialog("Enter Plugin Name:");
            if (pluginName != null && !pluginName.isEmpty()) {
                pluginListModel.addElement(pluginName);
                JOptionPane.showMessageDialog(null, "Plugin " + pluginName + " loaded successfully!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PluginTextEditor editor = new PluginTextEditor();
            editor.setVisible(true);
        });
    }
}
