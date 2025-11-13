import java.util.*;

public class OrchestraEnsemble extends Ensemble {
    public static final int VIOLINIST_ROLE = 1;
    public static final int CELLIST_ROLE = 2;

    public OrchestraEnsemble(String eID) {
        super(eID);
    }

    @Override
    public void updateMusicianRole() {
        Iterator<Musician> iterator = getMusicians();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            int role = musician.getRole();
            if (role != VIOLINIST_ROLE && role != CELLIST_ROLE) {
                musician.setRole(0);
            }
        }
    }

    @Override
    public void showEnsemble() {
        System.out.println("Orchestra Ensemble " + getName() + " (" + getEnsembleID() + ")");

        Map<Integer, StringBuilder> roles = new LinkedHashMap<>();
        roles.put(VIOLINIST_ROLE, new StringBuilder());
        roles.put(CELLIST_ROLE, new StringBuilder());

        Iterator<Musician> iterator = getMusicians();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            int role = musician.getRole();
            if (roles.containsKey(role)) {
                StringBuilder builder = roles.get(role);
                if (builder.length() > 0) {
                    builder.append("\n");
                }
                builder.append(musician.getMID()).append(", ").append(musician.getName());
            }
        }

        System.out.println("Violinist:");
        StringBuilder violinistList = roles.get(VIOLINIST_ROLE);
        if (violinistList != null && violinistList.length() > 0) {
            System.out.println(violinistList.toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Cellist:");
        StringBuilder cellistList = roles.get(CELLIST_ROLE);
        if (cellistList != null && cellistList.length() > 0) {
            System.out.println(cellistList.toString());
        } else {
            System.out.println("NIL");
        }
    }
}
