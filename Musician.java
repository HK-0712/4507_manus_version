public class Musician implements Cloneable {

    // Memento class (Inner class for encapsulation)
    private class MusicianMemento {
        private final int role;

        public MusicianMemento(int role) {
            this.role = role;
        }

        // Package-private access for the Caretaker to get the state
        public int getRole() {
            return role;
        }
    }

    // Originator methods
    public Object saveState() {
        return new MusicianMemento(this.role);
    }

    public void restoreState(Object memento) {
        if (memento instanceof MusicianMemento) {
            this.role = ((MusicianMemento) memento).getRole();
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
