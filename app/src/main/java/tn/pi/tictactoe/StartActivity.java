package tn.pi.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private EditText etPlayerName;
    private Button btnPlayHuman, btnPlayAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        etPlayerName = findViewById(R.id.etPlayerName);
        btnPlayHuman = findViewById(R.id.btnPlayHuman);
        btnPlayAI = findViewById(R.id.btnPlayAI);

        btnPlayHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(false);
            }
        });

        btnPlayAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(true);
            }
        });
    }

    private void startGame(boolean isAI) {
        String playerName = etPlayerName.getText().toString().trim();
        if (playerName.isEmpty()) {
            etPlayerName.setError("Enter your name!");
            return;
        }

        Intent intentHuman = new Intent(StartActivity.this, MainActivity.class);
        Intent intentAi = new Intent(StartActivity.this, AiActivity.class);
        intentHuman.putExtra("playerName", playerName);
        intentAi.putExtra("playerName",playerName);
        intentAi.putExtra("isAI", isAI);
        if (isAI)
        {
            startActivity(intentAi);
        }
        else {
            startActivity(intentHuman);
        }
    }
}
