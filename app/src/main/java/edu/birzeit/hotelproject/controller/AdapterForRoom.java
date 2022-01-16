package edu.birzeit.hotelproject.controller;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Room;

public class AdapterForRoom extends RecyclerView.Adapter<AdapterForRoom.ViewHolder> {
    private Context context;
    private List<Room> items;


    public AdapterForRoom(Context context, List<Room> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_room_details,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Room room = items.get(position);
        CardView cardView = holder.cardView;
        TextView txt = (TextView)cardView.findViewById(R.id.txtName_room);
        txt.setText(room.toString());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , BookDetailsActivity.class);
                intent.putExtra("RoomNumber" , room.getRoom_number());
                intent.putExtra("RoomType" , room.getRoom_type());
                intent.putExtra("RoomPrice" , room.getRoom_price());
                intent.putExtra("RoomDescreption" , room.getRoom_description());
                intent.putExtra("RoomID",room.getRoom_id());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }
}

