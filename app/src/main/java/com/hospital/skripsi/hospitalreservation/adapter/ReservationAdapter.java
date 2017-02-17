package com.hospital.skripsi.hospitalreservation.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hospital.skripsi.hospitalreservation.R;
import com.hospital.skripsi.hospitalreservation.models.Reservation;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by Karen on 2/17/2017.
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvHospitalName;
        TextView tvCreated;
        TextView tvTotal;
        ImageView imageReservation;

        public ReservationViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cvReservation);
            tvHospitalName = (TextView)itemView.findViewById(R.id.tvHospitalName);
            tvCreated = (TextView)itemView.findViewById(R.id.tvCreated);
            tvTotal = (TextView)itemView.findViewById(R.id.tvTotal);
            imageReservation = (ImageView)itemView.findViewById(R.id.imageReservation);
        }
    }

    List<Reservation> listItems;
    private Context mContext;

    public ReservationAdapter(Context context, List<Reservation> listItems){
        this.listItems = listItems;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_reservation, viewGroup, false);
        ReservationViewHolder view_holder = new ReservationViewHolder(v);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder view_holder, int i) {
        view_holder.tvHospitalName.setText(listItems.get(i).rumahsakit);
        view_holder.tvTotal.setText(listItems.get(i).total);
        view_holder.tvCreated.setText(listItems.get(i).created);

        if(listItems.get(i).reservation_image.equals("")){
            view_holder.imageReservation.setImageResource(R.drawable.noimage);
        }else{
            Ion.with(mContext)
                    .load(listItems.get(i).reservation_image)
                    .withBitmap()
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage)
                    .intoImageView(view_holder.imageReservation);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
