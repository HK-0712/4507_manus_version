import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

public abstract class Ensemble implements Cloneable {

    private String ensembleID;
    private String eName;
    private Map<String, Musician> musicians;

    public Ensemble(String eID) {
        this.ensembleID = eID;
        this.eName = "";
        this.musicians = new TreeMap<String, Musician>();
    }

    public String getEnsembleID() {
        return ensembleID;
    }

    public String getName() {
        return eName;
    }

    public void setName(String name) {
        this.eName = name;
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
