package command;

import java.util.Stack;


public class Invoker {
    private final Stack<Command> history;
    private static Invoker instance;

    // Private constructor
    private Invoker() {
        history = new Stack<>();
    }

    // Singleton için erişim metodu
    public static synchronized Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }

    public void executeCommand(Command command) {
        history.push(command);
        command.execute();
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("No command to undo");
        }
    }
}

