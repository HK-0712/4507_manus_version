public class ModifyInstrumentCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    private int newRole;
    private final String roleName;
    private final String description;
    private Memento memento;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

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

        if (manager != null) {
            memento = manager.saveState();
        }
        musician.setRole(newRole);
        System.out.println("instrument is updated.");
    }

    @Override
    public void undo() {

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
