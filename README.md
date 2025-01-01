**2048 Game Implementation**

This repository contains a Java implementation of the classic game 2048, designed using the Model-View-Controller (MVC) architecture. The project features intuitive gameplay with save/load, undo/redo functionalities, and a graphical user interface (GUI) built with Swing.

_Overview_

This project is structured to demonstrate the use of MVC design principles, separating the game's logic, interface, and control mechanisms. The game board updates dynamically based on user inputs, while maintaining a clear division between model logic and GUI functionality.

_Features_
- Dynamic GUI: Responsive game board built with Java Swing.
- Save/Load: Save and load game states for resuming later.
- Undo/Redo: Revert and repeat moves with ease.
- Custom File Handling: Utilities to manage file-based operations.
- Unit Testing: Comprehensive tests for game logic and functionality.

_Classes_
1. GameBoard (View & Controller)
    - Handles key and mouse inputs.
    - Updates the GUI and status labels based on the game state.
    - Interacts with the Game2048 model to update the game state.
    - Implements operations such as undo, redo, save, and load.
2. Game2048 (Model)
    - Core logic and state management for 2048.
    - Manages game board, game over status, and history for undo/redo operations.
    - Performs computational tasks like tile movement, merging, and win/game-over checks.
    - Implements file-based save and load functionality.
3. Run2048 (Setup)
    - Initializes the main game window with buttons and labels.
    - Organizes the game board and status components.
    - Handles user interactions through buttons like reset, instructions, undo, redo, save, and load.
4. FileUtilities
    - Provides helper functions for file operations.
    - Includes methods for reading from and writing to files.
5. LineIterator
    - Implements an iterator for reading files line by line.
    - Facilitates file parsing for save/load operations.
6. Game (Launcher)
    - Contains the main method to start the game.
    - Launches the Run2048 setup.
7. Game2048Test (Unit Tests)
    - Tests core functionalities of the Game2048 class.
    - Covers: movement operations, win conditions, game over checks, and undo/redo functionality.

_Setup and Usage_
- Prerequisites: Java JDK 8+, JUnit 5 (for testing)
- Running the game: clone the repository, compile the code, and run the game. Tests can be run using JUnit.

