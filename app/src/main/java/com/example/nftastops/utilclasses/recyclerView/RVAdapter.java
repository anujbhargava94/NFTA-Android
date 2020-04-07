package com.example.nftastops.utilclasses.recyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.stops.StopFragment1;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    List<StopTransactions> stopTransactions;

    //private final List items;
    private final OnItemClickListener listener;

    private String cardType;

    public RVAdapter(List<StopTransactions> stopTransactions, OnItemClickListener mlistener, String mcardType) {
        this.stopTransactions = stopTransactions;
        this.listener = mlistener;
        this.cardType = mcardType;
        //this.items = items;
    }

    public interface OnItemClickListener {
        //void onItemClick(ContentItem item);
        void onItemClick(StopTransactions transaction);
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        RVViewHolder pvh = new RVViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int i) {
        holder.bind(stopTransactions.get(i), listener);

        holder.stopID.setText(stopTransactions.get(i).getStop_id());
        //holder.transactionNo.setText(stopTransactions.get(i).getTransaction_no()+"");
        holder.requestType.setText(stopTransactions.get(i).getRequest_type()+"");
        //holder.county.setText(stopTransactions.get(i).getCounty()+"");
        //holder.date.setText(stopTransactions.get(i).getDate()+""); //TODO implement the toString()
        holder.location.setText(stopTransactions.get(i).getLocation()+"");
        holder.direction.setText(stopTransactions.get(i).getDirection()+"");
        holder.adminUser.setText(stopTransactions.get(i).getAdmin_user_id()+"");

        if(cardType.equals("history")){
            holder.requestType.setVisibility(View.GONE);
            holder.adminUser.setVisibility(View.GONE);
        }
        if(cardType.equals("serviceRequest")){
            holder.location.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return stopTransactions.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class RVViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView stopID;
        //TextView transactionNo;
        TextView requestType;
        //TextView county;
        //TextView date;
        TextView location;
        TextView direction;
        TextView adminUser;

        RVViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            stopID = (TextView) itemView.findViewById(R.id.cvStopID);
            //transactionNo = (TextView) itemView.findViewById(R.id.cvTransactionNo);
            requestType = (TextView) itemView.findViewById(R.id.cvRequestType);
            //county = (TextView) itemView.findViewById(R.id.county);
            //date = (TextView) itemView.findViewById(R.id.cvDate);
            location = (TextView) itemView.findViewById(R.id.cvlocation);
            direction = (TextView) itemView.findViewById(R.id.cvDirection);
            adminUser = (TextView)itemView.findViewById(R.id.cvAdminUser);
        }

        public void bind(final StopTransactions item1, final OnItemClickListener listener) {
            //name.setText(item.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //listener.onItemClick(item);
                    listener.onItemClick(item1);
                }
            });
        }

    }

}
