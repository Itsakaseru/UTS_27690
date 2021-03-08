package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SongListActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("List Lagu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Check which menu action user choose
        switch (id) {
            case R.id.menuBtnProfile:
                Intent goToProfile = new Intent(SongListActivity.this, ProfileActivity.class);
                boolean isLoggedIn = true;
                goToProfile.putExtra("data", isLoggedIn);
                startActivityForResult(goToProfile, 1);
                break;
            case R.id.menuBtnLogout:
                Intent goToMainMenu = new Intent(SongListActivity.this, MainActivity.class);
                startActivity(goToMainMenu);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
