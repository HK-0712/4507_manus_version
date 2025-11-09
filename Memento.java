import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Memento {

    private Map<String, Integer> musicianRoles;

    private Map<String, String> ensembleNames;

    public Memento(LinkedList<Ensemble> ensembles) {
        this.musicianRoles = new HashMap<>();
        this.ensembleNames = new HashMap<>();

        for (Ensemble e : ensembles) {
            ensembleNames.put(e.getEnsembleID(), e.getName());

            java.util.Iterator<Musician> it = e.getMusicians();
            while (it.hasNext()) {
                Musician m = it.next();
                musicianRoles.put(m.getMID(), m.getRole());
            }
        }
    }

    public Map<String, Integer> getMusicianRoles() {
        return musicianRoles;
    }

    public Map<String, String> getEnsembleNames() {
        return ensembleNames;
    }
}
