import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;

public abstract class Ensemble {
    // Attributes
    private String ensembleID;
    private String eName;
    private AbstractList<Musician> musicians;
    
    // Constructor
    public Ensemble(String eID) {
        this.ensembleID = eID;
        this.eName = "";
        this.musicians = new LinkedList<Musician>();
    }
    
    // Getter methods
    public String getEnsembleID() {
        return ensembleID;
    }
    
    public String getName() {
        return eName;
    }
    
    // Setter method
    public void setName(String name) {
        this.eName = name;
    }
    
    // Methods for managing musicians
    public void addMusician(Musician m) {
        musicians.add(m);
    }
    
    public void dropMusician(Musician m) {
        musicians.remove(m);
    }
    
    public Iterator<Musician> getMusicians() {
        return musicians.iterator();
    }
    
    // Abstract methods
    public abstract void updateMusicianRole();
    
    public abstract void showEnsemble();
}
