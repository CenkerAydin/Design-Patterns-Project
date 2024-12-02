package factory;

import editor.PluginTextEditor;

public class LaTeXPluginFactory extends PluginFactory {
    private static LaTeXPluginFactory instance;

    private LaTeXPluginFactory(PluginTextEditor editor) {
        super(editor);
    }

    public static synchronized LaTeXPluginFactory getInstance(PluginTextEditor editor) {
        if (instance == null) {
            instance = new LaTeXPluginFactory(editor);
        }
        return instance;
    }

    @Override
    public Plugin createPlugin() {
        return new LaTeXPlugin(editor);
    }
}

