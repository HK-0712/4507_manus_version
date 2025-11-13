public class ShowHistoryCmd implements Command {
    private final EnsembleService manager;

    public ShowHistoryCmd(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleService manager) {
        
    }

    @Override
    public void execute() {
        manager.showHistory();
    }

    @Override
    public void undo() {
        
    }

    @Override
    public String getDescription() {
        return "List undo/redo";
    }
}
