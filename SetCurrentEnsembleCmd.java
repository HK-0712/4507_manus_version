// This is a concrete command
// It for set current ensemble
public class SetCurrentEnsembleCmd implements Command {
    private final EnsembleService manager;
    private final String ensembleID;
    private Ensemble previousEnsemble;
    private Ensemble targetEnsemble;

    public SetCurrentEnsembleCmd(EnsembleService manager, String ensembleID) {
        this.manager = manager;
        this.ensembleID = ensembleID;
    }

    @Override
    public void setManager(EnsembleService manager) {
        
    }

    @Override
    public void execute() {
        previousEnsemble = manager.getCurrentEnsemble();
        
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
