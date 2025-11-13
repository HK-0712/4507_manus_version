// This is concrete ensemble for jazz band
// It extends the base Ensemble class

import java.util.*;

public class JazzBandEnsemble extends Ensemble {
    public static final int PIANIST_ROLE = 1;
    public static final int SAXOPHONIST_ROLE = 2;
    public static final int DRUMMER_ROLE = 3;

    public JazzBandEnsemble(String eID) {
        super(eID);
    }

    @Override
    public void updateMusicianRole() {
        Iterator<Musician> iterator = getMusicians();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            int role = musician.getRole();
            if (role != PIANIST_ROLE && role != SAXOPHONIST_ROLE && role != DRUMMER_ROLE) {
                musician.setRole(0);
            }
        }
    }

    @Override
    public void showEnsemble() {
        System.out.println("Jazz Band Ensemble " + getName() + " (" + getEnsembleID() + ")");

        Map<Integer, StringBuilder> roles = new LinkedHashMap<>();
        roles.put(PIANIST_ROLE, new StringBuilder());
        roles.put(SAXOPHONIST_ROLE, new StringBuilder());
        roles.put(DRUMMER_ROLE, new StringBuilder());

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

        System.out.println("Pianist:");
        StringBuilder pianistList = roles.get(PIANIST_ROLE);
        if (pianistList != null && pianistList.length() > 0) {
            System.out.println(pianistList.toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Saxophonist:");
        StringBuilder saxophonistList = roles.get(SAXOPHONIST_ROLE);
        if (saxophonistList != null && saxophonistList.length() > 0) {
            System.out.println(saxophonistList.toString());
        } else {
            System.out.println("NIL");
        }

        System.out.println("Drummer:");
        StringBuilder drummerList = roles.get(DRUMMER_ROLE);
        if (drummerList != null && drummerList.length() > 0) {
            System.out.println(drummerList.toString());
        } else {
            System.out.println("NIL");
        }
    }
}
