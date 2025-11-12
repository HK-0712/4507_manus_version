public class ModifyInstrumentCommand implements EnsembleCommand {
    private final Ensemble ensemble;
    private final Musician musician;
    private final int newRole;
    private final String instrumentName;
    private final String description;
    private int previousRole;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public ModifyInstrumentCommand(Ensemble ensemble, Musician musician, int newRole) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.newRole = newRole;
        this.instrumentName = getInstrumentName(newRole);
        this.description = buildDescription();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Not required for current operations.
    }

    @Override
    public void execute() {
        previousRole = musician.getRole();
        musician.setRole(newRole);
        ensemble.updateMusicianRole();
        System.out.println("instrument is updated.");
    }

    @Override
    public void undo() {
        musician.setRole(previousRole);
        ensemble.updateMusicianRole();
    }

    @Override
    public String getDescription() {
        return description;
    }

    private String getInstrumentName(int role) {
        if (ensemble instanceof OrchestraEnsemble) {
            if (role == OrchestraEnsemble.VIOLINIST_ROLE) {
                return "violinist";
            }
            if (role == OrchestraEnsemble.CELLIST_ROLE) {
                return "cellist";
            }
        } else if (ensemble instanceof JazzBandEnsemble) {
            if (role == JazzBandEnsemble.PIANIST_ROLE) {
                return "pianist";
            }
            if (role == JazzBandEnsemble.SAXOPHONIST_ROLE) {
                return "saxophonist";
            }
            if (role == JazzBandEnsemble.DRUMMER_ROLE) {
                return "drummer";
            }
        }
        return "unknown";
    }

    private String buildDescription() {
        return "Modify musician's instrument, " + musician.getMID() + ", " + instrumentName;
    }
}
