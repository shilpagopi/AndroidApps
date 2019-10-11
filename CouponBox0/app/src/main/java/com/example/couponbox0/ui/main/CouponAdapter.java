package com.example.couponbox0.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couponbox0.Coupon;
import com.example.couponbox0.R;

import java.util.List;

public class CouponAdapter extends BaseAdapter {

    public List<Coupon> couponList;
    public Context context;

    public CouponAdapter(List<Coupon> couponList, Context ctx) {
        this.couponList = couponList;
        this.context = ctx;
    }

    @Override
    public int getCount() {
        return couponList.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return couponList.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.coupon, parent, false);
        }

        final Coupon c = couponList.get(position);
        TextView sender = (TextView) v.findViewById(R.id.sender);
        TextView discount = (TextView) v.findViewById(R.id.discount);
        TextView content = (TextView) v.findViewById(R.id.content);
        TextView couponcode = (TextView) v.findViewById(R.id.couponcode);
        sender.setText(c.sender);
        discount.setText(c.discount);
        content.setText(c.content);
        couponcode.setText(c.couponcode);

        Button viewSms = (Button) v.findViewById(R.id.viewsms);
        viewSms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, c.content, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}