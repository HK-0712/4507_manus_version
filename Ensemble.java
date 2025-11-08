import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;

public abstract class Ensemble implements Cloneable {
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

    // Abstract method for OCP
    public abstract String getEnsembleTypeDescription();

    // Deep copy method for Memento
    @Override
    public Ensemble clone() {
        try {
            Ensemble cloned = (Ensemble) super.clone();
            // Deep copy the musicians list
            cloned.musicians = new LinkedList<Musician>();
            Iterator<Musician> it = this.musicians.iterator();
            while (it.hasNext()) {
                cloned.musicians.add(it.next().clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
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
