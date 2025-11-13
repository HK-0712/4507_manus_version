public class EnsembleState {
    private final String name;
    private final Ensemble ensemble;

    public EnsembleState(Ensemble ensemble) {
        this.ensemble = ensemble;
        this.name = ensemble.getName();
    }

    public void restore() {
        ensemble.setName(name);
    }
}
