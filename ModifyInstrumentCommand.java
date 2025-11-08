public class ModifyInstrumentCommand implements Command {
    private EnsembleManager manager;
    private Musician musician;
    private int oldRole;
    private int newRole;
    // private String newRoleName; // Removed to enforce OCP
    
    public ModifyInstrumentCommand(Musician musician, int newRole) {
        this.musician = musician;
        this.newRole = newRole;
        this.oldRole = musician.getRole();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
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
        // This command is executed on the current ensemble, which is stored in the manager.
        // We need to find the current ensemble to get the role name.
        Ensemble currentEnsemble = manager.getCurrentEnsemble();
        String roleName = "";
        if (currentEnsemble instanceof OrchestraEnsemble) {
            roleName = ((OrchestraEnsemble) currentEnsemble).getRoleName(newRole);
        } else if (currentEnsemble instanceof JazzBandEnsemble) {
            roleName = ((JazzBandEnsemble) currentEnsemble).getRoleName(newRole);
        }
        return "Modify musician's instrument, " + musician.getMID() + ", " + roleName;
    }
}
