import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class CommandFactory {
    private LinkedList<Ensemble> ensembles;
    private Ensemble currentEnsemble;
    private Scanner scanner;

    public CommandFactory(LinkedList<Ensemble> ensembles, Ensemble currentEnsemble, Scanner scanner) {
        this.ensembles = ensembles;
        this.currentEnsemble = currentEnsemble;
        this.scanner = scanner;
    }

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
            // case "s": // set current ensemble - handled by main program
            // case "se": // show current ensemble - handled by main program
            // case "sa": // display all ensembles - handled by main program
            // case "u": // undo - handled by main program
            // case "r": // redo - handled by main program
            // case "l": // list undo/redo - handled by main program
            // case "x": // exit - handled by main program
            default:
                return null;
        }
    }

    private Command createEnsembleCommand() {
        System.out.print("Enter music type (o = orchestra | j = jazz band) :- ");
        String type = scanner.nextLine().trim().toLowerCase();
        
        System.out.print("Ensemble ID:- ");
        String eID = scanner.nextLine().trim();
        
        System.out.print("Ensemble Name:- ");
        String eName = scanner.nextLine().trim();

        Ensemble newEnsemble = null;
        if (type.equals("o")) {
            newEnsemble = new OrchestraEnsemble(eID);
        } else if (type.equals("j")) {
            newEnsemble = new JazzBandEnsemble(eID);
        } else {
            System.out.println("Invalid music type.");
            return null;
        }

        newEnsemble.setName(eName);
        return new CreateEnsembleCommand(ensembles, newEnsemble);
    }

    private Command addMusicianCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician information (id, name):- ");
        String inputLine = scanner.nextLine().trim();
        // 檢查輸入是否包含逗號和空格，以符合 "id, name" 格式
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

        Musician newMusician = new Musician(mID);
        newMusician.setName(mName);

        // Role selection logic based on ensemble type
        if (currentEnsemble instanceof OrchestraEnsemble) {
            System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
            try {
                int instrument = Integer.parseInt(scanner.nextLine().trim());
                if (instrument == 1 || instrument == 2) {
                    newMusician.setRole(instrument);
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
                int instrument = Integer.parseInt(scanner.nextLine().trim());
                if (instrument >= 1 && instrument <= 3) {
                    newMusician.setRole(instrument);
                } else {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        } else {
            // Should not happen if OCP is followed, but for safety
            System.out.println("Unknown ensemble type.");
            return null;
        }

        return new AddMusicianCommand(currentEnsemble, newMusician);
    }

    private Command modifyInstrumentCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician ID:- ");
        String mID = scanner.nextLine().trim();

        Musician targetMusician = null;
        Iterator<Musician> it = currentEnsemble.getMusicians();
        while (it.hasNext()) {
            Musician m = it.next();
            if (m.getMID().equals(mID)) {
                targetMusician = m;
                break;
            }
        }

        if (targetMusician == null) {
            System.out.println("Musician not found in current ensemble.");
            return null;
        }

        int newRole = 0;
        if (currentEnsemble instanceof OrchestraEnsemble) {
            System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
            try {
                newRole = Integer.parseInt(scanner.nextLine().trim());
                if (newRole != 1 && newRole != 2) {
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
                newRole = Integer.parseInt(scanner.nextLine().trim());
                if (newRole < 1 || newRole > 3) {
                    System.out.println("Invalid instrument choice.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid instrument choice.");
                return null;
            }
        }

        return new ModifyInstrumentCommand(currentEnsemble, targetMusician, newRole);
    }

    private Command deleteMusicianCommand() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble set.");
            return null;
        }

        System.out.print("Please input musician ID:- ");
        String mID = scanner.nextLine().trim();

        Musician targetMusician = null;
        Iterator<Musician> it = currentEnsemble.getMusicians();
        while (it.hasNext()) {
            Musician m = it.next();
            if (m.getMID().equals(mID)) {
                targetMusician = m;
                break;
            }
        }

        if (targetMusician == null) {
            System.out.println("Musician not found in current ensemble.");
            return null;
        }

        return new DeleteMusicianCommand(currentEnsemble, targetMusician);
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
