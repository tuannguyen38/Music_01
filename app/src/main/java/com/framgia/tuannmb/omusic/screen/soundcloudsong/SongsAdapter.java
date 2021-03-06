package com.framgia.tuannmb.omusic.screen.soundcloudsong;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.utils.StringUtil;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private List<Song> mSongs;
    private LayoutInflater mLayoutInflater;
    private SongItemClickListener mSongItemClickListener;

    public SongsAdapter(List<Song> songs) {
        mSongs = songs;
    }

    public void setSongItemClickListener(SongItemClickListener songItemClickListener) {
        mSongItemClickListener = songItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mSongs.get(position));
        holder.setVisibleImageDownload(mSongs.get(position).isDownloadable());
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mAvatarSong;
        private TextView mTextSongName;
        private TextView mTextArtist;
        private TextView mTextDuration;
        private ImageView mDownloadSong;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatarSong = itemView.findViewById(R.id.image_artist_avatar);
            mTextSongName = itemView.findViewById(R.id.text_artist_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mDownloadSong = itemView.findViewById(R.id.image_download);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            itemView.setOnClickListener(this);
            mDownloadSong.setOnClickListener(this);
        }

        public void bindData(Song song) {
            if (song == null) {
                return;
            }
            Glide.with(itemView.getContext()).load(song.getArtworkUrl())
                    .placeholder(R.drawable.ic_head_phone_blue)
                    .into(mAvatarSong);
            mTextSongName.setText(song.getTitle());
            mTextArtist.setText(song.getUsername());
            mTextDuration.setText(StringUtil
                    .parseMilliSecondsToTimer(song.getDuration()));
        }

        private void setVisibleImageDownload(boolean isDownloadable) {
            if (!isDownloadable) {
                mDownloadSong.setVisibility(View.INVISIBLE);
                return;
            }
            mDownloadSong.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            if (mSongItemClickListener == null) return;
            if (view.getId() == R.id.image_download) {
                mSongItemClickListener.onDownloadSongClick(mSongs.get(getAdapterPosition()));
                return;
            }
            mSongItemClickListener.onItemClick(getAdapterPosition(), mSongs);
        }
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void updateSongs(List<Song> songs) {
        if (songs == null) {
            return;
        }
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    public interface SongItemClickListener {
        void onItemClick(int position, List<Song> songs);

        void onDownloadSongClick(Song song);
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }
}
