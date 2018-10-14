package com.example.chris.outapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;

import java.util.List;

import static com.example.chris.outapp.Utils.calculateTimeSince;

public class OutGoerAdapter extends ArrayAdapter<OutGoer> {

    private Context context;
    private List<OutGoer> outGoers;

    public OutGoerAdapter(@NonNull Context context, int resource, List<OutGoer> outGoers) {
        super(context, resource, outGoers);
        this.context = context;
        this.outGoers = outGoers;
    }

    @Override
    public int getCount() {
        return outGoers.size();
    }

    @Nullable
    @Override
    public OutGoer getItem(int position) {
        return outGoers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.view_feed_item, parent, false);

        TextView tvUserName = convertView.findViewById(R.id.userName);
        TextView tvVenueName = convertView.findViewById(R.id.venueName);
        TextView tvTimeSince = convertView.findViewById(R.id.timeSince);

        tvUserName.setText(outGoers.get(position).getUserName());
        tvVenueName.setText(outGoers.get(position).getVenueName());
        tvTimeSince.setText(calculateTimeSince(context, outGoers.get(position).getTimeMillis()));

        return convertView;
    }
}
