package com.iam_sid.jsonparsingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdap extends BaseAdapter {
    ArrayList<String> Numbers;
    Context context;
    ArrayList<String> Emails;
    private static LayoutInflater inflater=null;

    public CustomAdap( Context context, ArrayList<String> numbers, ArrayList<String> emails) {
        Numbers = numbers;
        context = context;
        Emails = emails;

        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView mono;
        TextView email;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView=inflater.inflate(R.layout.list_item,null);

        Holder holder =new Holder();
        holder.mono=(TextView)rowView.findViewById(R.id.mobile);
        holder.email=(TextView)rowView.findViewById(R.id.email);

        holder.mono.setText(Numbers.get(position));
        holder.email.setText(Emails.get(position));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You Clicked "+Numbers.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
