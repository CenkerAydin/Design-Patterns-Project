import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import renderer.FileAndFolderRenderer;
import strategy.export.ExportToHtmlStrategy;
import strategy.export.ExportToLatexStrategy;
import strategy.export.ExportToMarkdownStrategy;
import strategy.export.TextExportStrategy;
import strategy.textformat.*;
import strategy.theme.DarkThemeStrategy;
import strategy.theme.LightThemeStrategy;
import strategy.theme.SolarizedThemeStrategy;
import strategy.theme.ThemeStrategy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class PluginTextEditor extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> themeComboBox;
    private JList<String> pluginList;
    private JList<String> fileList;
    private DefaultListModel<String> pluginListModel;
    private DefaultListModel<String> filesListModel;
    private File currentFile;
    private String rootDirectory;
    private String filesPath;
    private String pluginsPath;
    private TextFormattingStrategy textFormattingStrategy;



    public PluginTextEditor() {
        initializeUI();  // Calls the method to initialize UI components
    }

    // Method to initialize all the components
    private void initializeUI() {
        setTitle("Plugin-Based Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize root directory
        rootDirectory = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Plugin Text Editor";
        createFilesDirectory();
        createPluginsFile();

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As");

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        menuBar.add(fileMenu);

        openFile.addActionListener(e -> openFile());
        saveFile.addActionListener(e -> saveFile());
        saveAsFile.addActionListener(e -> saveFileAs());


        // Plugin Menu
        JMenu pluginMenu = new JMenu("Plugins");
        JMenuItem loadPlugin = new JMenuItem("Load Plugin");
        loadPlugin.addActionListener(new LoadPluginActionListener());
        pluginMenu.add(loadPlugin);
        menuBar.add(pluginMenu);

        // Edit Menu
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

        // Theme Menu
        JMenu themeMenu = new JMenu("Themes");
        JMenuItem lightTheme = new JMenuItem("Light Theme");
        JMenuItem darkTheme = new JMenuItem("Dark Theme");
        JMenuItem solarizedTheme = new JMenuItem("Solarized Theme");
        themeMenu.add(lightTheme);
        themeMenu.add(darkTheme);
        themeMenu.add(solarizedTheme);
        menuBar.add(themeMenu);

        setJMenuBar(menuBar);

        lightTheme.addActionListener(e -> applyTheme(new LightThemeStrategy()));
        darkTheme.addActionListener(e -> applyTheme(new DarkThemeStrategy()));
        solarizedTheme.addActionListener(e -> applyTheme(new SolarizedThemeStrategy()));

        //Format
        JMenu formatMenu = new JMenu("Format");
        JMenuItem lowercase = new JMenuItem("Lowercase");
        JMenuItem reverse = new JMenuItem("Reverse");
        JMenuItem uppercase = new JMenuItem("Uppercase");
        JMenuItem boldItem = new JMenuItem("Bold");
        JMenuItem italicItem = new JMenuItem("Italic");
        JMenuItem underlineItem = new JMenuItem("Underline");
        JMenuItem increaseFont= new JMenuItem("Increase Font");
        JMenuItem decreaseFont= new JMenuItem("Decrease Font");

        lowercase.addActionListener(e -> applyTextFormatting(new LowercaseStrategy()));
        reverse.addActionListener(e -> applyTextFormatting(new ReverseTextStrategy()));
        uppercase.addActionListener(e -> applyTextFormatting(new UppercaseStrategy()));
        boldItem.addActionListener(e -> applyTextFormatting(new BoldTextStrategy()));
        italicItem.addActionListener(e -> applyTextFormatting(new ItalicTextStrategy()));
        underlineItem.addActionListener(e -> applyTextFormatting(new UnderlineTextStrategy()));
        increaseFont.addActionListener(e -> applyTextFormatting(new IncreaseFontSizeStrategy()));
        decreaseFont.addActionListener(e -> applyTextFormatting(new DecreaseFontSizeStrategy()));

        formatMenu.add(lowercase);
        formatMenu.add(reverse);
        formatMenu.add(uppercase);
        formatMenu.add(boldItem);
        formatMenu.add(italicItem);
        formatMenu.add(underlineItem);
        formatMenu.add(increaseFont);
        formatMenu.add(decreaseFont);

        menuBar.add(formatMenu);

        JMenu exportMenu = new JMenu("Export");
        JMenuItem exportMarkdown = new JMenuItem("Export as Markdown");
        JMenuItem exportLatex = new JMenuItem("Export as LaTeX");
        JMenuItem exportHtml = new JMenuItem("Export as HTML");

        exportHtml.addActionListener(e -> applyExportStrategy(new ExportToHtmlStrategy()));
        exportMarkdown.addActionListener(e -> applyExportStrategy(new ExportToMarkdownStrategy()));
        exportLatex.addActionListener(e -> applyExportStrategy(new ExportToLatexStrategy()));

        exportMenu.add(exportMarkdown);
        exportMenu.add(exportLatex);
        exportMenu.add(exportHtml);
        menuBar.add(exportMenu);


        // Main Text Area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Right Panel (Plugin and Files List)
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

        // List Plugins
        listPlugins();

        // Files and Folders Panel
        JPanel filesAndFoldersPanel = new JPanel();
        filesAndFoldersPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filesAndFolderLabel = new JLabel("Documents:");

        JButton newFileButton = new JButton("📄");
        newFileButton.setToolTipText("New File");
        newFileButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog("Enter file name:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                createFile(new File(filesPath), fileName);
            }
        });

        JButton newFolderButton = new JButton("📁");
        newFolderButton.setToolTipText("New Folder");
        newFolderButton.addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog("Enter folder name:");
            if (folderName != null && !folderName.trim().isEmpty()) {
                File folder = new File(filesPath, folderName);
                if (folder.mkdir()) {
                    System.out.println("Folder created: " + folder.getPath());
                    listFilesAndFolders();
                } else {
                    JOptionPane.showMessageDialog(null, "Folder already exists or cannot be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        headerPanel.add(filesAndFolderLabel);
        headerPanel.add(newFileButton);
        headerPanel.add(newFolderButton);

        filesListModel = new DefaultListModel<>();
        fileList = new JList<>(filesListModel);
        JScrollPane filesListScrollPane = new JScrollPane(fileList);
        filesAndFoldersPanel.add(headerPanel, BorderLayout.NORTH);
        filesAndFoldersPanel.add(filesListScrollPane, BorderLayout.CENTER);
        rightPanel.add(filesAndFoldersPanel);

        // List Files and Folders
        listFilesAndFolders();

        // Render list of files and folders
        fileList.setCellRenderer(new FileAndFolderRenderer());

        // Add mouse listener to the file list
        addFileListMouseListener(textArea);

        // Add the right panel to the frame
        add(rightPanel, BorderLayout.EAST);

        // Refresh the frame
        revalidate();
        repaint();
    }


    private void listFilesAndFolders() {
        File root = new File(filesPath);
        filesListModel.clear();
        if (root.exists() && root.isDirectory()) {
            File[] files = root.listFiles();
            for (File file : files) {
                filesListModel.addElement(file.getAbsolutePath());
            }
        }
    }

    private void listFilesInFolder(File folder) {
        filesListModel.clear();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                filesListModel.addElement(file.getAbsolutePath());
            }
        }
    }

    private void addFileListMouseListener(JTextArea textArea) {
        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = fileList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        String selectedPath = filesListModel.get(index);
                        File selectedFile = new File(selectedPath);
                        if (selectedFile.isDirectory()) {
                            listFilesInFolder(selectedFile);
                        } else {
                            readFile(selectedFile, textArea);
                        }
                    }
                }
            }
        });
    }

    private void createFile(File directory, String fileName) {
        File file = new File(directory, fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getPath());
                listFilesInFolder(directory);
            } else {
                System.out.println("File already exists: " + file.getPath());
                JOptionPane.showMessageDialog(null,
                        "File already exists or cannot be created.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error creating file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void readFile(File file, JTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.read(reader, null);
            textArea.setCaretPosition(0);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    "Error reading file: " + file.getName(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createFilesDirectory() {
        File directory = new File(rootDirectory);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Root directory created: " + directory.getPath());
        }
        filesPath = rootDirectory + File.separator + "files";
        File filesDirectory = new File(filesPath);
        if (!filesDirectory.exists()) {
            filesDirectory.mkdir();
            System.out.println("Files directory created: " + filesDirectory.getPath());
        } else {
            System.out.println("Files directory already exists: " + filesDirectory.getPath());
        }
    }

    private void createPluginsFile() {
        pluginsPath = rootDirectory + File.separator + "plugins.txt";
        File plugins = new File(pluginsPath);
        try {
            if (!plugins.exists()) {
                if (plugins.createNewFile()) {
                    System.out.println("File is created: " + plugins.getPath());
                } else {
                    System.out.println("Plugins file could not be created!");
                }
            } else {
                System.out.println("File already exists: " + plugins.getPath());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private void listPlugins() {
        File plugins = new File(pluginsPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(plugins))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pluginListModel.addElement(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        // Sağ tıklama işlemi
        pluginList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = pluginList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        pluginList.setSelectedIndex(index);
                        String selected = pluginList.getSelectedValue();
                        showPluginPopupMenu(e.getPoint(), selected);

                        // Markdown seçildiğinde preview görünümünü etkinleştir
                        if ("Markdown".equalsIgnoreCase(selected)) {
                            enableMarkdownPreview();
                        }
                    }
                }
            }
        });
    }

    private void enableMarkdownPreview() {
        // Mevcut görünümü temizleyip iki alanlı yapıyı oluştur
        getContentPane().removeAll();

        // Yazma alanı
        JTextArea writingArea = new JTextArea();
        writingArea.setLineWrap(true);
        writingArea.setWrapStyleWord(true);
        JScrollPane writingScrollPane = new JScrollPane(writingArea);

        // Önizleme alanı
        JTextArea previewArea = new JTextArea();
        previewArea.setEditable(false); // Düzenlenemez
        JScrollPane previewScrollPane = new JScrollPane(previewArea);

        // Yazma alanına her değişiklik yapıldığında önizleme alanını güncelle
        writingArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePreview(writingArea, previewArea);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePreview(writingArea, previewArea);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePreview(writingArea, previewArea);
            }
        });

        // Split Pane (İkiye bölmek için)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, writingScrollPane, previewScrollPane);
        splitPane.setDividerLocation(400);

        // Çıkış butonu
        JButton exitButton = new JButton("Çıkış");
        exitButton.addActionListener(e -> exitMarkdownPreview());

        // Yeni görünümü ekleyin
        add(splitPane, BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH); // Çıkış butonunu alt kısma ekleyin
        setJMenuBar(getJMenuBar());  // Menü çubuğunu tekrar ekleyin
        revalidate();
        repaint();
    }

    // Çıkış butonuna basıldığında varsayılan görünüme geri dönen metot
    private void exitMarkdownPreview() {
        // Clear the content pane before re-adding components
        getContentPane().removeAll();

        // Reinitialize the UI components
        initializeUI();

        // Optionally, re-add other components or settings
        revalidate();
        repaint();
    }




    // Markdown'u önizleme alanına dönüştürmek için kullanılan bir işlev
    private void updatePreview(JTextArea writingArea, JTextArea previewArea) {
        String text = writingArea.getText();
        String formattedText = convertMarkdownToHtml(text); // Markdown'u HTML'e dönüştürme
        previewArea.setText(formattedText);
    }

    // Markdown'u basitçe HTML'e çeviren örnek işlev
    private String convertMarkdownToHtml(String markdownText) {
        // Başlıklar
        markdownText = markdownText.replaceAll("###### (.*)", "<h6>$1</h6>");
        markdownText = markdownText.replaceAll("##### (.*)", "<h5>$1</h5>");
        markdownText = markdownText.replaceAll("#### (.*)", "<h4>$1</h4>");
        markdownText = markdownText.replaceAll("### (.*)", "<h3>$1</h3>");
        markdownText = markdownText.replaceAll("## (.*)", "<h2>$1</h2>");
        markdownText = markdownText.replaceAll("# (.*)", "<h1>$1</h1>");

        // Kalın ve italik metin
        markdownText = markdownText.replaceAll("\\*\\*([^*]+)\\*\\*", "<b>$1</b>"); // **kalın**
        markdownText = markdownText.replaceAll("__(.+?)__", "<b>$1</b>"); // __kalın__
        markdownText = markdownText.replaceAll("\\*([^*]+)\\*", "<i>$1</i>"); // *italik*
        markdownText = markdownText.replaceAll("_(.+?)_", "<i>$1</i>"); // _italik_

        // Satır içi kod
        markdownText = markdownText.replaceAll("`([^`]+)`", "<code>$1</code>"); // `kod`

        // Bağlantılar
        markdownText = markdownText.replaceAll("\\[(.+?)\\]\\((https?://[^\\s]+)\\)", "<a href='$2'>$1</a>");

        // Yatay çizgi
        markdownText = markdownText.replaceAll("(?m)^---$", "<hr>");

        // Sırasız listeler
        markdownText = markdownText.replaceAll("(?m)^\\* (.*)", "<ul><li>$1</li></ul>");
        markdownText = markdownText.replaceAll("(?m)^- (.*)", "<ul><li>$1</li></ul>");

        // Sıralı listeler
        markdownText = markdownText.replaceAll("(?m)^\\d+\\. (.*)", "<ol><li>$1</li></ol>");

        // Yeni satırları <br> ile değiştirme
        markdownText = markdownText.replaceAll("\n", "<br>");

        // Listeleri düzgün göstermek için gereksiz <ul> veya <ol> etiketlerini düzeltme
        markdownText = markdownText.replaceAll("</ul><ul>", "");
        markdownText = markdownText.replaceAll("</ol><ol>", "");

        return markdownText;
    }


    private void addPLugin(String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pluginsPath, true))) {
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Invalid plugin name!");
                return;
            }
            if (pluginListModel.contains(name)) {
                System.out.println("Plugin already exists!");
                return;
            }
            File plugins = new File(pluginsPath);
            if (plugins.length() > 0) {
                writer.newLine();
            }
            writer.write(name);
            pluginListModel.addElement(name);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private void deletePlugin(String plugin) {
        Queue<String> buffer = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pluginsPath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(plugin)) {
                    buffer.add(currentLine);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pluginsPath));) {
            while (!buffer.isEmpty()) {
                writer.write(buffer.poll());
                if (!buffer.isEmpty()) {
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        pluginListModel.removeElement(plugin);
    }

    private void showPluginPopupMenu(Point point, String plugin) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("remove");
        delete.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deletePlugin(plugin);
                    }
                }
        );
        popupMenu.add(delete);
        popupMenu.show(pluginList, point.x, point.y);
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

    // Plugin Yükleme İşlemi
    private class LoadPluginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pluginName = JOptionPane.showInputDialog("Enter Plugin Name:");
            if (pluginName != null && !pluginName.trim().isEmpty()) {
                addPLugin(pluginName);
                JOptionPane.showMessageDialog(null, "Plugin " + pluginName + " loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid plugin name!");
            }
        }
    }

    private void applyTheme(ThemeStrategy themeStrategy) {
        themeStrategy.applyTheme(textArea, this);
    }

    private void applyTextFormatting(TextFormattingStrategy strategy) {
        this.textFormattingStrategy = strategy;
        if (textFormattingStrategy != null) {
            textFormattingStrategy.applyStyle(textArea);
        }
    }
    private void applyExportStrategy(TextExportStrategy strategy) {
        strategy.exportText(textArea);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PluginTextEditor editor = new PluginTextEditor();
            editor.setVisible(true);
        });
    }
}
