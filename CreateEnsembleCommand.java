import java.util.LinkedList;

public class CreateEnsembleCommand implements EnsembleCommand {
    private final LinkedList<Ensemble> ensembles;
    private final Ensemble ensemble;
    private final String ensembleTypeLabel;
    private final String eID;
    private final String ensembleName;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public CreateEnsembleCommand(LinkedList<Ensemble> ensembles, Ensemble ensemble, String typeLabel) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
        this.ensembleTypeLabel = typeLabel;
        this.eID = ensemble.getEnsembleID();
        this.ensembleName = ensemble.getName();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Not required for current operations.
    }

    @Override
    public void execute() {
        ensembles.add(ensemble);
    }

    @Override
    public void undo() {
        ensembles.remove(ensemble);
    }

    @Override
    public String getDescription() {
        return "Create " + ensembleTypeLabel + ", " + eID + ", " + ensembleName;
    }
}
