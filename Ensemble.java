import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

public abstract class Ensemble implements Cloneable {

    private String eID;
    private String name;
    private Map<String, Musician> musicians;

    public Ensemble(String eID) {
        this.eID = eID;
        this.name = "";
        this.musicians = new TreeMap<String, Musician>();
    }

    public String getEnsembleID() {
        return eID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getEnsembleTypeDescription();

    @Override
    public Ensemble clone() {
        try {
            Ensemble cloned = (Ensemble) super.clone();

            cloned.musicians = new TreeMap<String, Musician>();
            for (Map.Entry<String, Musician> entry : this.musicians.entrySet()) {
                cloned.musicians.put(entry.getKey(), entry.getValue().clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMusician(Musician m) {
        musicians.put(m.getMID(), m);
    }

    public void dropMusician(Musician m) {
        musicians.remove(m.getMID());
    }

    public Iterator<Musician> getMusicians() {
        return musicians.values().iterator();
    }

    public abstract void updateMusicianRole();

    public abstract void showEnsemble();
}
