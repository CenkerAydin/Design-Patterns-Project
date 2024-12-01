package factory;

import editor.PluginTextEditor;

public class MarkdownPluginFactory extends PluginFactory{
   
    public MarkdownPluginFactory(PluginTextEditor editor) {
        super(editor);
    }

    @Override
    public Plugin createPlugin() {
        return new MarkdownPlugin(editor);
    }

}
