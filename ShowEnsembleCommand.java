public class ShowEnsembleCommand implements Command {
    private final EnsembleService manager;

    public ShowEnsembleCommand(EnsembleService manager) {
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
