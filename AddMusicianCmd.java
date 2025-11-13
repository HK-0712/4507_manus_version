// This is a concrete command
// It for add musician
public class AddMusicianCmd implements EnsembleCommand {
    private final Ensemble ensemble;
    private final Musician musician;
    private final String instrumentLabel;
    private final String description;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public AddMusicianCmd(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.instrumentLabel = getRoleStr(musician.getRole());
        this.description = buildDescription();
    }

    @Override
    public void setManager(EnsembleService manager) {
        
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
        return "Add musician, " + musician.getMID() + ", " + musician.getName() + ", " + instrumentLabel;
    }

    private String getRoleStr(int role) {
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
}
