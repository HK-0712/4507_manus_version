public class ModifyInstrumentCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    private int newRole;
    private final String roleName;
    private final String description;
    private Memento memento; // Memento Pattern: 儲存執行前的狀態

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    // private String newRoleName; // Removed to enforce OCP
    
    public ModifyInstrumentCommand(Ensemble ensemble, Musician musician, int newRole) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.newRole = newRole;
        this.roleName = resolveRoleName(newRole);
        this.description = buildDescription();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void execute() {
        // Memento Pattern: 在執行前保存狀態
        if (manager != null) {
            memento = manager.saveState();
        }
        musician.setRole(newRole);
    }
    
    @Override
    public void undo() {
        // Memento Pattern: 使用 Memento 還原狀態
        if (manager != null && memento != null) {
            manager.restoreState(memento);
        }
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
