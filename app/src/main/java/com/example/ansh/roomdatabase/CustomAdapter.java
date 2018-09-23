package com.example.ansh.roomdatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ansh.roomdatabase.data.ListItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<ListItem> mListItems;
    private Context mContext;
    private SimpleRecyclerViewOnClick mSimpleRecyclerViewOnClick;

    public CustomAdapter(List<ListItem> listItems, Context context, SimpleRecyclerViewOnClick simpleRecyclerViewOnClick) {
        mListItems = listItems;
        mContext = context;
        mSimpleRecyclerViewOnClick = simpleRecyclerViewOnClick;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        ListItem listItem = mListItems.get(position);
        customViewHolder.dateAndTime.setText(listItem.getDateAndTime());
        customViewHolder.message.setText(listItem.getMessage());
        customViewHolder.coloredCircle.setImageResource(listItem.getColorResource());

        customViewHolder.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        // helps the adapter to decide how many items to manage.
        return mListItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView coloredCircle;
        private TextView dateAndTime;
        private TextView message;
        private ViewGroup container;
        private ProgressBar mProgressBar;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            coloredCircle = itemView.findViewById(R.id.imv_list_item_circle);
            dateAndTime = itemView.findViewById(R.id.lbl_date_and_time);
            message = itemView.findViewById(R.id.lbl_message);
            mProgressBar = itemView.findViewById(R.id.pro_item_data);
            container = itemView.findViewById(R.id.root_list_item);

            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mSimpleRecyclerViewOnClick.onClick(container, getAdapterPosition());
        }
    }

}
