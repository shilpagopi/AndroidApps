package com.example.couponbox0.ui.main;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.ListView;

import com.example.couponbox0.Coupon;
import com.example.couponbox0.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private int index = 0;
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //final TextView textView2 = root.findViewById(R.id.trialtext);
        //if(fetchInbox()==null)
            //textView2.setText("changed text");
        HashMap<String,List<Coupon>> smshashmap = fetchInbox();
        if (smshashmap != null) {
            ListView lViewSMS = root.findViewById(R.id.listViewSMS);
            CouponAdapter adapter;
            switch(index){
                case 1: adapter = new CouponAdapter(smshashmap.get("coupon"), getActivity()); break;
                case 2: adapter = new CouponAdapter(smshashmap.get("travel"), getActivity()); break;
                case 3: adapter = new CouponAdapter(smshashmap.get("shopping"), getActivity()); break;
                case 4: adapter = new CouponAdapter(smshashmap.get("health"), getActivity()); break;
                default: adapter = new CouponAdapter(smshashmap.get("other"), getActivity()); break;
            }
            //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
        return root;
    }

    public HashMap<String,List<Coupon>> initHashMap(){
        HashMap<String,List<Coupon>> smshashmap = new HashMap<>();

        List<Coupon> sms = new ArrayList<Coupon>();
        smshashmap.put("coupon",sms);
        List<Coupon> sms2 = new ArrayList<Coupon>();
        smshashmap.put("shopping",sms2);
        List<Coupon> sms3 = new ArrayList<Coupon>();
        smshashmap.put("health",sms3);
        List<Coupon> sms4 = new ArrayList<Coupon>();
        smshashmap.put("other",sms4);
        List<Coupon> sms5 = new ArrayList<Coupon>();
        smshashmap.put("travel",sms5);

        return smshashmap;
    }

    public HashMap<String,List<Coupon>> fetchInbox()
    {
        HashMap<String,List<Coupon>> smshashmap = initHashMap();
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getActivity().getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        cursor.moveToFirst();
        int smscount = 0;
        while  (cursor.moveToNext() && smscount<50)
        {
            smscount+=1;
            String address = cursor.getString(1);
            String body = cursor.getString(3);

            System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);

            Coupon c = new Coupon();
            c.content = body.replaceAll("\n"," ");
            c.sender = address;
            c.setDiscount();
            c.setCouponcode();

            smshashmap.get(c.getCategory()).add(c);
            //sms.add(address+"\n"+body);
        }

        return smshashmap;

    }
}