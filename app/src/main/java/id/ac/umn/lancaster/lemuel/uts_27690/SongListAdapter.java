package id.ac.umn.lancaster.lemuel.uts_27690;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder>
{
    private LinkedList<Song> mSongList;
    private LayoutInflater mInflater;
    private Context mContext;

    public SongListAdapter(Context context, LinkedList<Song> songList) {
        this.mContext = context;
        this.mSongList = songList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.song_item_layout, parent, false);
        return new SongViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.SongViewHolder holder, int position) {
        Song mSong = mSongList.get(position);
        holder.songTitle.setText(mSong.getSongTitle());
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView songTitle;
        private SongListAdapter mAdapter;
        private int mPosisi;
        private Song mSong;

        public SongViewHolder(@NonNull View itemView, SongListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            songTitle = (TextView) itemView.findViewById(R.id.songTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mPosisi = getLayoutPosition();
            mSong = mSongList.get(mPosisi);

            // Call now playing class
            Intent intentDetail = new Intent(mContext, NowPlaying.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("SongDetail", mSong);
            intentDetail.putExtras(bundle);
            mContext.startActivity(intentDetail);
        }
    }
}
