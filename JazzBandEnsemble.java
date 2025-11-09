import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JazzBandEnsemble extends Ensemble {

    public static final int PIANIST_ROLE = 1;
    public static final int SAXOPHONIST_ROLE = 2;
    public static final int DRUMMER_ROLE = 3;

    public JazzBandEnsemble(String eID) {
        super(eID);
    }

    @Override
    public void updateMusicianRole() {

    }

    @Override
    public String getEnsembleTypeDescription() {
        return "jazz band ensemble";
    }

    @Override
    public void showEnsemble() {
        System.out.println("Jazz Band Ensemble " + getName() + " (" + getEnsembleID() + ")");

        Map<Integer, StringBuilder> roleGroups = new LinkedHashMap<>();
        roleGroups.put(PIANIST_ROLE, new StringBuilder());
        roleGroups.put(SAXOPHONIST_ROLE, new StringBuilder());
        roleGroups.put(DRUMMER_ROLE, new StringBuilder());

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

        System.out.println("Pianist:");
        if (roleGroups.get(PIANIST_ROLE).length() > 0) {
            System.out.println(roleGroups.get(PIANIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Saxophonist:");
        if (roleGroups.get(SAXOPHONIST_ROLE).length() > 0) {
            System.out.println(roleGroups.get(SAXOPHONIST_ROLE).toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Drummer:");
        if (roleGroups.get(DRUMMER_ROLE).length() > 0) {
            System.out.println(roleGroups.get(DRUMMER_ROLE).toString());
        } else {
            System.out.println("NIL");
        }
    }

    public String getRoleName(int role) {
        switch (role) {
            case PIANIST_ROLE:
                return "pianist";
            case SAXOPHONIST_ROLE:
                return "saxophonist";
            case DRUMMER_ROLE:
                return "drummer";
            default:
                return "unknown";
        }
    }
}
