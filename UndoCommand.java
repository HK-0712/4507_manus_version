// This is a concrete command
// It for undo last command
public class UndoCommand implements Command {
    private EnsembleService manager;

    public UndoCommand(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.undo();
    }

    @Override
    public void undo() {
        // Undo command itself cannot be undone
    }

    @Override
    public String getDescription() {
        return "Undo";
    }
}
