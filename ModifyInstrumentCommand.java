public class ModifyInstrumentCommand implements EnsembleCommand {
    private Ensemble ensemble;
    private Musician musician;
    private int oldRole;
    private int newRole;
    private final String roleName;
    private final String description;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    // private String newRoleName; // Removed to enforce OCP
    
    public ModifyInstrumentCommand(Ensemble ensemble, Musician musician, int newRole) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.newRole = newRole;
        this.oldRole = musician.getRole();
        this.roleName = resolveRoleName(newRole);
        this.description = buildDescription();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Manager reference is not required; ensemble is provided directly.
    }
    
    @Override
    public void execute() {
        musician.setRole(newRole);
    }
    
    @Override
    public void undo() {
        musician.setRole(oldRole);
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    private String resolveRoleName(int role) {
        if (ensemble instanceof OrchestraEnsemble) {
            return ((OrchestraEnsemble) ensemble).getRoleName(role);
        } else if (ensemble instanceof JazzBandEnsemble) {
            return ((JazzBandEnsemble) ensemble).getRoleName(role);
        }
        return "unknown";
    }

    private String buildDescription() {
        return "Modify musician\u2019s instrument, " + musician.getMID() + ", " + roleName;
    }
}
