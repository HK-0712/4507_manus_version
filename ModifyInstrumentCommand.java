public class ModifyInstrumentCommand implements Command {
    private Musician musician;
    private int oldRole;
    private int newRole;
    private String newRoleName;
    
    public ModifyInstrumentCommand(Musician musician, int oldRole, int newRole, String newRoleName) {
        this.musician = musician;
        this.oldRole = oldRole;
        this.newRole = newRole;
        this.newRoleName = newRoleName;
    }
    
    @Override
    public void execute() {
        musician.setRole(newRole);
    }
    
    @Override
    public void undo() {
        musician.setRole(oldRole);
    }
    
    @Override
    public String getDescription() {
        return "Modify musician's instrument, " + musician.getMID() + ", " + newRoleName;
    }
}
