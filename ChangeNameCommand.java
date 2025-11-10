// Command to change ensemble name with undo support
public class ChangeNameCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private String newName;
    // Memento to save ensemble's old name
    private Ensemble.EnsembleMemento memento;

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
        this.manager = manager;
    }

    @Override
    public void execute() {
        // Save old name before changing
        memento = ensemble.save();
        ensemble.setName(newName);
        System.out.println("Ensemble's name is updated.");
    }

    @Override
    public void undo() {
        // Restore old name
        if (memento != null) {
            ensemble.restore(memento);
        }
    }

    @Override
    public String getDescription() {
        return "Change ensemble\u2019s name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
