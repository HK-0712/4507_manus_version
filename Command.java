// This is Command pattern interface
// All command must implement this
public interface Command {
    void execute();
    void undo();
    String getDescription();
    void setManager(EnsembleService manager);
}
