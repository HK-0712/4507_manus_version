import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrchestraEnsemble extends Ensemble {

    public static final int VIOLINIST_ROLE = 1;
    public static final int CELLIST_ROLE = 2;

    public OrchestraEnsemble(String eID) {
        super(eID);
    }

    @Override
    public void updateMusicianRole() {

    }

    @Override
    public String getEnsembleTypeDescription() {
        return "orchestra ensemble";
    }

    @Override
    public void showEnsemble() {
        System.out.println("Orchestra Ensemble " + getName() + " (" + getEnsembleID() + ")");

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

        System.out.println("Violinist:");
        if (roleGroups.get(VIOLINIST_ROLE).length() > 0) {
            System.out.println(roleGroups.get(VIOLINIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }

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
