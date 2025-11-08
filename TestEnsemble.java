import java.util.Scanner;

public class TestEnsemble {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnsembleManager manager = new EnsembleManager();
        
        System.out.println("Music Ensembles Management System (MEMS)");
        
        while (true) {
            // CommandFactory needs the current state of the manager to create commands correctly
            CommandFactory factory = new CommandFactory(manager.getEnsembles(), manager.getCurrentEnsemble(), scanner);

            // 輸出命令選單
            System.out.println("c = create ensemble, s = set current ensemble, a = add musician, m = modify musician’s instrument,");
            System.out.println("d = delete musician, se = show ensemble, sa = display all ensembles, cn = change ensemble’s name,");
            System.out.println("u = undo, r = redo, l = list undo/redo, x = exit system");
            
            // 輸出當前樂團狀態
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
                manager.undo(); // 輸出已在 EnsembleManager 中處理
            } else if (commandCode.equals("r")) {
                manager.redo(); // 輸出已在 EnsembleManager 中處理
            } else if (commandCode.equals("l")) {
                manager.listUndoRedo();
            } else {
                Command command = factory.createCommand(commandCode);
                if (command != null) {
                    // All commands that modify state should be executed through the manager
                    command.setManager(manager);
                    manager.executeCommand(command);
                    
                    // The command itself should handle its own output.
                    // The 'c' command needs special handling to set the current ensemble and print the change message.
                    if (commandCode.equals("c")) {
                        // The ensemble is the last one added to the list
                        Ensemble newEnsemble = manager.getEnsembles().get(manager.getEnsembles().size() - 1);
                        manager.setCurrentEnsemble(newEnsemble);
                        
                    // The output message is handled by the command itself, but the current ensemble change message is missing.
                    // 根據 PDF 範例，輸出應為 "Current ensemble is changed to E001."
                    System.out.println("Current ensemble is changed to " + newEnsemble.getEnsembleID() + ".");
                } else if (commandCode.equals("a")) {
                    // 輸出已在 AddMusicianCommand 中處理
                } else if (commandCode.equals("m")) {
                    // 輸出已在 ModifyInstrumentCommand 中處理
                } else if (commandCode.equals("d")) {
                    // 輸出已在 DeleteMusicianCommand 中處理
                } else if (commandCode.equals("cn")) {
                    // 輸出已在 ChangeNameCommand 中處理
                }
                } else if (!commandCode.isEmpty()) {
                    System.out.println("Invalid command.");
                }
            }
        }
        scanner.close();
    }
}
