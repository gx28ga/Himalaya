package com.axun.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axun.himalaya.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.InnerHolder> {
    List<Track> mTracks = new ArrayList<>();
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    OnItemClickListener mOnItemClickListener;
    public DetailListAdapter() {
    }

    public void setData(List<Track> tracks) {
        if (tracks != null) {
            mTracks.clear();
            mTracks.addAll(tracks);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public DetailListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_list, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick();
                }
            }
        });
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListAdapter.InnerHolder holder, int position) {
        holder.setData(mTracks.get(position));
    }

    @Override
    public int getItemCount() {
        if (mTracks != null) {
            return mTracks.size();
        }
        return 0;
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mTrackTitle;
        private final TextView mTrackNo;
        private final TextView mTrackUpdateDate;
        private final TextView mPlayCount;
        private final TextView mMTrackDuration;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTrackTitle = itemView.findViewById(R.id.track_title);
            mTrackNo = itemView.findViewById(R.id.track_no);
            mTrackUpdateDate = itemView.findViewById(R.id.track_update_date);
            mPlayCount = itemView.findViewById(R.id.track_play_count);
            mMTrackDuration = itemView.findViewById(R.id.track_duration);

        }

        public void setData(Track track) {
            mTrackNo.setText(track.getOrderNum() + 1 + "");
            mTrackTitle.setText(track.getTrackTitle());
            mPlayCount.setText(track.getPlayCount() + "");

            int duration = track.getDuration() * 1000;
            String format = mDurationFormat.format(duration);
            mMTrackDuration.setText( format);
            mTrackUpdateDate.setText(mSimpleDateFormat.format(track.getUpdatedAt()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;

    }

    public interface OnItemClickListener{
        void onClick();
    }

}
