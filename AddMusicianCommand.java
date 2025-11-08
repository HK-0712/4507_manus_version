public class AddMusicianCommand implements Command {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    // private String roleName; // Removed to enforce OCP
    
    public AddMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void execute() {
        ensemble.addMusician(musician);
    }
    
    @Override
    public void undo() {
        ensemble.dropMusician(musician);
    }
    
    @Override
    public String getDescription() {
        // Assuming the ensemble has a method to get the role name from the role ID
        String roleName = "";
        if (ensemble instanceof OrchestraEnsemble) {
            roleName = ((OrchestraEnsemble) ensemble).getRoleName(musician.getRole());
        } else if (ensemble instanceof JazzBandEnsemble) {
            roleName = ((JazzBandEnsemble) ensemble).getRoleName(musician.getRole());
        }
        return "Add musician, " + musician.getMID() + ", " + musician.getName() + ", " + roleName;
    }
}
