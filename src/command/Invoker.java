package command;

import java.util.Stack;

public class Invoker {
    private Stack<Command> history;

    public Invoker() {
        history = new Stack<>();
    }
    public void executeCommand(Command command) {
        history.add(command);
        command.execute();
    }
    public void undo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        }else {
            System.out.println("No command to undo");
        }
    }
}
