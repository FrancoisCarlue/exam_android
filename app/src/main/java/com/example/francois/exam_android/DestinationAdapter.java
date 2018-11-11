package com.example.francois.exam_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DestinationAdapter extends BaseAdapter {

    List<Destination> biblio;

    public class ViewHolder{
        TextView tvType;
        TextView tvTitle;
        ImageView iv;
    }

    LayoutInflater inflater;
    public DestinationAdapter(Context context, List<Destination> objects){
        inflater = LayoutInflater.from(context);
        this.biblio = objects;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private View getInflatedLayoutForType(int type) {
        View convertView;
        if (type == 0) {
            convertView = inflater.inflate(R.layout.destination_item, null);
            return convertView;
        }
        else {
            convertView = inflater.inflate(R.layout.reversed_destination_item, null);
            return convertView;
        }
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            Log.v("test","converView is null");
            holder = new ViewHolder();
            convertView = getInflatedLayoutForType(getItemViewType(position));
            holder.tvType = (TextView) convertView.findViewById(R.id.txtType);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgView) ;
            convertView.setTag(holder);
        }
        else {
            Log.v("test","convertView is not null");
            holder = (ViewHolder) convertView.getTag();
        }

        Destination destination = biblio.get(position);
        holder.tvType.setText(destination.getType());
        holder.tvTitle.setText(destination.getTitle());
        Picasso.get().load(destination.getURL()).into(holder.iv, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load("https://st2.depositphotos.com/5777248/10534/v/950/depositphotos_105340898-stock-illustration-palm-tropical-tree-icon-isometric.jpg").into(holder.iv);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return biblio.size();
    }

    @Override
    public Object getItem(int position) {
        return biblio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
