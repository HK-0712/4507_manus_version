public class ShowEnsembleCmd implements Command {
    private final EnsembleService manager;

    public ShowEnsembleCmd(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleService manager) {
        
    }

    @Override
    public void execute() {
        manager.showCurrentEnsemble();
    }

    @Override
    public void undo() {
        
    }

    @Override
    public String getDescription() {
        return "Show current ensemble";
    }
}
