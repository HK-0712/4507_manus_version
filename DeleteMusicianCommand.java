public class DeleteMusicianCommand implements Command {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private Musician musician;
    
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
