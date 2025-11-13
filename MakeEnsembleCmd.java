import java.util.*;

// This is a concrete command
// It for make new ensemble
public class MakeEnsembleCmd implements EnsembleCommand {
    private final LinkedList<Ensemble> ensembles;
    private final Ensemble ensemble;
    private final String ensembleType;
    private final String eID;
    private final String ensembleName;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public MakeEnsembleCmd(LinkedList<Ensemble> ensembles, Ensemble ensemble, String typeLabel) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
        this.ensembleType = typeLabel;
        this.eID = ensemble.getEnsembleID();
        this.ensembleName = ensemble.getName();
    }

    @Override
    public void setManager(EnsembleService manager) {
        
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
        return "Create " + ensembleType + ", " + eID + ", " + ensembleName;
    }
}
