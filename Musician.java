public class Musician implements Cloneable {

    // Memento pattern - inner class to store musician state
    public static class MusicianMemento {
        private final int role;

        private MusicianMemento(int role) {
            this.role = role;
        }

        private int getRole() {
            return role;
        }
    }

    // Save current state
    public MusicianMemento save() {
        return new MusicianMemento(this.role);
    }

    // Restore from memento
    public void restore(MusicianMemento memento) {
        if (memento != null) {
            this.role = memento.getRole();
        }
    }

    // Legacy methods for backward compatibility
    public Object saveState() {
        return save();
    }

    public void restoreState(Object memento) {
        if (memento instanceof MusicianMemento) {
            restore((MusicianMemento) memento);
        }
    }

    private String mID;
    private String name;
    private int role;

    public Musician(String mID) {
        this.mID = mID;
        this.name = "";
        this.role = 0;
    }

    public String getMID() {
        return mID;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Musician clone() {
        try {
            return (Musician) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
