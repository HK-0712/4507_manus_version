public class ChangeNameCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private String oldName;
    private String newName;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    
    public ChangeNameCommand(Ensemble ensemble, String newName) {
        this.ensemble = ensemble;
        this.newName = newName;
        this.oldName = ensemble.getName();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void execute() {
        ensemble.setName(newName);
    }
    
    @Override
    public void undo() {
        ensemble.setName(oldName);
    }
    
    @Override
    public String getDescription() {
        return "Change ensemble\u2019s name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
