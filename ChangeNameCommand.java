public class ChangeNameCommand implements EnsembleCommand {
    private EnsembleManager manager;
    private Ensemble ensemble;
    private String newName;
    private Memento memento; // Memento Pattern: 儲存執行前的狀態

    @Override
    public Ensemble getEnsemble() {
        return ensemble;
    }
    
    public ChangeNameCommand(Ensemble ensemble, String newName) {
        this.ensemble = ensemble;
        this.newName = newName;
    }

    @Override
    public void setManager(EnsembleManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void execute() {
        // Memento Pattern: 在執行前保存狀態
        if (manager != null) {
            memento = manager.saveState();
        }
        ensemble.setName(newName);
        System.out.println("Ensemble's name is changed.");
    }
    
    @Override
    public void undo() {
        // Memento Pattern: 使用 Memento 還原狀態
        if (manager != null && memento != null) {
            manager.restoreState(memento);
        }
    }
    
    @Override
    public String getDescription() {
        return "Change ensemble\u2019s name, " + ensemble.getEnsembleID() + ", " + newName;
    }
}
