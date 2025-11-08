import java.util.Scanner;
import java.util.AbstractList;
import java.util.Iterator;

public class TestEnsemble {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnsembleManager manager = new EnsembleManager();
        
        System.out.println("Music Ensembles Management System (MEMS)");
        
        while (true) {
            // CommandFactory needs the current state of the manager to create commands correctly
            CommandFactory factory = new CommandFactory(manager.getEnsembles(), manager.getCurrentEnsemble(), scanner);

            System.out.println("c = create ensemble, s = set current ensemble, a = add musician, m = modify musician’s instrument,");
            System.out.println("d = delete musician, se = show ensemble, sa = display all ensembles, cn = change ensemble’s name,");
            System.out.println("u = undo, r = redo, l = list undo/redo, x = exit system");
            
            Ensemble current = manager.getCurrentEnsemble();
            if (current != null) {
                System.out.println("The current ensemble is " + current.getEnsembleID() + " " + current.getName() + ".");
            } else {
                System.out.println("No current ensemble set.");
            }

            System.out.print("Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- ");
            String commandCode = scanner.nextLine().trim().toLowerCase();

            if (commandCode.equals("x")) {
                System.out.println("Exiting system.");
                break;
            } else if (commandCode.equals("s")) {
                System.out.print("Please input ensemble ID:- ");
                String eID = scanner.nextLine().trim();
                Ensemble target = null;
                for (Ensemble e : manager.getEnsembles()) {
                    if (e.getEnsembleID().equals(eID)) {
                        target = e;
                        break;
                    }
                }
                if (target != null) {
                    manager.setCurrentEnsemble(target);
                    System.out.println("Changed current ensemble to " + eID + ".");
                } else {
                    System.out.println("Ensemble " + eID + " not found!");
                }
            } else if (commandCode.equals("se")) {
                manager.showCurrentEnsemble();
            } else if (commandCode.equals("sa")) {
                manager.displayAllEnsembles();
            } else if (commandCode.equals("u")) {
                manager.undo();
            } else if (commandCode.equals("r")) {
                manager.redo();
            } else if (commandCode.equals("l")) {
                manager.listUndoRedo();
            } else {
                Command command = factory.createCommand(commandCode);
                if (command != null) {
                    // All commands that modify state should be executed through the manager
                    command.setManager(manager);
                    manager.executeCommand(command);
                    
                    // The command itself should handle its own output, but for simplicity and to match the original code's intent:
                    if (commandCode.equals("c")) {
                        // The ensemble is the last one added to the list
                        Ensemble newEnsemble = manager.getEnsembles().get(manager.getEnsembles().size() - 1);
                        manager.setCurrentEnsemble(newEnsemble);
                        System.out.println(newEnsemble.getEnsembleTypeDescription() + " is created.");
                        System.out.println("Changed current ensemble to " + newEnsemble.getEnsembleID() + ".");
                    } else if (commandCode.equals("a")) {
                        System.out.println("Musician is added.");
                    } else if (commandCode.equals("m")) {
                        System.out.println("Instrument is updated.");
                    } else if (commandCode.equals("d")) {
                        System.out.println("Musician is deleted.");
                    } else if (commandCode.equals("cn")) {
                        System.out.println("Ensemble's name is updated.");
                    }
                } else if (!commandCode.isEmpty()) {
                    System.out.println("Invalid command.");
                }
            }
        }
        scanner.close();
    }
}
