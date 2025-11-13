// This is special command for ensemble
// It extend the base Command
public interface EnsembleCommand extends Command {
    Ensemble getEnsemble();
}