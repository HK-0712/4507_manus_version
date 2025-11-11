import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;

public abstract class Ensemble implements Cloneable {
    public static class EnsembleMemento {
        private final String name;
        private final Ensemble ensemble;

        private EnsembleMemento(Ensemble ensemble) {
            this.ensemble = ensemble;
            this.name = ensemble.name;
        }

        private void restore() {
            ensemble.setName(name);
        }
    }

    public EnsembleMemento save() {
        return new EnsembleMemento(this);
    }

    public void restore(EnsembleMemento memento) {
        if (memento != null) {
            memento.restore();
        }
    }

    public Object saveState() {
        return save();
    }

    public void restoreState(Object memento) {
        if (memento instanceof EnsembleMemento) {
            restore((EnsembleMemento) memento);
        }
    }

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

    public abstract void showEnsemble();
}
