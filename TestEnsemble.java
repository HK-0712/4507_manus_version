import java.util.*;

public class TestEnsemble {
    private static LinkedList<Ensemble> ensembles = new LinkedList<>();
    private static Ensemble currentEnsemble = null;
    private static Stack<Command> undoStack = new Stack<>();
    private static Stack<Command> redoStack = new Stack<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        String command;
        
        while (true) {
            showMenu();
            System.out.print("Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- ");
            command = scanner.nextLine().trim();
            
            switch (command) {
                case "c":
                    createEnsemble();
                    break;
                case "s":
                    setCurrentEnsemble();
                    break;
                case "a":
                    addMusician();
                    break;
                case "m":
                    modifyInstrument();
                    break;
                case "d":
                    deleteMusician();
                    break;
                case "se":
                    showEnsemble();
                    break;
                case "sa":
                    showAllEnsembles();
                    break;
                case "cn":
                    changeEnsembleName();
                    break;
                case "u":
                    undo();
                    break;
                case "r":
                    redo();
                    break;
                case "l":
                    listUndoRedo();
                    break;
                case "x":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
    
    private static void showMenu() {
        System.out.println("Music Ensembles Management System (MEMS)");
        System.out.println("c = create ensemble, s = set current ensemble, a = add musician, m = modify musician's instrument,");
        System.out.println("d = delete musician, se = show ensemble, sa = display all ensembles, cn = change ensemble's name,");
        System.out.println("u = undo, r = redo, l = list undo/redo, x = exit system");
        if (currentEnsemble != null) {
            System.out.println("The current ensemble is " + currentEnsemble.getEnsembleID() + " " + currentEnsemble.getName() + ".");
        }
    }
    
    private static void createEnsemble() {
        System.out.print("Enter music type (o = orchestra | j = jazz band) :- ");
        String type = scanner.nextLine().trim();
        
        System.out.print("Ensemble ID:- ");
        String id = scanner.nextLine().trim();
        
        System.out.print("Ensemble Name:- ");
        String name = scanner.nextLine().trim();
        
        Ensemble ensemble;
        if (type.equals("o")) {
            ensemble = new OrchestraEnsemble(id);
            System.out.println("Orchestra ensemble is created.");
        } else {
            ensemble = new JazzBandEnsemble(id);
            System.out.println("Jazz band ensemble is created.");
        }
        
        ensemble.setName(name);
        
        Command cmd = new CreateEnsembleCommand(ensembles, ensemble, type);
        executeCommand(cmd);
        
        currentEnsemble = ensemble;
        System.out.println("Current ensemble is changed to " + id + ".");
    }
    
    private static void setCurrentEnsemble() {
        System.out.print("Please input ensemble ID:- ");
        String id = scanner.nextLine().trim();
        
        for (Ensemble e : ensembles) {
            if (e.getEnsembleID().equals(id)) {
                currentEnsemble = e;
                System.out.println("Changed current ensemble to " + id + ".");
                return;
            }
        }
        
        System.out.println("Ensemble " + id + " is not found!!");
    }
    
    private static void addMusician() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble!");
            return;
        }
        
        System.out.print("Please input musician information (id, name):- ");
        String input = scanner.nextLine().trim();
        String[] parts = input.split(",");
        
        if (parts.length != 2) {
            System.out.println("Invalid input format!");
            return;
        }
        
        String musicianId = parts[0].trim();
        String musicianName = parts[1].trim();
        
        Musician musician = new Musician(musicianId);
        musician.setName(musicianName);
        
        if (currentEnsemble instanceof OrchestraEnsemble) {
            System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
            int role = Integer.parseInt(scanner.nextLine().trim());
            musician.setRole(role);
            String roleName = ((OrchestraEnsemble) currentEnsemble).getRoleName(role);
            
            Command cmd = new AddMusicianCommand(currentEnsemble, musician, roleName);
            executeCommand(cmd);
        } else if (currentEnsemble instanceof JazzBandEnsemble) {
            System.out.print("Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- ");
            int role = Integer.parseInt(scanner.nextLine().trim());
            musician.setRole(role);
            String roleName = ((JazzBandEnsemble) currentEnsemble).getRoleName(role);
            
            Command cmd = new AddMusicianCommand(currentEnsemble, musician, roleName);
            executeCommand(cmd);
        }
        
        System.out.println("Musician is added.");
    }
    
    private static void modifyInstrument() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble!");
            return;
        }
        
        System.out.print("Please input musician ID:- ");
        String musicianId = scanner.nextLine().trim();
        
        Iterator<Musician> iterator = currentEnsemble.getMusicians();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            if (musician.getMID().equals(musicianId)) {
                int oldRole = musician.getRole();
                
                if (currentEnsemble instanceof OrchestraEnsemble) {
                    System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
                    int newRole = Integer.parseInt(scanner.nextLine().trim());
                    String roleName = ((OrchestraEnsemble) currentEnsemble).getRoleName(newRole);
                    
                    Command cmd = new ModifyInstrumentCommand(musician, oldRole, newRole, roleName);
                    executeCommand(cmd);
                } else if (currentEnsemble instanceof JazzBandEnsemble) {
                    System.out.print("Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- ");
                    int newRole = Integer.parseInt(scanner.nextLine().trim());
                    String roleName = ((JazzBandEnsemble) currentEnsemble).getRoleName(newRole);
                    
                    Command cmd = new ModifyInstrumentCommand(musician, oldRole, newRole, roleName);
                    executeCommand(cmd);
                }
                
                System.out.println("Instrument is updated.");
                return;
            }
        }
        
        System.out.println("Musician not found!");
    }
    
    private static void deleteMusician() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble!");
            return;
        }
        
        System.out.print("Please input musician ID:- ");
        String musicianId = scanner.nextLine().trim();
        
        Iterator<Musician> iterator = currentEnsemble.getMusicians();
        while (iterator.hasNext()) {
            Musician musician = iterator.next();
            if (musician.getMID().equals(musicianId)) {
                Command cmd = new DeleteMusicianCommand(currentEnsemble, musician);
                executeCommand(cmd);
                System.out.println("Musician is deleted.");
                return;
            }
        }
        
        System.out.println("Musician not found!");
    }
    
    private static void showEnsemble() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble!");
            return;
        }
        
        currentEnsemble.showEnsemble();
    }
    
    private static void showAllEnsembles() {
        for (Ensemble e : ensembles) {
            if (e instanceof OrchestraEnsemble) {
                System.out.println("Orchestra Ensemble " + e.getName() + " (" + e.getEnsembleID() + ")");
            } else if (e instanceof JazzBandEnsemble) {
                System.out.println("Jazz Band Ensemble " + e.getName() + " (" + e.getEnsembleID() + ")");
            }
        }
    }
    
    private static void changeEnsembleName() {
        if (currentEnsemble == null) {
            System.out.println("No current ensemble!");
            return;
        }
        
        System.out.print("Please input new name of the current ensemble:- ");
        String newName = scanner.nextLine().trim();
        
        String oldName = currentEnsemble.getName();
        Command cmd = new ChangeNameCommand(currentEnsemble, oldName, newName);
        executeCommand(cmd);
        
        System.out.println("Ensemble's name is updated.");
    }
    
    private static void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo!");
            return;
        }
        
        Command cmd = undoStack.pop();
        cmd.undo();
        redoStack.push(cmd);
        
        System.out.println("Command (" + cmd.getDescription() + ") is undone.");
        
        // Check if we need to change current ensemble after undo
        if (cmd instanceof CreateEnsembleCommand) {
            // Find the previous ensemble or set to null
            if (!ensembles.isEmpty()) {
                currentEnsemble = ensembles.getLast();
                System.out.println("The current ensemble is changed to " + currentEnsemble.getEnsembleID() + " " + currentEnsemble.getName() + ".");
            } else {
                currentEnsemble = null;
            }
        } else if (cmd instanceof AddMusicianCommand || cmd instanceof DeleteMusicianCommand) {
            // Check if current ensemble still exists
            boolean found = false;
            for (Ensemble e : ensembles) {
                if (e == currentEnsemble) {
                    found = true;
                    break;
                }
            }
            if (!found && !ensembles.isEmpty()) {
                currentEnsemble = ensembles.getLast();
                System.out.println("The current ensemble is changed to " + currentEnsemble.getEnsembleID() + " " + currentEnsemble.getName() + ".");
            }
        }
    }
    
    private static void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo!");
            return;
        }
        
        Command cmd = redoStack.pop();
        cmd.execute();
        undoStack.push(cmd);
        
        System.out.println("Command (" + cmd.getDescription() + ") is redone.");
        
        // Check if we need to change current ensemble after redo
        if (cmd instanceof CreateEnsembleCommand) {
            // The ensemble was just re-created, make it current
            for (Ensemble e : ensembles) {
                if (cmd.getDescription().contains(e.getEnsembleID())) {
                    currentEnsemble = e;
                    System.out.println("The current ensemble is changed to " + currentEnsemble.getEnsembleID() + ".");
                    break;
                }
            }
        }
    }
    
    private static void listUndoRedo() {
        System.out.println("Undo List");
        for (Command cmd : undoStack) {
            System.out.println(cmd.getDescription());
        }
        System.out.println("-- End of undo list --");
        
        System.out.println("Redo List");
        // Print redo stack in reverse order
        for (int i = redoStack.size() - 1; i >= 0; i--) {
            System.out.println(redoStack.get(i).getDescription());
        }
        System.out.println("-- End of redo list --");
    }
    
    private static void executeCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear(); // Clear redo stack when a new command is executed
    }
}

