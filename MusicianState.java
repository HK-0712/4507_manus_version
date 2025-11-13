// This is Memento pattern
// It save the state of musician
// So we can restore it later for undo
public class MusicianState {
    private final int role;
    private final Musician musician;

    public MusicianState(Musician musician) {
        this.musician = musician;
        this.role = musician.getRole();
    }

    public void restore() {
        musician.setRole(role);
    }
}
