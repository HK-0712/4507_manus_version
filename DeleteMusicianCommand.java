import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeleteMusicianCommand implements EnsembleCommand {
    private final Ensemble ensemble;
    private final Musician musician;
    private List<Musician> allMusiciansBeforeDelete;

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }

    public DeleteMusicianCommand(Ensemble ensemble, Musician musician) {
        this.ensemble = ensemble;
        this.musician = musician;
        this.allMusiciansBeforeDelete = new ArrayList<>();
    }

    @Override
    public void setManager(EnsembleManager manager) {
        // Not required for current operations.
    }

    @Override
    public void execute() {
        // Save the complete order of all musicians before deletion
        allMusiciansBeforeDelete.clear();
        Iterator<Musician> iterator = ensemble.getMusicians();
        while (iterator.hasNext()) {
            allMusiciansBeforeDelete.add(iterator.next());
        }
        
        ensemble.dropMusician(musician);
    }

    @Override
    public void undo() {
        // Remove all current musicians
        List<Musician> currentMusicians = new ArrayList<>();
        Iterator<Musician> iterator = ensemble.getMusicians();
        while (iterator.hasNext()) {
            currentMusicians.add(iterator.next());
        }
        for (Musician m : currentMusicians) {
            ensemble.dropMusician(m);
        }
        
        // Re-add all musicians in the original order
        for (Musician m : allMusiciansBeforeDelete) {
            ensemble.addMusician(m);
        }
    }

    @Override
    public String getDescription() {
        return "Delete musician, " + musician.getMID();
    }
}
