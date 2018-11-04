package com.example.chris.outapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.view.OnItemClickListener;

import java.util.List;

public class VenueFriendRecyclerAdapter extends RecyclerView.Adapter<VenueFriendRecyclerAdapter.VenueFriendViewHolder> {

    private List<User> venueFriendList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public static class VenueFriendViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewFriendName;

        public VenueFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFriendName = itemView.findViewById(R.id.friendName);
        }

        public void bind(User friend, OnItemClickListener onItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(friend);
                }
            });
        }
    }

    public VenueFriendRecyclerAdapter(Context context, List<User> venueFriendList, OnItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.venueFriendList = venueFriendList;
        this.onItemClickListener = onItemClickListener;
    }

    public User getItem(int id){
        return venueFriendList.get(id);
    }

    @NonNull
    @Override
    public VenueFriendRecyclerAdapter.VenueFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.view_friend_item, viewGroup, false);
        return new VenueFriendRecyclerAdapter.VenueFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueFriendRecyclerAdapter.VenueFriendViewHolder venueFriendViewHolder, int i) {
        venueFriendViewHolder.bind(venueFriendList.get(i), onItemClickListener);
        if(venueFriendList.get(i).getUserId() != null) {
            venueFriendViewHolder.textViewFriendName.setText(venueFriendList.get(i).getUserName());
        } else {
            venueFriendViewHolder.textViewFriendName.setText("No One Going Here Tonight :(");
        }
    }

    @Override
    public int getItemCount() {
        if(venueFriendList != null){
            return venueFriendList.size();
        } else {
            return 0;
        }
    }
}
