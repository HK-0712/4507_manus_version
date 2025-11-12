public class ListUndoRedoCommand implements Command {
    private final EnsembleManager manager;

    public ListUndoRedoCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Already set in constructor
    }

    @Override
    public void execute() {
        manager.listUndoRedo();
    }

    @Override
    public void undo() {
        // List command doesn't need undo
    }

    @Override
    public String getDescription() {
        return "List undo/redo";
    }
}
