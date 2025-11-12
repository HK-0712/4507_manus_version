import java.util.LinkedList;
import java.util.Stack;

public class EnsembleManager {
    private final LinkedList<Ensemble> ensembles;
    private Ensemble currentEnsemble;
    private final Stack<Command> history;
    private final Stack<Command> redoStack;

    public EnsembleManager() {
        this.ensembles = new LinkedList<>();
        this.currentEnsemble = null;
        this.history = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        redoStack.clear();

        command.execute();
        history.push(command);
    }

    public Command undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();

            boolean switched = false;
            if (command instanceof CreateEnsembleCommand) {

                boolean currExists = false;
                for (Ensemble e : ensembles) {
                    if (e.equals(currentEnsemble)) {
                        currExists = true;
                        break;
                    }
                }

                if (!currExists && !ensembles.isEmpty()) {

                    this.currentEnsemble = ensembles.getFirst();
                    switched = true;
                } else if (ensembles.isEmpty()) {

                    this.currentEnsemble = null;
                    switched = true;
                } else if (currExists) {

                    switched = true;
                }
            }

            if (!(command instanceof CreateEnsembleCommand) && command instanceof EnsembleCommand) {
                Ensemble ens = ((EnsembleCommand) command).getEnsemble();

                boolean ensExists = ensembles.contains(ens);

                if (ensExists && !ens.equals(currentEnsemble)) {

                    this.currentEnsemble = ens;
                    switched = true;
                }
            }

            redoStack.push(command);

            System.out.println("Command (" + command.getDescription() + ") is undone.");

            if (switched) {
                if (currentEnsemble != null) {
                    System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID()
                            + " " + this.currentEnsemble.getName() + ".");
                }
            }

            return command;
        } else {
            System.out.println("Undo List is empty.");
        }
        return null;
    }

    public Command redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            history.push(command);

            System.out.println("Command (" + command.getDescription() + ") is redone.");

            boolean switched = false;

            if (command instanceof CreateEnsembleCommand) {

                Ensemble ens = ((CreateEnsembleCommand) command).getEnsemble();
                this.currentEnsemble = ens;
                switched = true;
            }

            else if (command instanceof EnsembleCommand) {
                Ensemble ens = ((EnsembleCommand) command).getEnsemble();

                boolean ensExists = ensembles.contains(ens);

                if (ensExists && !ens.equals(currentEnsemble)) {

                    this.currentEnsemble = ens;
                    switched = true;
                }
            }

            if (switched && currentEnsemble != null) {
                System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID() + " "
                        + this.currentEnsemble.getName() + ".");
            }

            return command;
        } else {
            System.out.println("Redo List is empty.");
        }
        return null;
    }

    public void listUndoRedo() {
        System.out.println("Undo List");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i).getDescription());
        }
        System.out.println("-- End of undo list --");
        System.err.println("");

        System.out.println("Redo List");
        for (int i = 0; i < redoStack.size(); i++) {
            System.out.println(redoStack.get(i).getDescription());
        }
        System.out.println("-- End of redo list --");
    }

    public LinkedList<Ensemble> getEnsembles() {
        return ensembles;
    }

    public Ensemble getCurrentEnsemble() {
        return currentEnsemble;
    }

    public void setCurrentEnsemble(Ensemble ensemble) {
        this.currentEnsemble = ensemble;
    }

    public void showCurrentEnsemble() {
        if (currentEnsemble != null) {
            currentEnsemble.showEnsemble();
        } else {
            System.out.println("No current ensemble set.");
        }
    }

    public void displayAllEnsembles() {
        if (ensembles.isEmpty()) {
            System.out.println("No ensembles created.");
            return;
        }
        for (Ensemble e : ensembles) {
            System.out.println(getEnsembleTypeLabel(e) + " " + e.getName() + " (" + e.getEnsembleID() + ")");
        }
    }

    public Stack<Command> getHistory() {
        return history;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }

    private String getEnsembleTypeLabel(Ensemble ensemble) {
        if (ensemble instanceof OrchestraEnsemble) {
            return "orchestra ensemble";
        }
        if (ensemble instanceof JazzBandEnsemble) {
            return "jazz band ensemble";
        }
        return "ensemble";
    }
}
