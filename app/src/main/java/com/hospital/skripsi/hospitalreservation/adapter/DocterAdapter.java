package com.hospital.skripsi.hospitalreservation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hospital.skripsi.hospitalreservation.DetailScheduleActivity;
import com.hospital.skripsi.hospitalreservation.R;
import com.hospital.skripsi.hospitalreservation.models.Docter;

import java.util.List;

/**
 * Created by Karen on 2/21/2017.
 */

public class DocterAdapter extends RecyclerView.Adapter<DocterAdapter.DocterViewHolder> {
    public class DocterViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvDocterName;
        TextView tvSpeciality;

        public DocterViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Docter feedItem = listItems.get(getAdapterPosition());
                    Intent i = new Intent(mContext, DetailScheduleActivity.class);
                    i.putExtra("id_docter",feedItem.id_docter);
                    i.putExtra("docter_name",feedItem.docter_name);
                    mContext.startActivity(i);
                }
            });

            cv = (CardView)itemView.findViewById(R.id.cvDocter);
            tvDocterName = (TextView)itemView.findViewById(R.id.tvDocterName);
            tvSpeciality = (TextView)itemView.findViewById(R.id.tvSpeciality);
        }
    }

    List<Docter> listItems;
    private Context mContext;

    public DocterAdapter(Context context, List<Docter> listItems){
        this.listItems = listItems;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DocterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_docter, viewGroup, false);
        DocterViewHolder view_holder = new DocterViewHolder(v);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(DocterViewHolder view_holder, int i) {
        view_holder.tvDocterName.setText(listItems.get(i).docter_name);
        view_holder.tvSpeciality.setText(listItems.get(i).speciality);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}
