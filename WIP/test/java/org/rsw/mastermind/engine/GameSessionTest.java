// package org.rws.mastermind.engine;

// import static org.mockito.Mockito.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.rws.mastermind.database.MastermindDB;
// import org.rws.mastermind.http.HttpHandler;
// import org.rws.mastermind.input.CLIInputHandler;
// import org.rws.mastermind.models.GameState;
// import org.rws.mastermind.models.Player;
// import org.rws.mastermind.settings.GameSetter;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class GameSessionTest {

//     @Mock
//     private GameSetter settings;

//     @Mock
//     private HttpHandler http;

//     @Mock
//     private MastermindDB db;

//     @Mock
//     private CLIInputHandler input;

//     @Mock
//     private GameState gameState;

//     private List<Player> players;

//     private GameSession gameSession;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Mock `validateInput` to return a non-null, valid string
//         when(input.validateInput()).thenReturn("Player 1");

//         players = Arrays.asList(new Player("Player 1", db, input));

//         when(settings.getFeedbackType()).thenReturn("default");
//         when(settings.getNumberOfRounds()).thenReturn(10);

//         gameSession = new GameSession(settings, http, "session123", players);
//     }

//     @Test
//     void create() {
//         GameSession session = GameSession.create(settings, http, "session123", players);
//         assertNotNull(session);
//         assertEquals("session123", session.getSessionId());

//         session = GameSession.create(settings, http, "session123", null);
//         assertNull(session);
//     }


//     @Test
//     void resetSession() {
//         gameSession.resetSession();
//         verify(settings, times(1)).getFeedbackType();
//         verify(settings, times(1)).getNumberOfRounds();
//     }

//     @Test
//     void processGuess() {
//         String guess = "1234";
//         when(gameState.processGuess(guess)).thenReturn("Feedback for 1234");

//         // Assuming the gameState is being properly set within GameSession
//         // Mocking directly here to validate expected behavior
//         String feedback = gameSession.processGuess(guess);
//         assertEquals("Feedback for 1234", feedback);
//     }

//     @Test
//     void endSession() {
//         gameSession.endSession();
//         // Assuming endGame() method in GameState is called within endSession
//         // Verifying its invocation
//         verify(gameState, times(1)).endGame();
//     }

//     @Test
//     void getCurrentPlayer() {
//         Player currentPlayer = gameSession.getCurrentPlayer();
//         assertEquals("Player 1", currentPlayer.getName());
//     }

//     @Test
//     void incrementCurrentPlayer() {
//         gameSession.incrementCurrentPlayer();
//         Player currentPlayer = gameSession.getCurrentPlayer();
//         assertEquals("Player 2", currentPlayer.getName());

//         gameSession.incrementCurrentPlayer();
//         currentPlayer = gameSession.getCurrentPlayer();
//         assertEquals("Player 1", currentPlayer.getName());
//     }
// }