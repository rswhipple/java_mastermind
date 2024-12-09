# Java Mastermind

Java Mastermind is a command-line implementation of the classic Mastermind game. It features multiple game modes, a database for game management, and a clean shutdown mechanism.

## Prerequisites

Ensure the following are installed on your system:

- **Java**: Version 17 or later
- **Maven**: Version 3.6 or later

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
   Compile and package the project using Maven:
   ```bash
   mvn package
   ```

   This will:
   - Download dependencies (e.g., SQLite JDBC driver).
   - Compile the source files.
   - Package the application into a runnable JAR located in the `target/` directory.

## Running the Application

Run the application using Maven:

```bash
mvn exec:java -Dexec.mainClass="org.rws.mastermind.Main" -Dexec.args="<mode>"
```

Replace `<mode>` with one of the following:
- `cli_simple`: Starts the game with a simple command-line interface.
- `cli_robust`: Starts the game with a more advanced command-line interface.

### Example:
```bash
mvn exec:java -Dexec.mainClass="org.rws.mastermind.Main" -Dexec.args="cli_simple"
```

## Features

- **Multiple Game Modes**:
  - Simple CLI mode (`cli_simple`)
  - Robust CLI mode (`cli_robust`)
- **Database Integration**: Game data is stored and managed using SQLite.
- **Clean Shutdown**: Centralized shutdown handling for smooth resource management.

## Folder Structure

- **`mastermind/src/main/java/`**: Main application source code.
- **`mastermind/src/main/resources/`**: Database file location.
- **`target/`**: Output directory for compiled classes and packaged JAR files.

## Troubleshooting

1. **No Suitable Driver Found**:
   - Ensure the SQLite JDBC driver is included as a dependency in the `pom.xml`.

2. **Database File Missing**:
   - The application will create the database file at `mastermind/src/main/resources/mastermind_db.sqlite3` if it does not exist.


## License

This project is licensed under the MIT License. See `LICENSE` for details.


