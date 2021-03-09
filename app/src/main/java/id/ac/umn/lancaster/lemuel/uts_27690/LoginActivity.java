package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private EditText inputUsername, inputPassword;

    private final String setUsername = "uasmobile";
    private final String setPassword = "uasmobilegenap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Halaman Login");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Username and Password from User Input
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                if (username.equals(setUsername) && password.equals(setPassword)) {
                    Intent goToSongList = new Intent(LoginActivity.this, SongListActivity.class);
                    finish();
                    startActivity(goToSongList);
                }
                else {
                    // Show toast
                    Toast.makeText(v.getContext(), "Username atau Password salah!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goToMainMenu = new Intent(LoginActivity.this, MainActivity.class);
                setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
