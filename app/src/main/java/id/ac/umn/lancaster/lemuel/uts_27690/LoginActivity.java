package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private EditText inputUsername, inputPassword;

    private String setUsername = "uasmobile";
    private String setPassword = "uasmobilegenap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    startActivity(goToSongList);
                }
                else {
                    // Show toast
                    Toast.makeText(v.getContext(), "Username atau Password salah!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
