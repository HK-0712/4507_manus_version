import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Memento {
    // 儲存每個 Musician 的 role 快照
    private Map<String, Integer> musicianRoles;
    // 儲存每個 Ensemble 的 name 快照
    private Map<String, String> ensembleNames;

    public Memento(LinkedList<Ensemble> ensembles) {
        this.musicianRoles = new HashMap<>();
        this.ensembleNames = new HashMap<>();
        
        // 保存所有 ensemble 的名稱和其中所有 musician 的 role
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
