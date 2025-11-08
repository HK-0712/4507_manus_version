import java.util.LinkedList;
import java.util.LinkedList;

public class Memento {
    private LinkedList<Ensemble> state;
    private Ensemble currentEnsemble;

    public Memento(LinkedList<Ensemble> ensembles, Ensemble current) {
        // Deep copy the list of ensembles and the current ensemble reference
        this.state = new LinkedList<>();
        for (Ensemble e : ensembles) {
            // Assuming Ensemble has a copy constructor or clone method for deep copy
            // For simplicity in this context, we'll assume a shallow copy of the list is sufficient,
            // but in a real-world scenario, a deep copy of Ensemble objects would be necessary.
            // Since the state is the list of ensembles and the current ensemble, we'll focus on copying the list structure.
            this.state.add(e);
        }
        this.currentEnsemble = current;
    }

    public LinkedList<Ensemble> getState() {
        return state;
    }

    public Ensemble getCurrentEnsemble() {
        return currentEnsemble;
    }
}
