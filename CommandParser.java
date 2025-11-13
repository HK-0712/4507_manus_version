import java.util.*;

public class CommandParser {
    private final EnsembleService manager;
    private final Scanner scanner;

    public CommandParser(EnsembleService manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public Command createCommand(String commandCode) {
        switch (commandCode) {
            case "c":
                return makeEnsembleCmd();
            case "s":
                return setCurrentEnsembleCommand();
            case "a":
                return addMusicianCommand();
            case "m":
                return changeInstrumentCmd();
            case "d":
                return deleteMusicianCommand();
            case "se":
                return new ShowEnsembleCommand(manager);
            case "sa":
                return new ShowAllEnsemblesCmd(manager);
            case "cn":
                return changeNameCommand();
            case "l":
                return new ShowHistoryCommand(manager);
            case "u":
                return new UndoCommand(manager);
            case "r":
                return new RedoCommand(manager);

            default:
                return null;
        }
    }

    private Command makeEnsembleCmd() {
        System.out.print("Enter music type (o = orchestra | j = jazz band) :- ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Ensemble ID:- ");
        String eID = scanner.nextLine().trim();

        System.out.print("Ensemble Name:- ");
        String eName = scanner.nextLine().trim();

        switch (type) {
            case "o":
                OrchestraEnsemble orchestra = new OrchestraEnsemble(eID);
                orchestra.setName(eName);
                return new MakeEnsembleCmd(manager.getEnsembles(), orchestra, "orchestra ensemble");
            case "j":
                JazzBandEnsemble jazzBand = new JazzBandEnsemble(eID);
                jazzBand.setName(eName);
                return new MakeEnsembleCmd(manager.getEnsembles(), jazzBand, "jazz band ensemble");
            default:
                System.out.println("Invalid music type.");
                return null;
        }
    }

    private Command addMusicianCommand() {
        Ensemble currentEnsemble = manager.getCurrentEnsemble();
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

    private Command changeInstrumentCmd() {
        Ensemble currentEnsemble = manager.getCurrentEnsemble();
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

        return new ChangeInstrumentCmd(currentEnsemble, musican, role);
    }

    private Command deleteMusicianCommand() {
        Ensemble currentEnsemble = manager.getCurrentEnsemble();
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

    private Command setCurrentEnsembleCommand() {
        System.out.print("Please input ensemble ID:- ");
        String eID = scanner.nextLine().trim();
        return new SetCurrentEnsembleCommand(manager, eID);
    }

    private Command changeNameCommand() {
        Ensemble currentEnsemble = manager.getCurrentEnsemble();
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input new name of the current ensemble:- ");
        String newName = scanner.nextLine().trim();

        return new ChangeNameCommand(currentEnsemble, newName);
    }
}
