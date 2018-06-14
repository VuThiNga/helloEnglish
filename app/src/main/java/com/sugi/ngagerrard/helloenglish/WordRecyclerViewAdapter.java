package com.sugi.ngagerrard.helloenglish;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nga Gerrard on 28/05/2018.
 */

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordRecyclerHolder>{

    private IWordRecyclerAdapter mIWordRecyclerAdapter;

    public WordRecyclerViewAdapter(IWordRecyclerAdapter mIWordRecyclerAdapter) {
        this.mIWordRecyclerAdapter = mIWordRecyclerAdapter;
    }

    public IWordRecyclerAdapter getmIWordRecyclerAdapter() {
        return mIWordRecyclerAdapter;
    }

    public void setmIWordRecyclerAdapter(IWordRecyclerAdapter mIWordRecyclerAdapter) {
        this.mIWordRecyclerAdapter = mIWordRecyclerAdapter;
    }

    @Override
    public WordRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_item_word, parent, false);
        return new WordRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final WordRecyclerHolder holder, final int position) {
        final Word data = mIWordRecyclerAdapter.getData(position);
        holder.tvWord.setText(data.getWord());
        holder.tvSpelling.setText(data.getPro() + " " + data.getType());
        holder.tvTranslate.setText(data.getVi());
        holder.tvScore.setText(data.getScore() + "");
        if(data.getScore() == 0){
            holder.imgScore.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
        }else {
            holder.imgScore.setImageResource(R.drawable.ic_star_yellow_700_24dp);
        }
        if(data.isFavourite()) {
            holder.imgStar.setImageResource(R.drawable.ic_star_yellow_700_24dp);
        }else {
            holder.imgStar.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
        }
        holder.imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIWordRecyclerAdapter.onClickStar(position);
            }
        });
        holder.imgSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIWordRecyclerAdapter.onClickSpeaker(position);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIWordRecyclerAdapter.onClickLayout(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIWordRecyclerAdapter.getCount();
    }

    class WordRecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imgSpeaker;
        private TextView tvWord;
        private TextView tvSpelling;
        private TextView tvTranslate;
        private ImageView imgStar;
        private LinearLayout layout;
        private ImageView imgScore;
        private TextView tvScore;

        public WordRecyclerHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_word);
            imgSpeaker = itemView.findViewById(R.id.img_speaker);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvSpelling = itemView.findViewById(R.id.tv_spelling);
            tvTranslate = itemView.findViewById(R.id.tv_translate);
            imgStar = itemView.findViewById(R.id.img_star);
            imgScore = itemView.findViewById(R.id.img_score);
            tvScore = itemView.findViewById(R.id.tv_score);

        }
    }

    public interface IWordRecyclerAdapter{
        int getCount();
        Word getData(int position);
        void onClickStar(int position);
        void onClickSpeaker(int position);
        void onClickLayout(int position);
    }
}
