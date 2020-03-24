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

    List<StopTransactions> stopTransactions;

    //private final List items;
    private final OnItemClickListener listener;

    public RVAdapter(List<StopTransactions> stopTransactions, OnItemClickListener mlistener) {
        this.stopTransactions = stopTransactions;
        this.listener = mlistener;
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
        holder.transactionNo.setText(stopTransactions.get(i).getTransaction_no()+"");
        holder.requestType.setText(stopTransactions.get(i).getRequest_type()+"");
        holder.date.setText(stopTransactions.get(i).getDate()+""); //TODO implement the toString()
        holder.location.setText(stopTransactions.get(i).getLocation()+"");
        holder.direction.setText(stopTransactions.get(i).getDirection()+"");
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
        TextView transactionNo;
        TextView requestType;
        TextView date;
        TextView location;
        TextView direction;

        RVViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            stopID = (TextView) itemView.findViewById(R.id.cvStopID);
            transactionNo = (TextView) itemView.findViewById(R.id.cvTransactionNo);
            requestType = (TextView) itemView.findViewById(R.id.cvRequestType);
            date = (TextView) itemView.findViewById(R.id.cvDate);
            location = (TextView) itemView.findViewById(R.id.cvlocation);
            direction = (TextView) itemView.findViewById(R.id.cvDirection);
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
