public class ChangeNameCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private String newName;
    // Classic Memento Pattern: Command saves the Memento of the specific Ensemble being modified
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
        // Classic Memento Pattern: Save state of only the Ensemble being modified
        memento = ensemble.save();
        ensemble.setName(newName);
        System.out.println("Ensemble's name is updated.");
    }

    @Override
    public void undo() {
        // Classic Memento Pattern: Restore state of only the Ensemble
        if (memento != null) {
            ensemble.restore(memento);
        }
    }

    @Override
    public String getDescription() {
        return "Change ensemble\u2019s name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
