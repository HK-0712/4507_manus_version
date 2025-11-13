import java.util.*;

public abstract class Ensemble {
    private final String ensembleID;
    private String eName;
    private final AbstractList<Musician> musicians;

    public Ensemble(String eID) {
        this.ensembleID = eID;
        this.eName = "";
        this.musicians = new LinkedList<>();
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

    public void addMusician(Musician m) {
        if (m == null) {
            return;
        }
        Iterator<Musician> iterator = musicians.iterator();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            if (m.getMID().equals(musician.getMID())) {
                iterator.remove();
                break;
            }
        }
        musicians.add(m);
        updateMusicianRole();
    }

    public void dropMusician(Musician m) {
        if (m == null) {
            return;
        }
        Iterator<Musician> iterator = musicians.iterator();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            if (m.getMID().equals(musician.getMID())) {
                iterator.remove();
                break;
            }
        }
        updateMusicianRole();
    }

    public Iterator<Musician> getMusicians() {
        return musicians.iterator();
    }

    public abstract void updateMusicianRole();

    public abstract void showEnsemble();
}
