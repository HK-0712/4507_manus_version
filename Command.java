// Command interface for command pattern
public interface Command {
    void execute();
    void undo();
    String getDescription();
    void setManager(EnsembleManager manager);
}
