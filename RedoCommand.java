public class RedoCommand implements Command {
    private EnsembleManager manager;

    public RedoCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
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
