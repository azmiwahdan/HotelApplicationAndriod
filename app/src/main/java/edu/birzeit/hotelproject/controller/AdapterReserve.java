package edu.birzeit.hotelproject.controller;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Reserve;

public class AdapterReserve extends RecyclerView.Adapter<AdapterReserve.ViewHolder> {
    private Context context;
    private List<Reserve> items;
    private TextView textViewRoomNum ;
    private TextView textViewStartDate;
    private TextView textViewEndDate;


    public AdapterReserve(Context context, List<Reserve> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.reserve_card,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reserve reserve = items.get(position);
        CardView cardView = holder.cardView;
//        TextView txt = (TextView)cardView.findViewById(R.id.txtName_room);
//        txt.setText(reserve.toString());

        Log.d("Reserve object = " , String.valueOf(reserve.getRoomNumber()));

        textViewRoomNum = (TextView)cardView.findViewById(R.id.roomNum);
        textViewStartDate = (TextView)cardView.findViewById(R.id.startDate);
        textViewEndDate = (TextView)cardView.findViewById(R.id.endDate);

//        textViewRoomNum.setText(reserve.getRoomNumber());
//        textViewStartDate.setText(reserve.getEndDate());
//        textViewEndDate.setText(reserve.getEndDate());

        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(context, CheckOutActivity.class);
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

