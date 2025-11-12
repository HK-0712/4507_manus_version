public class ChangeNameCommand implements EnsembleCommand {
    private final Ensemble ensemble;
    private final String newName;
    private String oldName;

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
        // Not required for current operations.
    }

    @Override
    public void execute() {
        oldName = ensemble.getName();
        ensemble.setName(newName);
        System.out.println("Ensemble's name is updated.");
    }

    @Override
    public void undo() {
        ensemble.setName(oldName);
    }

    @Override
    public String getDescription() {
        return "Change ensemble's name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
