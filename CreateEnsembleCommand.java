import java.util.LinkedList;

public class CreateEnsembleCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private LinkedList<Ensemble> ensembles;
    private Ensemble ensemble;
    private final String ensType;
    private final String eID;
    private final String ensemblName;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public CreateEnsembleCommand(LinkedList<Ensemble> ensembles, Ensemble ensemble) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
        this.ensType = ensemble.getEnsembleTypeDescription();
        this.eID = ensemble.getEnsembleID();
        this.ensemblName = ensemble.getName();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        ensembles.add(ensemble);
        System.out.println(ensemble.getEnsembleTypeDescription() + " is created.");
    }

    @Override
    public void undo() {
        ensembles.remove(ensemble);
    }

    @Override
    public String getDescription() {
        return "Create " + ensType + ", " + eID + ", " + ensemblName;
    }
}
