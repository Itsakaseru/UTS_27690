package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class SongListActivity extends AppCompatActivity
{
    RecyclerView rvSongList;
    SongListAdapter mAdapter;
    LinkedList<Song> songList = new LinkedList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set action bar title
        getSupportActionBar().setTitle("List Lagu");

        rvSongList = (RecyclerView) findViewById(R.id.songList);
        mAdapter = new SongListAdapter(this, songList);
        rvSongList.setAdapter(mAdapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(this));
        insertSongs();
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

    public void insertSongs() {
        songList.add(new Song("Anata no Risou no Heroine", "Desc", "android.resource://" +getPackageName() + "/"+ R.raw.anata_no_risou_no_heroine));
        songList.add(new Song("CHASE!", "Desc", "android.resource://" +getPackageName() + "/"+ R.raw.chase));
        songList.add(new Song("Starlight", "Desc", "android.resource://" +getPackageName() + "/"+ R.raw.starlight));
        songList.add(new Song("SUPER NOVA", "Desc", "android.resource://" +getPackageName() + "/"+ R.raw.super_nova));
        songList.add(new Song("VIVID WORLD", "Desc", "android.resource://" +getPackageName() + "/"+ R.raw.vivid_world));
    }
}
