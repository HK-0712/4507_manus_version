public class DisplayAllEnsemblesCommand implements Command {
    private final EnsembleManager manager;

    public DisplayAllEnsemblesCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        
    }

    @Override
    public void execute() {
        manager.displayAllEnsembles();
    }

    @Override
    public void undo() {
        
    }

    @Override
    public String getDescription() {
        return "Display all ensembles";
    }
}
