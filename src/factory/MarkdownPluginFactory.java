package factory;

import javax.swing.JFrame;

import editor.PluginTextEditor;

public class MarkdownPluginFactory extends PluginFactory{
   
    public MarkdownPluginFactory(JFrame frame , PluginTextEditor editor) {
        super(frame, editor);
    }

    @Override
    public Plugin createPlugin() {
        return new MarkdownPlugin(frame ,editor);
    }

}
