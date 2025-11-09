public class Musician implements Cloneable {

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
