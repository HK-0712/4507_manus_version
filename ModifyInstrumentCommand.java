public class ModifyInstrumentCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    private int newrole;
    private final String instrmntName;
    private final String descr;
    
    private Musician.MusicianMemento memento;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public ModifyInstrumentCommand(Ensemble ensemble, Musician musician, int newRole) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.newrole = newRole;
        this.instrmntName = getInstrumentName(newRole);
        this.descr = getDesc();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        
        memento = musician.save();
        musician.setRole(newrole);
        System.out.println("instrument is updated.");
    }

    @Override
    public void undo() {
        
        if (memento != null) {
            musician.restore(memento);
        }
    }

    @Override
    public String getDescription() {
        return descr;
    }

    private String getInstrumentName(int role) {
        if (ensemble instanceof OrchestraEnsemble) {
            return ((OrchestraEnsemble) ensemble).getRoleName(role);
        } else if (ensemble instanceof JazzBandEnsemble) {
            return ((JazzBandEnsemble) ensemble).getRoleName(role);
        }
        return "unknown";
    }

    private String getDesc() {
        return "Modify musician's instrument, " + musician.getMID() + ", " + instrmntName;
    }
}
