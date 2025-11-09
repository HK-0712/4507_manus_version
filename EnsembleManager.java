import java.util.LinkedList;
import java.util.Stack;

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
        // 儲存當前所有 musician roles 和 ensemble names 的快照
        return new Memento(ensembles);
    }

    // Originator: Restore state from Memento
    public void restoreState(Memento memento) {
        // 還原所有 musician 的 roles
        for (Ensemble e : ensembles) {
            java.util.Iterator<Musician> it = e.getMusicians();
            while (it.hasNext()) {
                Musician m = it.next();
                Integer savedRole = memento.getMusicianRoles().get(m.getMID());
                if (savedRole != null) {
                    m.setRole(savedRole);
                }
            }
        }
        
        // 還原所有 ensemble 的 names
        for (Ensemble e : ensembles) {
            String savedName = memento.getEnsembleNames().get(e.getEnsembleID());
            if (savedName != null) {
                e.setName(savedName);
            }
        }
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
            boolean ensembleSwitched = false;
            if (command instanceof CreateEnsembleCommand) {
                // CreateEnsembleCommand 的 undo() 會將樂團從 ensembles 列表中移除。
                // 檢查 currentEnsemble 是否還存在於 ensembles 列表中
                boolean currentEnsembleExists = false;
                for (Ensemble e : ensembles) {
                    if (e.equals(currentEnsemble)) {
                        currentEnsembleExists = true;
                        break;
                    }
                }
                
                if (!currentEnsembleExists && !ensembles.isEmpty()) {
                    // 如果 currentEnsemble 被刪除，且還有其他樂團，則切換到第一個樂團 (E001 SYO)
                    this.currentEnsemble = ensembles.getFirst();
                    ensembleSwitched = true;
                } else if (ensembles.isEmpty()) {
                    // 如果所有樂團都被刪除
                    this.currentEnsemble = null;
                    ensembleSwitched = true; // 雖然是 null，但狀態改變了
                } else if (currentEnsembleExists) {
                    // 如果 currentEnsemble 仍然存在 (例如 undo 刪除 E102 時 currentEnsemble 是 E001)，
                    // 根據 PDF 範例，即使沒有切換，只要是 CreateEnsembleCommand 的 undo，也需要輸出狀態切換訊息。
                    ensembleSwitched = true;
                }
            }
            
            // 檢查是否為其他 EnsembleCommand 的 undo (即涉及切換 currentEnsemble)
            // 只有在不是 CreateEnsembleCommand 的 undo 時才執行此邏輯。
            if (!(command instanceof CreateEnsembleCommand) && command instanceof EnsembleCommand) {
                Ensemble ensembleInvolved = ((EnsembleCommand) command).getEnsemble();
                // 確保 ensembleInvolved 仍然存在於 ensembles 列表中
                boolean ensembleInvolvedExists = false;
                for (Ensemble e : ensembles) {
                    if (e.equals(ensembleInvolved)) {
                        ensembleInvolvedExists = true;
                        break;
                    }
                }
                
                if (ensembleInvolvedExists && !ensembleInvolved.equals(currentEnsemble)) {
                    // 如果 undone command 涉及一個不同的樂團，切換到它
                    this.currentEnsemble = ensembleInvolved;
                    ensembleSwitched = true;
                }
            }
            
            redoStack.push(command);
            
            // 確保只輸出一次 undo 訊息
            System.out.println("Command (" + command.getDescription() + ") is undone.");
            
            // 統一輸出樂團切換訊息
            if (ensembleSwitched) {
                if (currentEnsemble != null) {
                    System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID() + " " + this.currentEnsemble.getName() + ".");
                }
            }
            
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
            
            // 處理 redo 後的樂團切換邏輯
            boolean ensembleSwitched = false;
            
            // 檢查是否為 CreateEnsembleCommand 的 redo (即重新創建樂團)
            if (command instanceof CreateEnsembleCommand) {
                // CreateEnsembleCommand 的 execute() 會將樂團重新加入到 ensembles 列表中
                // 應該將 currentEnsemble 切換到新創建的樂團
                Ensemble redoneEnsemble = ((CreateEnsembleCommand) command).getEnsemble();
                this.currentEnsemble = redoneEnsemble;
                ensembleSwitched = true;
            }
            // 檢查是否為其他 EnsembleCommand 的 redo
            else if (command instanceof EnsembleCommand) {
                Ensemble ensembleInvolved = ((EnsembleCommand) command).getEnsemble();
                // 確保 ensembleInvolved 仍然存在於 ensembles 列表中
                boolean ensembleInvolvedExists = false;
                for (Ensemble e : ensembles) {
                    if (e.equals(ensembleInvolved)) {
                        ensembleInvolvedExists = true;
                        break;
                    }
                }
                
                if (ensembleInvolvedExists && !ensembleInvolved.equals(currentEnsemble)) {
                    // 如果 redo 的命令涉及一個不同的樂團,切換到它
                    this.currentEnsemble = ensembleInvolved;
                    ensembleSwitched = true;
                }
            }
            
            // 輸出樂團切換訊息
            if (ensembleSwitched && currentEnsemble != null) {
                System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID() + " " + this.currentEnsemble.getName() + ".");
            }
            
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
        System.err.println("");

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
