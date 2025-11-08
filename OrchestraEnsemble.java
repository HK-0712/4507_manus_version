import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrchestraEnsemble extends Ensemble {
    // Constants
    public static final int VIOLINIST_ROLE = 1;
    public static final int CELLIST_ROLE = 2;
    
    // Constructor
    public OrchestraEnsemble(String eID) {
        super(eID);
    }
    
    // Implementation of abstract methods
    @Override
    public void updateMusicianRole() {
        // Not used in this version - handled by main program
    }

    @Override
    public String getEnsembleTypeDescription() {
        return "orchestra ensemble";
    }
    
    @Override
    public void showEnsemble() {
        System.out.println("Orchestra Ensemble " + getName() + " (" + getEnsembleID() + ")");
        
        // Group musicians by role
        Map<Integer, StringBuilder> roleGroups = new LinkedHashMap<>();
        roleGroups.put(VIOLINIST_ROLE, new StringBuilder());
        roleGroups.put(CELLIST_ROLE, new StringBuilder());
        
        Iterator<Musician> iterator = getMusicians();
        while (iterator.hasNext()) {
            Musician m = iterator.next();
            int role = m.getRole();
            if (roleGroups.containsKey(role)) {
                if (roleGroups.get(role).length() > 0) {
                    roleGroups.get(role).append("\n");
                }
                roleGroups.get(role).append(m.getMID()).append(", ").append(m.getName());
            }
        }
        
        // Display violinists
        System.out.println("Violinist:");
        if (roleGroups.get(VIOLINIST_ROLE).length() > 0) {
            System.out.println(roleGroups.get(VIOLINIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }
        
        // Display cellists
        System.out.println("Cellist:");
        if (roleGroups.get(CELLIST_ROLE).length() > 0) {
            System.out.println(roleGroups.get(CELLIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }
    }
    
    public String getRoleName(int role) {
        switch (role) {
            case VIOLINIST_ROLE:
                return "violinist";
            case CELLIST_ROLE:
                return "cellist";
            default:
                return "unknown";
        }
    }
}
