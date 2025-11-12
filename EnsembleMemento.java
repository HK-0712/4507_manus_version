public class EnsembleMemento {
    private final String name;
    private final Ensemble ensemble;

    public EnsembleMemento(Ensemble ensemble) {
        this.ensemble = ensemble;
        this.name = ensemble.getName();
    }

    public void restore() {
        ensemble.setName(name);
    }
}
