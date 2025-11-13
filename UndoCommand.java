public class UndoCommand implements Command {
    private EnsembleManager manager;

    public UndoCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
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
