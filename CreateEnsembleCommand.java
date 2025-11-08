import java.util.LinkedList;

public class CreateEnsembleCommand implements Command {
    private LinkedList<Ensemble> ensembles;
    private Ensemble ensemble;
    private String ensembleType;
    
    public CreateEnsembleCommand(LinkedList<Ensemble> ensembles, Ensemble ensemble, String ensembleType) {
        this.ensembles = ensembles;
        this.ensemble = ensemble;
        this.ensembleType = ensembleType;
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
        if (ensembleType.equals("o")) {
            return "Create orchestra ensemble, " + ensemble.getEnsembleID() + ", " + ensemble.getName();
        } else {
            return "Create jazz band ensemble, " + ensemble.getEnsembleID() + ", " + ensemble.getName();
        }
    }
}
