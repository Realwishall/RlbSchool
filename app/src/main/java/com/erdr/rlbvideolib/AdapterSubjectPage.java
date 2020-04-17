package com.erdr.rlbvideolib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSubjectPage extends RecyclerView.Adapter<AdapterSubjectPage.BatchViewHolder> {

    Context mCtx;
    List<ListSubjectPage> batchtlist;
    //I am adding extra


    public AdapterSubjectPage(Context mCtx, List<ListSubjectPage> batchtlist) {
        this.mCtx = mCtx;
        this.batchtlist = batchtlist;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_subject,parent,false);

        BatchViewHolder BatchViewHolder = new BatchViewHolder(view);
        return BatchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {

        ListSubjectPage Batchdata;
        Batchdata = batchtlist.get(position);
        holder.TheMainText.setText(Batchdata.getSubjectName());
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
        CardView ThisIsCard;
        TextView TheMainText;
        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            ThisIsCard = itemView.findViewById(R.id.ThisIsCard);
            TheMainText = itemView.findViewById(R.id.TheMainText);

            ThisIsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SubjectPage) mCtx).userItemClick(getAdapterPosition());
                }
            });

        }
    }

}
