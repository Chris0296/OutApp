package com.example.chris.outapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.view.OnItemClickListener;

import java.util.List;

public class OutGoerRecyclerAdapter extends RecyclerView.Adapter<OutGoerRecyclerAdapter.OutGoerViewHolder> {
    private List<OutGoer> outGoerList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public OutGoerRecyclerAdapter(Context context, List<OutGoer> outGoerList, OnItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.outGoerList = outGoerList;
        this.onItemClickListener = onItemClickListener;
    }
    public OutGoer getItem(int id){
        return outGoerList.get(id);
    }
    @NonNull
    @Override
    public OutGoerRecyclerAdapter.OutGoerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.view_feed_item, viewGroup, false);
        return new OutGoerRecyclerAdapter.OutGoerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OutGoerViewHolder outGoerViewHolder, int i) {
        outGoerViewHolder.bind(outGoerList.get(i), onItemClickListener);
        outGoerViewHolder.textViewUserName.setText(outGoerList.get(i).getUserName());
        outGoerViewHolder.textViewVenueName.setText(outGoerList.get(i).getVenueName());
        outGoerViewHolder.textViewTimeSince.setText(Utils.calculateTimeSince(context, outGoerList.get(i).getTimeMillis()));
    }
    @Override
    public int getItemCount() {
        if(outGoerList != null){
            return outGoerList.size();
        } else {
            return 0;
        }
    }
    public static class OutGoerViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewUserName;
        private TextView textViewVenueName;
        private TextView textViewTimeSince;
        public OutGoerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.userName);
            textViewVenueName = itemView.findViewById(R.id.venueName);
            textViewTimeSince = itemView.findViewById(R.id.timeSince);
        }
        public void bind(OutGoer outGoer, OnItemClickListener onItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(outGoer);
                }
            });
        }
    }
}