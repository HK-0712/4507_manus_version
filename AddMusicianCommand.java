public class AddMusicianCommand implements Command {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    private final String roleName;
    private final String description;
    // private String roleName; // Removed to enforce OCP
    
    public AddMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.roleName = resolveRoleName(musician.getRole());
        this.description = buildDescription();
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
        return description;
    }

    private String buildDescription() {
        return "Add musician, " + musician.getMID() + ", " + musician.getName() + ", " + roleName;
    }

    private String resolveRoleName(int role) {
        if (ensemble instanceof OrchestraEnsemble) {
            return ((OrchestraEnsemble) ensemble).getRoleName(role);
        } else if (ensemble instanceof JazzBandEnsemble) {
            return ((JazzBandEnsemble) ensemble).getRoleName(role);
        }
        return "unknown";
    }
}
