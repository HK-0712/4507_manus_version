public class ShowEnsembleCommand implements Command {
    private final EnsembleManager manager;

    public ShowEnsembleCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        
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
