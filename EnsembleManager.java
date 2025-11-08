import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Iterator;

public class EnsembleManager {
    private LinkedList<Ensemble> ensembles;
    private Ensemble currentEnsemble;
    private Stack<Command> history;
    private Stack<Command> redoStack;

    public EnsembleManager() {
        this.ensembles = new LinkedList<>();
        this.currentEnsemble = null;
        this.history = new Stack<>();
        this.redoStack = new Stack<>();
    }

    // Originator: Save state to Memento
    public Memento saveState() {
        // Deep copy the list of ensembles
        LinkedList<Ensemble> clonedEnsembles = new LinkedList<>();
        for (Ensemble e : ensembles) {
            clonedEnsembles.add(e.clone());
        }
        
        // Find the corresponding currentEnsemble in the cloned list
        Ensemble clonedCurrentEnsemble = null;
        if (currentEnsemble != null) {
            for (Ensemble e : clonedEnsembles) {
                if (e.getEnsembleID().equals(currentEnsemble.getEnsembleID())) {
                    clonedCurrentEnsemble = e;
                    break;
                }
            }
        }
        
        return new Memento(clonedEnsembles, clonedCurrentEnsemble);
    }

    // Originator: Restore state from Memento
    public void restoreState(Memento memento) {
        this.ensembles = memento.getState();
        this.currentEnsemble = memento.getCurrentEnsemble();
    }

    // Caretaker: Execute command with Memento logic
    public void executeCommand(Command command) {
        // Clear redo stack on new command
        redoStack.clear();
        
        // Save state before execution
        // The command itself will handle the state change, we only save the command to history
        // For a true Memento pattern, the command should contain the Memento of the state *before* execution.
        // Since the commands operate on the manager's state, we'll save the command itself to history.
        // This is a common simplification of Command + Memento.
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            redoStack.push(command);
            // System.out.println("Command (" + command.getDescription() + ") is undone."); // Removed to match PDF
        } else {
            System.out.println("Undo List is empty.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            history.push(command);
            // System.out.println("Command (" + command.getDescription() + ") is redone."); // Removed to match PDF
        } else {
            System.out.println("Redo List is empty.");
        }
    }

    public void listUndoRedo() {
        System.out.println("Undo List");
        for (int i = history.size() - 1; i >= 0; i--) {
            System.out.println(history.get(i).getDescription());
        }
        System.out.println("-- End of undo list --");

        System.out.println("Redo List");
        // Print redo stack in reverse order
        for (int i = redoStack.size() - 1; i >= 0; i--) {
            System.out.println(redoStack.get(i).getDescription());
        }
        System.out.println("-- End of redo list --");
    }

    // Utility methods for main program
    public LinkedList<Ensemble> getEnsembles() {
        return ensembles;
    }

    public Ensemble getCurrentEnsemble() {
        return currentEnsemble;
    }

    public void setCurrentEnsemble(Ensemble ensemble) {
        this.currentEnsemble = ensemble;
    }

    public void showCurrentEnsemble() {
        if (currentEnsemble != null) {
            currentEnsemble.showEnsemble();
        } else {
            System.out.println("No current ensemble set.");
        }
    }

    public void displayAllEnsembles() {
        if (ensembles.isEmpty()) {
            System.out.println("No ensembles created.");
            return;
        }
        for (Ensemble e : ensembles) {
            System.out.println(e.getName() + " (" + e.getEnsembleID() + ")");
        }
    }

    public Stack<Command> getHistory() {
        return history;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }
}
