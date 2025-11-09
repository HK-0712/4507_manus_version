public class DeleteMusicianCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    
    public DeleteMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void execute() {
        ensemble.dropMusician(musician);
        System.out.println("Musician is deleted.");
    }
    
    @Override
    public void undo() {
        ensemble.addMusician(musician);
    }
    
    @Override
    public String getDescription() {
        return "Delete musician, " + musician.getMID();
    }
}
