public class ChangeNameCommand implements Command {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private String oldName;
    private String newName;
    
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
        return "Change ensemble's name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
