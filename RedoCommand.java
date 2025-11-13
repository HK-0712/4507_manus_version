// This is a concrete command
// It for redo last undo command
public class RedoCommand implements Command {
    private EnsembleService manager;

    public RedoCommand(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.redo();
    }

    @Override
    public void undo() {
        // Redo command itself cannot be undone
    }

    @Override
    public String getDescription() {
        return "Redo";
    }
}
