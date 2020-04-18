package com.erdr.rlbvideolib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLecture extends RecyclerView.Adapter<AdapterLecture.BatchViewHolder> {

    Context mCtx;
    List<ListLectureList> batchtlist;
    //I am adding extra


    public AdapterLecture(Context mCtx, List<ListLectureList> batchtlist) {
        this.mCtx = mCtx;
        this.batchtlist = batchtlist;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_lecture_video,parent,false);

        BatchViewHolder BatchViewHolder = new BatchViewHolder(view);
        return BatchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {

        ListLectureList Batchdata;
        Batchdata = batchtlist.get(position);

        holder.mainText.setText(Batchdata.getLectureText());
        holder.subjectText.setText(Batchdata.getLectureChapter());
        String start = "https://img.youtube.com/vi/";
        String end = "/mqdefault.jpg";
        String mid = Batchdata.getLectureUrl();
        Picasso.get()
                .load(start+mid+end)
                .placeholder(R.drawable.dead)
                .error(R.drawable.dead)
                .into(holder.thisImage);
/*
        holder.textViewNumber.setText(Batchdata.getNumber());
        holder.MenuText.setText(Batchdata.getMenuText());
*/

    }

    @Override
    public int getItemCount() {

        return batchtlist.size();
    }

    class BatchViewHolder extends RecyclerView.ViewHolder {
        ImageView thisImage;
        TextView mainText,subjectText;

        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            thisImage = itemView.findViewById(R.id.thisImage);
            mainText = itemView.findViewById(R.id.mainText);
            subjectText = itemView.findViewById(R.id.subjectText);
/*

            thisImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LectureList) mCtx).userItemClick(getAdapterPosition());
                }
            });
*/

        }
    }

}
