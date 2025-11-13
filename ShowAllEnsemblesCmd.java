public class ShowAllEnsemblesCmd implements Command {
    private final EnsembleService manager;

    public ShowAllEnsemblesCmd(EnsembleService manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleService manager) {
        
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
