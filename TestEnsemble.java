import java.util.Scanner;

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
                    String targetName = target.getName();
                    String nameSuffix = (targetName != null && !targetName.isBlank()) ? " " + targetName : "";
                    System.out.println("The current ensemble is changed to " + eID + nameSuffix + ".");
                } else {
                    System.out.println("Ensemble " + eID + " not found!");
                }
            } else if (commandCode.equals("se")) {
                manager.showCurrentEnsemble();
            } else if (commandCode.equals("sa")) {
                manager.displayAllEnsembles();
            } else if (commandCode.equals("u")) {
                Command undoneCommand = manager.undo();
                if (undoneCommand != null) {
                    System.out.println("Command (" + undoneCommand.getDescription() + ") is undone.");
                }
            } else if (commandCode.equals("r")) {
                Command redoneCommand = manager.redo();
                if (redoneCommand != null) {
                    System.out.println("Command (" + redoneCommand.getDescription() + ") is redone.");
                }
            } else if (commandCode.equals("l")) {
                manager.listUndoRedo();
            } else {
                Command command = factory.createCommand(commandCode);
                if (command != null) {
                    // All commands that modify state should be executed through the manager
                    command.setManager(manager);
                    manager.executeCommand(command);
                    
                    // The command itself should handle its own output.
                    // The command's execute method should return a String message to be printed.
                    // The original code had the output logic here, which is incorrect for OCP.
                    // However, to match the original code's intent and the PDF output, we need to check the command type.
                    
                    // The output messages in the original code were:
                    // c: {ensemble type} is created. \n Changed current ensemble to {ID}.
                    // a: Musician is added.
                    // m: Instrument is updated.
                    // d: Musician is deleted.
                    // cn: Ensemble's name is updated.
                    
                    // To match the PDF, we need to ensure the output is correct.
                    // The 'c' command needs special handling to set the current ensemble.
                    if (commandCode.equals("c")) {
                        // The ensemble is the last one added to the list
                        Ensemble newEnsemble = manager.getEnsembles().get(manager.getEnsembles().size() - 1);
                        manager.setCurrentEnsemble(newEnsemble);
                        // The output message is handled by the command itself, but the current ensemble change message is missing.
                        // The original code had:
                        // System.out.println(newEnsemble.getEnsembleTypeDescription() + " is created.");
                        // System.out.println("Changed current ensemble to " + newEnsemble.getEnsembleID() + ".");
                        // We will assume the command prints the first line, and we print the second.
                        String newName = newEnsemble.getName();
                        String nameSuffix = (newName != null && !newName.isBlank()) ? " " + newName : "";
                        System.out.println("The current ensemble is changed to " + newEnsemble.getEnsembleID() + nameSuffix + ".");
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
