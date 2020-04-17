package com.example.nftastops.utilclasses.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;

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
        holder.stopIDText.setText("Stop ID");
        holder.countyText.setText("County");
        holder.transactionTypeText.setText("Transaction Type");
        holder.requestTypeText.setText("Request Type");
        holder.adminUserText.setText("Admin User");
        holder.directionText.setText("Direction");
        holder.stopID.setText(stopTransactions.get(i).getStop_id());
        //holder.transactionNo.setText(stopTransactions.get(i).getTransaction_no()+"");
        holder.requestType.setText(stopTransactions.get(i).getRequest_type() + "");

        if (stopTransactions.get(i).getCounty()!= null){
        holder.county.setText(stopTransactions.get(i).getCounty().getDisplay_name() + "");}
        holder.transactionType.setText(stopTransactions.get(i).getTransaction_type());
        //holder.date.setText(stopTransactions.get(i).getDate()+""); //TODO implement the toString()
        //holder.location.setText(stopTransactions.get(i).getLocation()+"");
        if(stopTransactions.get(i).getDirection() != null){
        holder.direction.setText(stopTransactions.get(i).getDirection().getDisplay_name() + "");}
        holder.adminUser.setText(stopTransactions.get(i).getRequested_user() + "");

        if (cardType.equals("history")) {
            holder.requestTypeText.setVisibility(View.GONE);
            holder.requestType.setVisibility(View.GONE);
            holder.adminUserText.setVisibility(View.GONE);
            holder.adminUser.setVisibility(View.GONE);
        }
        if (cardType.equals("serviceRequest")) {
            holder.countyText.setVisibility(View.GONE);
            holder.county.setVisibility(View.GONE);
            holder.transactionTypeText.setVisibility(View.GONE);
            holder.transactionType.setVisibility(View.GONE);
        }

        if (holder.transactionType.getText().toString().equals("remove")) {
            //if(stopTransactions.get(i).getTransaction_type().equals("remove")){
            holder.county.setVisibility(View.GONE);
            holder.direction.setVisibility(View.GONE);
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
        TextView stopIDText;
        TextView stopID;
        TextView transactionTypeText;
        TextView transactionType;
        TextView requestTypeText;
        TextView requestType;
        TextView countyText;
        TextView county;
        //TextView date;
        //TextView location;
        TextView directionText;
        TextView direction;
        TextView adminUserText;
        TextView adminUser;

        RVViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            stopID = (TextView) itemView.findViewById(R.id.times);
            stopIDText = (TextView) itemView.findViewById(R.id.cvStopID);
            //transactionNo = (TextView) itemView.findViewById(R.id.cvTransactionNo);
            requestTypeText = (TextView) itemView.findViewById(R.id.cvRequestTypetext);
            requestType = (TextView) itemView.findViewById(R.id.cvRequestType);
            countyText = (TextView) itemView.findViewById(R.id.cvCountytext);
            county = (TextView) itemView.findViewById(R.id.cvCounty);
            transactionTypeText = (TextView)itemView.findViewById(R.id.cvTransactionTypetext);
            transactionType = (TextView) itemView.findViewById(R.id.cvTransactionType);
            //date = (TextView) itemView.findViewById(R.id.cvDate);
            //location = (TextView) itemView.findViewById(R.id.cvlocation);
            directionText = (TextView) itemView.findViewById(R.id.cvDirectiontext);
            direction = (TextView) itemView.findViewById(R.id.cvDirection);
            adminUserText = (TextView) itemView.findViewById(R.id.cvAdminUsertext);
            adminUser = (TextView) itemView.findViewById(R.id.cvAdminUser);
        }

        public void bind(final StopTransactions item1, final OnItemClickListener listener) {
            //name.setText(item.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onItemClick(item);
                    listener.onItemClick(item1);
                }
            });
        }

    }

    public void setData(List<StopTransactions> stopTransactions) {
        this.stopTransactions = stopTransactions;
        notifyDataSetChanged();
        //notifyDataSetChanged();
    }

}
