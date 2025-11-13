import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnsembleManager manager = new EnsembleManager();

        while (true) {

            CommandFactory factory = new CommandFactory(manager, scanner);

            System.out.println("");
            System.err.println("Music Ensembles Management System (MEMS)");
            System.out.println(
                    "c = create ensemble, s = set current ensemble, a = add musician, m = modify musician’s instrument,");
            System.out.println(
                    "d = delete musician, se = show ensemble, sa = display all ensembles, cn = change ensemble’s name,");
            System.out.println("u = undo, r = redo, l = list undo/redo, x = exit system");

            Ensemble current = manager.getCurrentEnsemble();
            if (current != null) {
                System.out
                        .println("The current ensemble is " + current.getEnsembleID() + " " + current.getName() + ".");
            } else {
                System.out.println("No current ensemble set.");
            }

            System.out.print("Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- ");
            String commandCode = scanner.nextLine().trim().toLowerCase();

            if (commandCode.equals("x")) {
                System.out.println("Exiting system.");
                break;
            } else {
                Command command = factory.createCommand(commandCode);
                if (command != null) {

                    command.setManager(manager);

                    if (commandCode.equals("se") || commandCode.equals("sa") || commandCode.equals("l") ||
                            commandCode.equals("u") || commandCode.equals("r")) {
                        command.execute();
                    } else if (commandCode.equals("s")) {

                        command.execute();
                        SetCurrentEnsembleCommand setCmd = (SetCurrentEnsembleCommand) command;
                        if (setCmd.isSuccess()) {
                            System.out.println("Changed current ensemble to " + setCmd.getEnsembleID() + ".");
                        } else {
                            System.out.println("Ensemble " + setCmd.getEnsembleID() + " is not found!!");
                        }
                    } else {

                        manager.executeCommand(command);

                        if (commandCode.equals("c")) {
                            Ensemble ens = manager.getEnsembles().get(manager.getEnsembles().size() - 1);

                            if (ens instanceof OrchestraEnsemble) {
                                System.out.println("Orchestra ensemble is created.");
                            } else if (ens instanceof JazzBandEnsemble) {
                                System.out.println("Jazz band ensemble is created.");
                            }

                            manager.setCurrentEnsemble(ens);
                            System.out.println("Current ensemble is changed to " + ens.getEnsembleID() + ".");
                        } else if (commandCode.equals("a")) {
                            System.out.println("Musician is added.");
                        } else if (commandCode.equals("m")) {
                            System.out.println("instrument is updated.");
                        } else if (commandCode.equals("d")) {
                            System.out.println("Musician is deleted.");
                        } else if (commandCode.equals("cn")) {
                            System.out.println("Ensemble's name is updated.");
                        }
                    }
                } else if (!commandCode.isEmpty()) {
                    System.out.println("Invalid command.");
                }
            }
        }
        scanner.close();
    }
}
