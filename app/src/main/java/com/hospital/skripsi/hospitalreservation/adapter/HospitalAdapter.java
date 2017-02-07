package com.hospital.skripsi.hospitalreservation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hospital.skripsi.hospitalreservation.DetailHospitalActivity;
import com.hospital.skripsi.hospitalreservation.R;
import com.hospital.skripsi.hospitalreservation.models.Hospital;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by Karen on 2/6/2017.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvHospitalName;
        TextView tvPrice;
        ImageView imageHospital;

        public HospitalViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Hospital feedItem = listItems.get(getAdapterPosition());
                    Intent i = new Intent(mContext, DetailHospitalActivity.class);
                    i.putExtra("id_hospital",feedItem.id_hospital);
                    i.putExtra("hospital_name",feedItem.hospital_name);
                    i.putExtra("telp",feedItem.telp);
                    i.putExtra("email",feedItem.email);
                    i.putExtra("image",feedItem.image);
                    i.putExtra("thumb_image",feedItem.thumb_image);
                    i.putExtra("description",feedItem.description);
                    i.putExtra("address",feedItem.address);
                    i.putExtra("price",feedItem.price);
                    i.putExtra("label_price",feedItem.label_price);
                    mContext.startActivity(i);
                    //Toast.makeText(mContext,feedItem.hospital_name,Toast.LENGTH_LONG).show();
                }
            });
            cv = (CardView)itemView.findViewById(R.id.cvMain);
            tvHospitalName = (TextView)itemView.findViewById(R.id.tvHospitalName);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            imageHospital = (ImageView)itemView.findViewById(R.id.imageHospital);
        }
    }

    List<Hospital> listItems;
    private Context mContext;

    public HospitalAdapter(Context context, List<Hospital> listItems){
        this.listItems = listItems;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main, viewGroup, false);
        HospitalViewHolder view_holder = new HospitalViewHolder(v);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder view_holder, int i) {
        view_holder.tvHospitalName.setText(listItems.get(i).hospital_name);
        view_holder.tvPrice.setText(listItems.get(i).label_price);

        if(listItems.get(i).image.equals("")){
            view_holder.imageHospital.setImageResource(R.drawable.noimage);
        }else{
            Ion.with(mContext)
                    .load(listItems.get(i).thumb_image)
                    .withBitmap()
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage)
                    .intoImageView(view_holder.imageHospital);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
