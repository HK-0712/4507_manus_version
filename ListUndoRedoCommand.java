public class ListUndoRedoCommand implements Command {
    private final EnsembleManager manager;

    public ListUndoRedoCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        
    }

    @Override
    public void execute() {
        manager.listUndoRedo();
    }

    @Override
    public void undo() {
        
    }

    @Override
    public String getDescription() {
        return "List undo/redo";
    }
}
