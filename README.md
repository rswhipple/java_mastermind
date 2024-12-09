# Java Mastermind

Java Mastermind is a command-line implementation of the classic Mastermind game. It features multiple game modes, a database for game management, and a clean shutdown mechanism.

## Prerequisites

Ensure the following are installed on your system:
- Java 17 or later
- Maven (optional, for dependency management and building)
- SQLite (for database handling)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rswhipple/java_mastermind.git
   cd java_mastermind
   ```

2. **Compile the Project**:
    - Using `javac`:
      ```bash
      javac -d out mastermind/src/main/java/org/rws/mastermind/*.java
      ```
    - Using Maven:
      ```bash
      mvn compile
      ```

3. **Setup the Database**:
   The application will automatically create the SQLite database file at `mastermind/src/main/resources/mastermind_db.sqlite3` if it does not exist.

## Running the Application

Run the application from the `java_mastermind` directory:

```bash
java -cp out org.rws.mastermind.Main <mode>
```

Replace `<mode>` with one of the following:
- `cli_simple`: Starts the game with a simple command-line interface.
- `cli_robust`: Starts the game with a more advanced command-line interface.

### Example
```bash
java -cp out org.rws.mastermind.Main cli_simple
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

## Contributing

Feel free to submit issues or pull requests. Contributions are welcome!

## License

This project is licensed under the MIT License. See `LICENSE` for details.

---

Replace `<your-username>` in the clone command with your GitHub username. Let me know if you need additional customizations!
