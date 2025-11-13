import java.util.*;

public class EnsembleService {
    private final LinkedList<Ensemble> ensembles;
    private Ensemble currentEnsemble;
    private final Stack<Command> history;
    private final Stack<Command> undoneCommands;

    public EnsembleService() {
        this.ensembles = new LinkedList<>();
        this.currentEnsemble = null;
        this.history = new Stack<>();
        this.undoneCommands = new Stack<>();
    }

    public void execute(Command command) {
        undoneCommands.clear();

        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();

            boolean switched = false;
            if (command instanceof MakeEnsembleCmd) {

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

            if (!(command instanceof MakeEnsembleCmd) && command instanceof EnsembleCommand) {
                Ensemble ens = ((EnsembleCommand) command).getEnsemble();

                boolean ensExists = ensembles.contains(ens);

                if (ensExists && !ens.equals(currentEnsemble)) {

                    this.currentEnsemble = ens;
                    switched = true;
                }
            }

            undoneCommands.push(command);

            System.out.println("Command (" + command.getDescription() + ") is undone.");

            if (switched) {
                if (currentEnsemble != null) {
                    System.out.println("The current ensemble is changed to " + this.currentEnsemble.getEnsembleID()
                            + " " + this.currentEnsemble.getName() + ".");
                }
            }
        } else {
            System.out.println("Undo List is empty.");
        }
    }

    public void redo() {
        if (!undoneCommands.isEmpty()) {
            Command command = undoneCommands.pop();
            command.execute();
            history.push(command);

            System.out.println("Command (" + command.getDescription() + ") is redone.");

            boolean switched = false;

            if (command instanceof MakeEnsembleCmd) {

                Ensemble ens = ((MakeEnsembleCmd) command).getEnsemble();
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
        } else {
            System.out.println("Redo List is empty.");
        }
    }

    public void showHistory() {
        System.out.println("Undo List");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i).getDescription());
        }
        System.out.println("-- End of undo list --");
        System.err.println("");

        System.out.println("Redo List");
        for (int i = 0; i < undoneCommands.size(); i++) {
            System.out.println(undoneCommands.get(i).getDescription());
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
            System.out.println(getEnsembleType(e) + " " + e.getName() + " (" + e.getEnsembleID() + ")");
        }
    }

    private String getEnsembleType(Ensemble ensemble) {
        if (ensemble instanceof OrchestraEnsemble) {
            return "orchestra ensemble";
        }
        if (ensemble instanceof JazzBandEnsemble) {
            return "jazz band ensemble";
        }
        return "ensemble";
    }
}
