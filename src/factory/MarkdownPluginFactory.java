package factory;

import editor.PluginTextEditor;

public class MarkdownPluginFactory extends PluginFactory {
    private static MarkdownPluginFactory instance;

    private MarkdownPluginFactory(PluginTextEditor editor) {
        super(editor);
    }

    public static synchronized MarkdownPluginFactory getInstance(PluginTextEditor editor) {
        if (instance == null) {
            instance = new MarkdownPluginFactory(editor);
        }
        return instance;
    }

    @Override
    public Plugin createPlugin() {
        return new MarkdownPlugin(editor);
    }
}

