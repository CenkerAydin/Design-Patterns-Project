package factory;

import editor.PluginTextEditor;

public class LaTeXPluginFactory extends PluginFactory{
       
    public LaTeXPluginFactory(PluginTextEditor editor) {
        super(editor);
    }

    @Override
    public Plugin createPlugin() {
        return new LaTeXPlugin(editor);
    }
}
