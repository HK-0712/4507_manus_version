public class Musician {
    private final String musicianID;
    private String mName;
    private int role;

    public Musician(String mID) {
        this.musicianID = mID;
        this.mName = "";
        this.role = 0;
    }

    public String getMID() {
        return musicianID;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
