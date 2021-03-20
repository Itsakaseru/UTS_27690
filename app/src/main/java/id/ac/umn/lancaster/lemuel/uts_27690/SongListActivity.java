package id.ac.umn.lancaster.lemuel.uts_27690;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

        // Check permissions
        if(ContextCompat.checkSelfPermission(SongListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(SongListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(SongListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                ActivityCompat.requestPermissions(SongListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            // Read song from internal storage
            readSongFromStorage();
            loadRecycle();
        }

        AlertDialog.Builder welcomeMsg = new AlertDialog.Builder(this);
        welcomeMsg.setTitle("Selamat Datang");
        welcomeMsg.setMessage("Lemuel Lancaster \n00000027690");
        welcomeMsg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = welcomeMsg.show();
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
                finish();
                startActivity(goToMainMenu);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void readSongFromStorage() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int data = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = songCursor.getString(songTitle);
                String artist = songCursor.getString(songArtist);
                String path = songCursor.getString(data);

                songList.add(new Song(title, artist, path));

            } while (songCursor.moveToNext());


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(SongListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // Read song from internal storage
                        readSongFromStorage();
                        loadRecycle();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied!\nPlease allow our application to access internal storage", Toast.LENGTH_LONG).show();
                    Intent goToMainMenu = new Intent(SongListActivity.this, MainActivity.class);
                    finish();
                    setResult(RESULT_OK);
                }
        }
    }

    public void loadRecycle() {
        rvSongList = (RecyclerView) findViewById(R.id.songList);
        mAdapter = new SongListAdapter(this, songList);
        rvSongList.setAdapter(mAdapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(this));
    }
}
