// This is concrete command for change instrument
// It use Memento pattern for undo
public class ChangeInstrumentCmd implements EnsembleCommand {
    private final Ensemble ensemble;
    private final Musician musician;
    private final int newInstrument;
    private final String instrumentName;
    private final String description;
    private MusicianState memento;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public ChangeInstrumentCmd(Ensemble ensemble, Musician musician, int newInstrument) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.newInstrument = newInstrument;
        this.instrumentName = getInstrumentName(newInstrument);
        this.description = buildDescription();
    }

    @Override
    public void setManager(EnsembleService manager) {
        
    }

    @Override
    public void execute() {
        // create memento for save state before change
        memento = new MusicianState(musician);
        musician.setRole(newInstrument);
    }

    @Override
    public void undo() {
        // use memento to restore old state
        memento.restore();
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
