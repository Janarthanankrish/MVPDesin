package com.mvpdesign.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonfeaturelib.Common.CommonTextValidations;
import com.mvpdesign.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    Context context;
    ArrayList<DashboardPojo> newsList;

    public DashboardAdapter(Context context, ArrayList<DashboardPojo> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dashboard_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        DashboardPojo dashboardPojo=newsList.get(position);
        myViewHolder.name.setText(dashboardPojo.title);
        myViewHolder.description.setText(dashboardPojo.description);
        myViewHolder.date.setText(dashboardPojo.date);
        CommonTextValidations.LoadGlideImages(context,dashboardPojo.image,myViewHolder.image,R.mipmap.ic_launcher);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView image;
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_description)
        TextView description;
        @BindView(R.id.tv_date)
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
