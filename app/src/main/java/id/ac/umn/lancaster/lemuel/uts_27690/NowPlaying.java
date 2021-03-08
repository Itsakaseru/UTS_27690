package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class NowPlaying extends AppCompatActivity
{
    private TextView songName;
    private ImageButton btnControl;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private SeekBar seekBar;

    MediaPlayer mPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        songName = (TextView) findViewById(R.id.songName);
        btnControl = findViewById(R.id.btnControl);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        seekBar = findViewById(R.id.seekBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set action bar title
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Song song = (Song) bundle.getSerializable("SongDetail");

        songName.setText(song.getSongTitle());
        Uri uri = Uri.parse(song.getSongURI());

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(getApplicationContext(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Autoplay
        mPlayer.start();
        updateSeekBar();

        seekBar.setMax(mPlayer.getDuration());

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If music is playing
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    btnControl.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                }
                // If not then resume
                else {
                    mPlayer.start();
                    btnControl.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });
    }

    private void updateSeekBar() {
        seekBar.setProgress(mPlayer.getCurrentPosition());
        seekBar.postDelayed(runnable, 50);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Run Action when back button is pressed
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
