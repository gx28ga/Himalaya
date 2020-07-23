package com.axun.himalaya.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axun.himalaya.R;
import com.axun.himalaya.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private static final String TAG = "RecommendAdapter";
    private List<Album> mData = new ArrayList<>();
    private onRecommendItemClickListener mItemClickListener;

    public RecommendAdapter() {
    }

    @NonNull
    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_recommend, null);
        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    int position = (Integer) v.getTag();
                    mItemClickListener.onItemClick(position, mData.get(position));
                }
            }
        });
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (albumList != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        private final ImageView ivCover;
        private final TextView tvDesc;
        private final TextView tvPlayCount;
        private final TextView tvContentCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.album_title);
            ivCover = itemView.findViewById(R.id.album_cover);
            tvDesc = itemView.findViewById(R.id.album_desc);
            tvPlayCount = itemView.findViewById(R.id.album_play_count);
            tvContentCount = itemView.findViewById(R.id.album_content_count);

        }

        public void setData(Album album) {
            tvTitle.setText(album.getAlbumTitle());
            LogUtil.d(TAG,album.getCoverUrlLarge());
            Picasso.with(itemView.getContext()).load(album.getCoverUrlLarge()).into(ivCover);
            tvDesc.setText(album.getAlbumIntro());
            tvPlayCount.setText(album.getPlayCount() + "");
            tvContentCount.setText(album.getIncludeTrackCount() + "");

        }
    }

    public void setOnRecommendItemClickListener (onRecommendItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface onRecommendItemClickListener {
        void onItemClick(int position, Album album);
    }

}
