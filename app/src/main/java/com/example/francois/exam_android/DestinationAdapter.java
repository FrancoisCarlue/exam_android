package com.example.francois.exam_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            Log.v("test","converView is null");
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.destination_item, null);
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
        Picasso.get().load(destination.getURL()).into(holder.iv);
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
