public class ShowEnsembleCommand implements Command {
    private final EnsembleManager manager;

    public ShowEnsembleCommand(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Already set in constructor
    }

    @Override
    public void execute() {
        manager.showCurrentEnsemble();
    }

    @Override
    public void undo() {
        // Show command doesn't need undo
    }

    @Override
    public String getDescription() {
        return "Show current ensemble";
    }
}
