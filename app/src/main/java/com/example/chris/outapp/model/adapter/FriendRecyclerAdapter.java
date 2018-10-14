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
import com.example.chris.outapp.view.OnItemClickListener;

import java.util.List;

public class FriendRecyclerAdapter extends RecyclerView.Adapter<FriendRecyclerAdapter.FriendViewHolder> {

    private List<User> friendList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public FriendRecyclerAdapter(Context context, List<User> friendList, OnItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.friendList = friendList;
        this.onItemClickListener = onItemClickListener;
    }

    public User getItem(int id){
        return friendList.get(id);
    }

    @NonNull
    @Override
    public FriendRecyclerAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.view_friend_item, viewGroup, false);
        return new FriendRecyclerAdapter.FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder friendViewHolder, int i) {
        friendViewHolder.bind(friendList.get(i), onItemClickListener);
        friendViewHolder.textViewFriendName.setText(friendList.get(i).getUserName());
        if(friendList.get(i).getDestinations() != null){
            if(friendList.get(i).getDestinations().size() == 1){
                friendViewHolder.textViewFriendDestinations.setText(
                        friendList.get(i).getDestinations().size() + context.getString(R.string.destination));
            } else {
                friendViewHolder.textViewFriendDestinations.setText(
                        friendList.get(i).getDestinations().size() + context.getString(R.string.destinations));
            }
        } else {
            friendViewHolder.textViewFriendDestinations.setText(R.string.noDestinations);
        }
    }

    @Override
    public int getItemCount() {
        if(friendList != null){
            return friendList.size();
        } else {
            return 0;
        }
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewFriendName;
        private TextView textViewFriendDestinations;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFriendName = itemView.findViewById(R.id.friendName);
            textViewFriendDestinations = itemView.findViewById(R.id.destinations);
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
}