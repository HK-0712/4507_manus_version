import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

public abstract class Ensemble implements Cloneable {
    // Attributes
    private String ensembleID;
    private String eName;
    private Map<String, Musician> musicians;
    
    // Constructor
    public Ensemble(String eID) {
        this.ensembleID = eID;
        this.eName = "";
        this.musicians = new TreeMap<String, Musician>();
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

    // Abstract method for OCP
    public abstract String getEnsembleTypeDescription();

    // Deep copy method for Memento
    @Override
    public Ensemble clone() {
        try {
            Ensemble cloned = (Ensemble) super.clone();
            // Deep copy the musicians map
            cloned.musicians = new TreeMap<String, Musician>();
            for (Map.Entry<String, Musician> entry : this.musicians.entrySet()) {
                cloned.musicians.put(entry.getKey(), entry.getValue().clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Methods for managing musicians
    public void addMusician(Musician m) {
        musicians.put(m.getMID(), m);
    }
    
    public void dropMusician(Musician m) {
        musicians.remove(m.getMID());
    }
    
    public Iterator<Musician> getMusicians() {
        return musicians.values().iterator();
    }
    
    // Abstract methods
    public abstract void updateMusicianRole();
    
    public abstract void showEnsemble();
}
