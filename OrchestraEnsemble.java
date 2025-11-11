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
    public String getEnsembleTypeDescription() {
        return "orchestra ensemble";
    }

    @Override
    public void showEnsemble() {
        System.out.println("Orchestra Ensemble " + getName() + " (" + getEnsembleID() + ")");

        Map<Integer, StringBuilder> roles = new LinkedHashMap<>();
        roles.put(VIOLINIST_ROLE, new StringBuilder());
        roles.put(CELLIST_ROLE, new StringBuilder());

        Iterator<Musician> iterator = getMusicians();
        while (iterator.hasNext()) {
            Musician m = iterator.next();
            int role = m.getRole();
            if (roles.containsKey(role)) {
                if (roles.get(role).length() > 0) {
                    roles.get(role).append("\n");
                }
                roles.get(role).append(m.getMID()).append(", ").append(m.getName());
            }
        }

        System.out.println("Violinist:");
        if (roles.get(VIOLINIST_ROLE).length() > 0) {
            System.out.println(roles.get(VIOLINIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Cellist:");
        if (roles.get(CELLIST_ROLE).length() > 0) {
            System.out.println(roles.get(CELLIST_ROLE).toString());
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
