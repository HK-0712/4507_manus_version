public class Musician {
    // Attributes
    private String musicianID;
    private String mName;
    private int role;
    
    // Constructor
    public Musician(String mID) {
        this.musicianID = mID;
        this.mName = "";
        this.role = 0;
    }
    
    // Getter methods
    public String getMID() {
        return musicianID;
    }
    
    public int getRole() {
        return role;
    }
    
    public String getName() {
        return mName;
    }
    
    // Setter methods
    public void setRole(int role) {
        this.role = role;
    }
    
    public void setName(String name) {
        this.mName = name;
    }
}
