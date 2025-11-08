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

    public Command undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            
            // 檢查是否為 CreateEnsembleCommand 的 undo (即刪除樂團)
            if (command instanceof CreateEnsembleCommand) {
                // 由於 CreateEnsembleCommand 的 undo 刪除了樂團，需要檢查 currentEnsemble 是否仍有效
                // 這裡的邏輯是：如果 currentEnsemble 仍然存在於 ensembles 列表中，則保持不變。
                // 否則，如果 currentEnsemble 被刪除，則 currentEnsemble 應設為 null (由 CreateEnsembleCommand 的 undo 處理)
                // 根據 PDF 範例，即使 currentEnsemble 沒變，也需要輸出狀態切換訊息
                
                // 檢查是否需要輸出狀態切換訊息 (無論是否真的切換)
                // 由於 CreateEnsembleCommand 的 undo 已經輸出了 "orchestra ensemble is created." 或 "jazz band ensemble is created."
                // 我們需要確保在 undo 之後，如果 currentEnsemble 仍然存在，則輸出其狀態
                if (currentEnsemble != null) {
                    System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID() + " " + this.currentEnsemble.getName() + ".");
                }
            }
            
            // 檢查是否為其他 EnsembleCommand 的 undo (即涉及切換 currentEnsemble)
            if (command instanceof EnsembleCommand) {
                Ensemble ensembleInvolved = ((EnsembleCommand) command).getEnsemble();
                if (ensembleInvolved != null && !ensembleInvolved.equals(currentEnsemble)) {
                    // 如果 undone command 涉及一個不同的樂團，切換到它
                    this.currentEnsemble = ensembleInvolved;
                    System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID() + " " + this.currentEnsemble.getName() + ".");
                }
            }
            
            redoStack.push(command);
            // 確保只輸出一次 undo 訊息
            // Command 的 undo() 方法不應該輸出 "Command (...) is undone."
            // 而是由 EnsembleManager 統一輸出
            System.out.println("Command (" + command.getDescription() + ") is undone.");
            return command;
        } else {
            System.out.println("Undo List is empty.");
        }
        return null;
    }

    public Command redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            history.push(command);
            // 確保只輸出一次 redo 訊息
            System.out.println("Command (" + command.getDescription() + ") is redone.");
            return command;
        } else {
            System.out.println("Redo List is empty.");
        }
        return null;
    }

    public void listUndoRedo() {
        System.out.println("Undo List");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i).getDescription());
        }
        System.out.println("-- End of undo list --");

        System.out.println("Redo List");
        for (int i = 0; i < redoStack.size(); i++) {
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
            System.out.println(e.getEnsembleTypeDescription() + " " + e.getName() + " (" + e.getEnsembleID() + ")");
        }
    }

    public Stack<Command> getHistory() {
        return history;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }
}
