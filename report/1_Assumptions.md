# Part 1 - Assumptions

## Assumptions Regarding the Problem Context

### 1. Ensemble Structure
- Each ensemble must have unique ensemble ID (String type)
- Ensemble can have a name that can be modified after creation
- Two concrete ensemble types exist: OrchestraEnsemble and JazzBandEnsemble
- OrchestraEnsemble support two roles: Violinist (role=1) and Cellist (role=2)
- JazzBandEnsemble support three roles: Pianist (role=1), Saxophonist (role=2), Drummer (role=3)
- Ensemble use LinkedList to store musicians internally
- Abstract class Ensemble define common behavior for all ensemble types
- System don't enforce unique ensemble ID globally, assume user provide unique ID

### 2. Musician Structure
- Each musician have unique musician ID (String type) within same ensemble
- Musician have a name (String) and role (int value)
- Role value represent the instrument musician play in the ensemble
- When musician move to different ensemble type, invalid roles are reset to 0
- Musician object store in ensemble's musician list
- If add musician with duplicate ID to same ensemble, old musician is removed first then new one added

### 3. Command Execution
- All operations implement Command interface with execute() and undo() methods
- Commands that modify data (create, add, delete, change) are executed through EnsembleService and stored in history
- Query commands (show, display) and control commands (undo, redo) execute directly in Main class
- Query commands still implement Command interface but undo() is empty (no-op)
- Each command provide description for display in history list

### 4. Current Ensemble Concept
- System maintain single "current ensemble" reference at any time
- Operations like add musician, delete musician work on current ensemble only
- User can switch current ensemble using set current ensemble command
- If current ensemble become null, operations that need it should show error
- When new ensemble created, Main class set it as current ensemble (not automatic by EnsembleService)

### 5. Undo/Redo Mechanism
- EnsembleService maintain two stacks: history stack and undoneCommands stack
- Only modification commands (create, add, delete, change name, change instrument) push to history stack
- When command execute through manager, it push to history stack
- When undo occur, command pop from history and push to undone stack
- When new modification command execute, undone stack is cleared
- Commands store necessary information (like previous state) to support undo

### 6. State Preservation
- Before modify musician role, save old role using MusicianState memento
- Before modify ensemble name, save old name using EnsembleState memento
- Memento objects store reference to original object and old values
- Restore operation apply saved values back to object
- Only ChangeInstrumentCmd and ChangeNameCmd use memento pattern
- Creation and deletion commands don't need memento (they restore by reverse operation)

### 7. Command Creation
- CommandParser factory class responsible for create all command objects
- Factory method take user input and construct appropriate command
- Each command type need different parameters from user input
- Factory also handle user input collection using Scanner (factory have dual responsibility)
- Main class don't directly instantiate command classes
- Factory use switch statement to determine which command to create

### 8. Collection Usage
- Ensemble use AbstractList<Musician> (implemented as LinkedList)
- EnsembleService use LinkedList<Ensemble> for storing ensembles
- Iterator pattern used to traverse musician list without exposing internal structure
- When add musician with duplicate ID, old musician is removed first then new one added
- Order of musicians preserved in insertion order

### 9. Polymorphism
- Different ensemble types override updateMusicianRole() with specific logic
- Different ensemble types override showEnsemble() with specific display format
- Commands implement common Command interface but have different execute logic
- Factory return Command interface type, actual type determined at runtime

### 10. Input/Output Assumptions
- Musician input format is "id, name" (comma and space separator)
- Instrument/role input is integer value (1, 2, or 3 depend on ensemble type)
- Invalid input should show error message and not execute command
- System output follow specific format for ensemble and musician display
- All interaction through console (System.out and Scanner)