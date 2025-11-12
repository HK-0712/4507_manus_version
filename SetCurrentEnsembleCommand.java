public class SetCurrentEnsembleCommand implements Command {
    private final EnsembleManager manager;
    private final String ensembleID;
    private Ensemble previousEnsemble;
    private Ensemble targetEnsemble;

    public SetCurrentEnsembleCommand(EnsembleManager manager, String ensembleID) {
        this.manager = manager;
        this.ensembleID = ensembleID;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Already set in constructor
    }

    @Override
    public void execute() {
        previousEnsemble = manager.getCurrentEnsemble();
        
        // Find the ensemble
        targetEnsemble = null;
        for (Ensemble e : manager.getEnsembles()) {
            if (e.getEnsembleID().equals(ensembleID)) {
                targetEnsemble = e;
                break;
            }
        }
        
        if (targetEnsemble != null) {
            manager.setCurrentEnsemble(targetEnsemble);
        }
    }

    @Override
    public void undo() {
        manager.setCurrentEnsemble(previousEnsemble);
    }

    @Override
    public String getDescription() {
        return "Set current ensemble, " + ensembleID;
    }

    public boolean isSuccess() {
        return targetEnsemble != null;
    }

    public String getEnsembleID() {
        return ensembleID;
    }
}
