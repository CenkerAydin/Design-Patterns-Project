package factory;

import javax.swing.JFrame;

import editor.PluginTextEditor;

public abstract class PluginFactory {
    protected final JFrame frame;
    protected final PluginTextEditor editor;
    
    public PluginFactory(JFrame frame, PluginTextEditor editor) {
        this.frame = frame;
        this.editor = editor;
    }
    public abstract Plugin createPlugin();
}
