package edu.birzeit.hotelproject.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Reservations;

public class AdapterForAllReservation extends RecyclerView.Adapter<AdapterForAllReservation.ViewHolder> {
    private Context context;
    private List<Reservations>items;


    public AdapterForAllReservation(Context context, List<Reservations> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.all_reservation_card,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reservations reservations = items.get(position);
        CardView cardView = holder.cardView;
        TextView txt = (TextView)cardView.findViewById(R.id.txtName);
        txt.setText(reservations.toString());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
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

