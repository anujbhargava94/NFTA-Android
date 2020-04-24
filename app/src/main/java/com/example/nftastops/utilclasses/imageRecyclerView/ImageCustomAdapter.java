package com.example.nftastops.utilclasses.imageRecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nftastops.R;
import com.example.nftastops.model.ImageItem;

import java.util.List;

public class ImageCustomAdapter extends RecyclerView.Adapter<ImageCustomAdapter.RVViewHolder> {

    List<ImageItem> imageItem;

    public ImageCustomAdapter(List<ImageItem> imageItem) {
        this.imageItem = imageItem;
    }

    @NonNull
    @Override
    public ImageCustomAdapter.RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_view, parent, false);
        ImageCustomAdapter.RVViewHolder pvh = new ImageCustomAdapter.RVViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCustomAdapter.RVViewHolder holder,final int i) {
        holder.image.setImageBitmap(imageItem.get(i).getImgBitmap());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onItemClick(item);
                imageItem.remove(i); //or some other task
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageItem.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class RVViewHolder extends RecyclerView.ViewHolder {

        ImageButton image;
        TextView deleteButton;
        RVViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_item);
            deleteButton = itemView.findViewById(R.id.delete_img_item);
        }

    }
}