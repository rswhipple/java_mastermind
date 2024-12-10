# Mastermind Game - Java Implementation

## Overview

This project is a Java-based, object-oriented implementation of the Mastermind game. It features a dynamic Command-Line Interface (CLI), database integration using SQLite, and partial support for multi-threading and event-driven design. The game allows players to interactively solve the Mastermind code-breaking puzzle with customizable settings and player tracking.

This project emphasizes software design principles such as modularity, maintainability, and scalability, laying the foundation for future enhancements like multiplayer or online gameplay.

## Prerequisites

Ensure the following are installed on your system:

- **Java**: JDK 23.0.1 or later [https://jdk.java.net]
- **Maven**: Version 3.9.9 or later [https://maven.apache.org/download.cgi]

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rswhipple/java_mastermind.git
   cd java_mastermind/mastermind
   ```

2. **Verify Prerequisites**:
   - Check Java installation:
     ```bash
     java -version
     ```
   - Check Maven installation:
     ```bash
     mvn -v
     ```

3. **Build the Project**:
   Build and package the project using Maven:
   ```bash
   mvn clean install
   ```

   This will:
   - Clean any previous builds,
   - Download dependencies (e.g., SQLite JDBC driver).
   - Compile the source files.
   - Package the application into a runnable JAR located in the `target/` directory.

## Running the Application

Run the application using Java:

```bash
java -jar target/mastermind-1.0-SNAPSHOT.jar <mode>
```

Replace `<mode>` with one of the following:
- `cli_basic`: Starts the game with a simple command-line interface.
- `cli_dynamic`: Starts the game with a more advanced command-line interface.


### Example:
```bash
java -jar target/mastermind-1.0-SNAPSHOT.jar cli_dynamic
```

## Play with the Dynamic CLI
1. The game starts with the main menu offering various options:
   * Game Settings: Customize gameplay by adjusting the number of players, rounds, code length, feedback type, or setting a custom code.
   * Leaderboard: View the top players based on performance metrics like win rates.
   * Start New Game: Begin a fresh game session with the selected settings
   * ... more options ...
2. Create a Player name to track your results.
4. `#` + `enter` at any point to see the Main Menu.
4. `ctrl c` + `enter` at any point to trigger a clean shutdown.


## Play with the Basic CLI 
1. Game rules are provided to you before the game begins.
2. A new game will begin after the last game session ends.
3. Enter `ctrl` + `c` at any point to exit the program.

## Extensions
- **Multiple Game Modes**:
  - Basic CLI mode (`cli_basic`)
  - Dynamic CLI mode (`cli_dynamic`)
- **Setting Customization**:
   - Change the length of the code or the number of rounds.
   - Choose the type of feedback you want the program to provide.
   - Choose to set the code yourself or play with an open hand (useful for debugging).
- **Database Integration and Leaderboard**: 
   - Game data is stored and managed using SQLite.
   - Leaderboard menu option shows the top 5 players and their record of wins and losses.
- **Logging**: 
   - Uses the slf4j library for logging.
   - Logs are stored in the logs/application.log
- **Clean Shutdown**: 
   - Centralized shutdown handling for smooth resource management.

## **Challenges**

1. Refactoring the two **Game Engine** classes was the most significant challenge. Initially, the **dynamic `CLIGameEngine`** extended the **basic `MMGameEngine`**. My plan was to use `MMGameEngine` as a base class to support future scaling and implementations. However, integrating the two game loops with the rest of the features proved more complex than anticipated. To resolve this, I adopted the **Factory design pattern**, which provided a cleaner, more modular approach to creating game engines.

2. Getting comfortable with Java's ecosystem was another hurdle. As someone relatively new to Java, I had to familiarize myself with its libraries, conventions, and development tools while ensuring my code remained clean and maintainable. 

## Future Extensions
- **Play against the computer**
- **Finish Multiplayer Mode**
- **Refine Feedback and Scoring**
- **Refine Statistics**
- **API version** 

## Folder Structure

- **`mastermind/src/main/java/`**: Main application source code.
- **`mastermind/src/main/resources/`**: Database file location.
- **`target/`**: Output directory for compiled classes and packaged JAR files.


## License

This project is licensed under the MIT License. See `LICENSE` for details.


