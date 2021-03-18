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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NowPlaying extends AppCompatActivity
{
    private TextView songName;
    private TextView songArtist;
    private ImageButton btnControl;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private SeekBar seekBar;

    private Uri uri;

    private int pos;

    MediaPlayer mPlayer = new MediaPlayer();
    ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        songName = (TextView) findViewById(R.id.songName);
        songArtist = (TextView) findViewById(R.id.songArtist);
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
        pos = (Integer) bundle.getSerializable("POS");
        songList = (ArrayList<Song>) bundle.getSerializable("SongList");

        uri = Uri.parse(songList.get(pos).getSongURI());

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Load the current song into player
        setSongPlayer(uri);

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If music is playing
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    seekBar.removeCallbacks(runnable);
                    btnControl.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                }
                // If not then resume
                else {
                    mPlayer.start();
                    updateSeekBar();
                    btnControl.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If user click the previous button
                // Check if previous is available
                if (pos != 0) {
                    pos--;
                    uri = Uri.parse(songList.get(pos).getSongURI());
                    setSongPlayer(uri);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If user click the previous button
                // Check if next is available
                if (pos != songList.size() - 1) {
                    pos++;
                    uri = Uri.parse(songList.get(pos).getSongURI());
                    setSongPlayer(uri);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mPlayer != null && fromUser){
                    mPlayer.seekTo(progress);
                }
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnControl.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            }
        });
    }

    private void setSongPlayer(Uri songUri) {

        // Always make sure there are no song playing atm
        mPlayer.reset();

        // Set current song selection to media player
        try {
            mPlayer.setDataSource(getApplicationContext(), songUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prepare
        try {
            mPlayer.prepare();
            playMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Play current selected song and start seekbar
    private void playMediaPlayer() {
        // Play the song and set text to song title
        mPlayer.start();
        songName.setText(songList.get(pos).getSongTitle());
        songArtist.setText(songList.get(pos).getSongArtist());

        // Set button to pause
        btnControl.setBackgroundResource(R.drawable.ic_baseline_pause_24);

        // Set maximum seekbar to the current song duration
        seekBar.setMax(mPlayer.getDuration());
        updateSeekBar();
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
                Intent goToSongList = new Intent(NowPlaying.this, SongListActivity.class);
                setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        seekBar.removeCallbacks(runnable);
    }
}
