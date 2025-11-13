public interface Command {
    void execute();
    void undo();
    String getDescription();
    void setManager(EnsembleService manager);
}
