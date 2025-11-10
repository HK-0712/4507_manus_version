# 1. Assumptions

## Assumptions regarding the problem context

During the development of Music Ensembles Management System (MEMS), I made following assumptions:

### 1.1 Ensemble Assumptions

- Each ensemble must have unique ID. System will not check duplicate, user need to ensure this.
- Only two types supported: Orchestra and Jazz Band.
- Empty ensemble (no musicians) is allowed.
- Ensemble name can be changed and support undo/redo.

### 1.2 Musician Assumptions

- Musician ID must be unique within an ensemble, but different ensembles can have same musician ID.
- One musician belongs to only one ensemble.
- Orchestra musicians: violinist or cellist only.
- Jazz band musicians: pianist, saxophonist or drummer only.

### 1.3 System Operations

- Only one "current ensemble" at a time. Must set current ensemble before operations like add/modify/delete musician.
- New ensemble automatically becomes current ensemble.
- Undo/redo works for: create ensemble, add musician, modify instrument, delete musician, change name.
- Redo list cleared when new command executed after undo.

### 1.4 User Input

- User input correct format (e.g., "ID, Name" for musician).
- Invalid input shows error message.
- Can exit anytime with 'x' command.

### 1.5 Data Storage

- No persistence. All data lost when exit.

### 1.6 Design Patterns

- Factory pattern creates ensembles and commands.
- Memento pattern saves only necessary state (instrument/name), not whole object.
- Command pattern uses Stack for undo/redo (LIFO order).

