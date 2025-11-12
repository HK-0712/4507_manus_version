public class ChangeNameCommand implements EnsembleCommand {
    private final Ensemble ensemble;
    private final String newName;
    private EnsembleMemento memento;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public ChangeNameCommand(Ensemble ensemble, String newName) {
        this.ensemble = ensemble;
        this.newName = newName;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        
    }

    @Override
    public void execute() {
        memento = new EnsembleMemento(ensemble);
        ensemble.setName(newName);
    }

    @Override
    public void undo() {
        memento.restore();
    }

    @Override
    public String getDescription() {
        return "Change ensemble's name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
