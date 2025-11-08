import java.util.LinkedList;

public class CreateEnsembleCommand implements Command {
    private EnsembleManager manager;
    private LinkedList<Ensemble> ensembles;
    private Ensemble ensemble;
    // private String ensembleType; // Removed to enforce OCP
    
    public CreateEnsembleCommand(LinkedList<Ensemble> ensembles, Ensemble ensemble) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
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
        return "Create " + ensemble.getEnsembleTypeDescription() + ", " + ensemble.getEnsembleID() + ", " + ensemble.getName();
    }
}
