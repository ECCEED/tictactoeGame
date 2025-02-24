package tn.pi.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button[] buttons = new Button[9];
    private Button btnRestart, btnBackToMenu;
    private String currentPlayer = "X";
    private boolean gameActive = true; // To check if the game is active or ended
    private String[] board = new String[9]; // To track board state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);

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

        // Link restart and back to menu buttons
        btnRestart = findViewById(R.id.btnRestart);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        btnRestart.setVisibility(View.GONE); // Hide initially
        btnBackToMenu.setVisibility(View.GONE); // Hide initially

        // Initialize the board state
        for (int i = 0; i < 9; i++) {
            board[i] = "";
        }

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
        // If the game is not active, return
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
            btnRestart.setVisibility(View.VISIBLE); // Show restart button
            btnBackToMenu.setVisibility(View.VISIBLE); // Show back to menu button
            return;
        }

        // Check if the game is a draw
        if (isBoardFull()) {
            tvStatus.setText("It's a draw!");
            gameActive = false;
            btnRestart.setVisibility(View.VISIBLE); // Show restart button
            btnBackToMenu.setVisibility(View.VISIBLE); // Show back to menu button
            return;
        }

        // Switch player
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        tvStatus.setText(currentPlayer + "'s Turn");
    }

    private boolean checkWinner() {
        // Check all winning combinations
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}  // Diagonals
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
        // Reset the board
        for (int i = 0; i < 9; i++) {
            board[i] = "";
            buttons[i].setText("");
            buttons[i].setEnabled(true); // Re-enable buttons
        }

        // Reset game state
        gameActive = true;
        currentPlayer = "X";
        tvStatus.setText("Player 1's Turn");

        // Hide restart button and back to menu button again
        btnRestart.setVisibility(View.GONE);
        btnBackToMenu.setVisibility(View.GONE);
    }

    private void goBackToMenu() {
        // Navigate to StartActivity
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity so the user can't come back using the back button
    }

}
