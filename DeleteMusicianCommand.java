public class DeleteMusicianCommand implements Command {
    private Ensemble ensemble;
    private Musician musician;
    
    public DeleteMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
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
