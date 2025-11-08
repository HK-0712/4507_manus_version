public class AddMusicianCommand implements Command {
    private Ensemble ensemble;
    private Musician musician;
    private String roleName;
    
    public AddMusicianCommand(Ensemble ensemble, Musician musician, String roleName) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.roleName = roleName;
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
        return "Add musician, " + musician.getMID() + ", " + musician.getName() + ", " + roleName;
    }
}
