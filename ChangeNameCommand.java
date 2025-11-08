public class ChangeNameCommand implements Command {
    private Ensemble ensemble;
    private String oldName;
    private String newName;
    
    public ChangeNameCommand(Ensemble ensemble, String oldName, String newName) {
        this.ensemble = ensemble;
        this.oldName = oldName;
        this.newName = newName;
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
