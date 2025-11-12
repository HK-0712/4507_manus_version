public class MusicianMemento {
    private final int role;
    private final Musician musician;

    public MusicianMemento(Musician musician) {
        this.musician = musician;
        this.role = musician.getRole();
    }

    public void restore() {
        musician.setRole(role);
    }
}
