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
import com.hospital.skripsi.hospitalreservation.models.Room;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by Karen on 2/7/2017.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvRoomName;
        TextView tvPrice;
        ImageView imageRoom;

        public RoomViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cvRoom);
            tvRoomName = (TextView)itemView.findViewById(R.id.tvRoomName);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            imageRoom = (ImageView)itemView.findViewById(R.id.imageRoom);
        }
    }

    List<Room> listItems;
    private Context mContext;

    public RoomAdapter(Context context, List<Room> listItems){
        this.listItems = listItems;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_room, viewGroup, false);
        RoomViewHolder view_holder = new RoomViewHolder(v);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(RoomViewHolder view_holder, int i) {
        view_holder.tvRoomName.setText(listItems.get(i).room_name);
        view_holder.tvPrice.setText(listItems.get(i).label_price);

        if(listItems.get(i).image.equals("")){
            view_holder.imageRoom.setImageResource(R.drawable.noimage);
        }else{
            Ion.with(mContext)
                    .load(listItems.get(i).image)
                    .withBitmap()
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage)
                    .intoImageView(view_holder.imageRoom);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
