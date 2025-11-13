// This is concrete command for change name
// It use Memento pattern for undo
public class ChangeNameCmd implements EnsembleCommand {
    private final Ensemble ensemble;
    private final String newName;
    private EnsembleState memento;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public ChangeNameCmd(Ensemble ensemble, String newName) {
        this.ensemble = ensemble;
        this.newName = newName;
    }

    @Override
    public void setManager(EnsembleService manager) {
        
    }

    @Override
    public void execute() {
        // create memento for save state before change
        memento = new EnsembleState(ensemble);
        ensemble.setName(newName);
    }

    @Override
    public void undo() {
        // use memento to restore old state
        memento.restore();
    }

    @Override
    public String getDescription() {
        return "Change ensemble's name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
