import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PluginTextEditor extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> themeComboBox;
    private JList<String> pluginList;
    private DefaultListModel<String> pluginListModel;

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
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        menuBar.add(fileMenu);

        JMenu pluginMenu = new JMenu("Plugins");
        JMenuItem loadPlugin = new JMenuItem("Load Plugin");
        loadPlugin.addActionListener(new LoadPluginActionListener());
        pluginMenu.add(loadPlugin);
        menuBar.add(pluginMenu);

        setJMenuBar(menuBar);

        // Ana Metin Alanı
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Sağ Panel (Tema ve Plugin Listesi)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(200, 600));

        // Tema Seçimi
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BorderLayout());
        JLabel themeLabel = new JLabel("Select Theme:");
        themeComboBox = new JComboBox<>(new String[]{"Light", "Dark", "Solarized"});
        themeComboBox.addActionListener(new ThemeChangeActionListener());
        themePanel.add(themeLabel, BorderLayout.NORTH);
        themePanel.add(themeComboBox, BorderLayout.CENTER);
        rightPanel.add(themePanel, BorderLayout.NORTH);

        // Plugin Listesi
        JPanel pluginPanel = new JPanel();
        pluginPanel.setLayout(new BorderLayout());
        JLabel pluginLabel = new JLabel("Loaded Plugins:");
        pluginListModel = new DefaultListModel<>();
        pluginList = new JList<>(pluginListModel);
        JScrollPane pluginScrollPane = new JScrollPane(pluginList);
        pluginPanel.add(pluginLabel, BorderLayout.NORTH);
        pluginPanel.add(pluginScrollPane, BorderLayout.CENTER);
        rightPanel.add(pluginPanel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);
    }

    // Tema Değiştirme İşlemi
    private class ThemeChangeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            if ("Dark".equals(selectedTheme)) {
                textArea.setBackground(Color.DARK_GRAY);
                textArea.setForeground(Color.WHITE);
            } else if ("Solarized".equals(selectedTheme)) {
                textArea.setBackground(new Color(0xEEE8D5));
                textArea.setForeground(new Color(0x073642));
            } else { // Light theme as default
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
