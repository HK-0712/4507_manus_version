import java.util.LinkedList;

public class CreateEnsembleCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private LinkedList<Ensemble> ensembles;
    private Ensemble ensemble;
    private final String ensembleTypeDescription;
    private final String ensembleID;
    private final String ensembleNameSnapshot;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    // private String ensembleType; // Removed to enforce OCP
    
    public CreateEnsembleCommand(LinkedList<Ensemble> ensembles, Ensemble ensemble) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
        this.ensembleTypeDescription = ensemble.getEnsembleTypeDescription();
        this.ensembleID = ensemble.getEnsembleID();
        this.ensembleNameSnapshot = ensemble.getName();
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
        return "Create " + ensembleTypeDescription + ", " + ensembleID + ", " + ensembleNameSnapshot;
    }
}
