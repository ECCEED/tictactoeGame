package tn.pi.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AiActivity extends AppCompatActivity {

    private TextView tvStatus, tvPlayerName;
    private Button[] buttons = new Button[9];
    private Button btnRestart, btnBackToMenu;
    private String currentPlayer = "X"; // "X" is the player, "O" is the AI
    private boolean gameActive = true;
    private String[] board = new String[9]; // To track board state
    private String playerName = ""; // Player's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        tvStatus = findViewById(R.id.tvStatus);
        tvPlayerName = findViewById(R.id.tvPlayerName); // TextView to display player name
        btnRestart = findViewById(R.id.btnRestart);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        // Link all buttons to the array
        buttons[0] = findViewById(R.id.button00);
        buttons[1] = findViewById(R.id.button01);
        buttons[2] = findViewById(R.id.button02);
        buttons[3] = findViewById(R.id.button10);
        buttons[4] = findViewById(R.id.button11);
        buttons[5] = findViewById(R.id.button12);
        buttons[6] = findViewById(R.id.button20);
        buttons[7] = findViewById(R.id.button21);
        buttons[8] = findViewById(R.id.button22);

        // Initialize the board state
        for (int i = 0; i < 9; i++) {
            board[i] = "";
        }

        // Get the player's name from the Intent
        playerName = getIntent().getStringExtra("playerName");

        // Display the player's name on the screen
        tvPlayerName.setText("Player: " + playerName);

        // Set up listeners for each button
        for (int i = 0; i < 9; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(v -> onButtonClick(finalI));
        }

        // Set up listener for the restart button
        btnRestart.setOnClickListener(v -> restartGame());

        // Set up listener for the back to menu button
        btnBackToMenu.setOnClickListener(v -> goBackToMenu());
    }

    private void onButtonClick(int index) {
        // If the game is not active or the cell is already occupied, return
        if (!gameActive || !board[index].equals("")) {
            return;
        }

        // Mark the current player's move
        board[index] = currentPlayer;
        buttons[index].setText(currentPlayer);
        buttons[index].setEnabled(false); // Disable the button after click

        // Check if there's a winner
        if (checkWinner()) {
            tvStatus.setText(currentPlayer + " wins!");
            gameActive = false;
            btnRestart.setVisibility(View.VISIBLE);
            btnBackToMenu.setVisibility(View.VISIBLE);
            return;
        }

        // Check if the game is a draw
        if (isBoardFull()) {
            tvStatus.setText("It's a draw!");
            gameActive = false;
            btnRestart.setVisibility(View.VISIBLE);
            btnBackToMenu.setVisibility(View.VISIBLE);
            return;
        }

        // Switch player
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        tvStatus.setText(currentPlayer + "'s Turn");

        // If it's the AI's turn, make a move
        if (currentPlayer.equals("O")) {
            aiMove();
        }
    }

    private void aiMove() {
        // Find the first available move for the AI (simple random move)
        int move = getRandomMove();
        board[move] = "O";
        buttons[move].setText("O");
        buttons[move].setEnabled(false);

        // Check if the AI has won
        if (checkWinner()) {
            tvStatus.setText("AI wins!");
            gameActive = false;
            btnRestart.setVisibility(View.VISIBLE);
            btnBackToMenu.setVisibility(View.VISIBLE);
            return;
        }

        // Check if the game is a draw
        if (isBoardFull()) {
            tvStatus.setText("It's a draw!");
            gameActive = false;
            btnRestart.setVisibility(View.VISIBLE);
            btnBackToMenu.setVisibility(View.VISIBLE);
            return;
        }

        // Switch player to human
        currentPlayer = "X";
        tvStatus.setText("Player's Turn");
    }

    private int getRandomMove() {
        // Return a random available move for the AI
        int[] availableMoves = new int[9];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i].equals("")) {
                availableMoves[count++] = i;
            }
        }
        int randomIndex = (int) (Math.random() * count);
        return availableMoves[randomIndex];
    }

    private boolean checkWinner() {
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winCombinations) {
            if (board[combo[0]].equals(currentPlayer) &&
                    board[combo[1]].equals(currentPlayer) &&
                    board[combo[2]].equals(currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell.equals("")) {
                return false;
            }
        }
        return true;
    }

    private void restartGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = "";
            buttons[i].setText("");
            buttons[i].setEnabled(true); // Re-enable buttons
        }

        gameActive = true;
        currentPlayer = "X";
        tvStatus.setText("Player's Turn");

        btnRestart.setVisibility(View.GONE);
        btnBackToMenu.setVisibility(View.GONE);
    }

    private void goBackToMenu() {
        // Navigate to StartActivity
        Intent intent = new Intent(AiActivity.this, StartActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}
