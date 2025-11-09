
public class AddMusicianCommand implements EnsembleCommand {

    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    private final String instrumnt;
    private final String desc;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public AddMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.instrumnt = getRoleStr(musician.getRole());
        this.desc = makeDesc();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        ensemble.addMusician(musician);
        System.out.println("Musician is added.");
    }

    @Override
    public void undo() {
        ensemble.dropMusician(musician);
    }

    @Override
    public String getDescription() {
        return desc;
    }

    private String makeDesc() {
        return "Add musician, " + musician.getMID() + ", " + musician.getName() + ", " + instrumnt;
    }

    private String getRoleStr(int role) {
        if (ensemble instanceof OrchestraEnsemble) {
            return ((OrchestraEnsemble) ensemble).getRoleName(role);
        } else if (ensemble instanceof JazzBandEnsemble) {
            return ((JazzBandEnsemble) ensemble).getRoleName(role);
        }
        return "unknown";
    }
}
