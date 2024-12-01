package factory;

import editor.PluginTextEditor;

public abstract class PluginFactory {
    protected final PluginTextEditor editor;
    
    
    public PluginFactory(PluginTextEditor editor) {
        this.editor = editor;
    }
    public abstract Plugin createPlugin();
}
