import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

// Factory pattern to create different command objects
public class CommandFactory {
    private LinkedList<Ensemble> ensembles;
    private Ensemble currentEnsemble;
    private Scanner scanner;

    public CommandFactory(LinkedList<Ensemble> ensembles, Ensemble currentEnsemble, Scanner scanner) {
        this.ensembles = ensembles;
        this.currentEnsemble = currentEnsemble;
        this.scanner = scanner;
    }

    // Factory method to create appropriate command based on user input
    public Command createCommand(String commandCode) {
        switch (commandCode) {
            case "c":
                return createEnsembleCommand();
            case "a":
                return addMusicianCommand();
            case "m":
                return modifyInstrumentCommand();
            case "d":
                return deleteMusicianCommand();
            case "cn":
                return changeNameCommand();

            default:
                return null;
        }
    }

    // Create different types of ensemble objects based on user choice
    private Command createEnsembleCommand() {
        System.out.print("Enter music type (o = orchestra | j = jazz band) :- ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Ensemble ID:- ");
        String eID = scanner.nextLine().trim();

        System.out.print("Ensemble Name:- ");
        String eName = scanner.nextLine().trim();

        Ensemble ens = null;
        if (type.equals("o")) {
            ens = new OrchestraEnsemble(eID);
        } else if (type.equals("j")) {
            ens = new JazzBandEnsemble(eID);
        } else {
            System.out.println("Invalid music type.");
            return null;
        }

        ens.setName(eName);
        return new CreateEnsembleCommand(ensembles, ens);
    }

    private Command addMusicianCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician information (id, name):- ");
        String inputLine = scanner.nextLine().trim();

        if (!inputLine.contains(", ")) {
            System.out.println("Invalid musician information format.");
            return null;
        }
        String[] info = inputLine.split(", ");
        if (info.length != 2) {
            System.out.println("Invalid musician information format.");
            return null;
        }
        String mID = info[0];
        String mName = info[1];

        Musician m = new Musician(mID);
        m.setName(mName);

        if (currentEnsemble instanceof OrchestraEnsemble) {
            System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
            try {
                int inst = Integer.parseInt(scanner.nextLine().trim());
                if (inst == 1 || inst == 2) {
                    m.setRole(inst);
                } else {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        } else if (currentEnsemble instanceof JazzBandEnsemble) {
            System.out.print("Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- ");
            try {
                int inst = Integer.parseInt(scanner.nextLine().trim());
                if (inst >= 1 && inst <= 3) {
                    m.setRole(inst);
                } else {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        } else {

            System.out.println("Unknown ensemble type.");
            return null;
        }

        return new AddMusicianCommand(currentEnsemble, m);
    }

    private Command modifyInstrumentCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician ID:- ");
        String mID = scanner.nextLine().trim();

        Musician musican = null;
        Iterator<Musician> it = currentEnsemble.getMusicians();
        while (it.hasNext()) {
            Musician m = it.next();
            if (m.getMID().equals(mID)) {
                musican = m;
                break;
            }
        }

        if (musican == null) {
            System.out.println("Musician not found in current ensemble.");
            return null;
        }

        int role = 0;
        if (currentEnsemble instanceof OrchestraEnsemble) {
            System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
            try {
                role = Integer.parseInt(scanner.nextLine().trim());
                if (role != 1 && role != 2) {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        } else if (currentEnsemble instanceof JazzBandEnsemble) {
            System.out.print("Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- ");
            try {
                role = Integer.parseInt(scanner.nextLine().trim());
                if (role < 1 || role > 3) {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        }

        return new ModifyInstrumentCommand(currentEnsemble, musican, role);
    }

    private Command deleteMusicianCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician ID:- ");
        String mID = scanner.nextLine().trim();

        Musician musican = null;
        Iterator<Musician> it = currentEnsemble.getMusicians();
        while (it.hasNext()) {
            Musician m = it.next();
            if (m.getMID().equals(mID)) {
                musican = m;
                break;
            }
        }

        if (musican == null) {
            System.out.println("Musician not found in current ensemble.");
            return null;
        }

        return new DeleteMusicianCommand(currentEnsemble, musican);
    }

    private Command changeNameCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input new name of the current ensemble:- ");
        String newName = scanner.nextLine().trim();

        return new ChangeNameCommand(currentEnsemble, newName);
    }
}
