package com.rr.wallet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.wallet.R;
import com.rr.wallet.adapter.EntryRecyclerAdapter;
import com.rr.wallet.dao.Entry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 22-Mar-18.
 */

public class FragmentTab extends Fragment {
    private ArrayList<Entry> entryArrayList;

    @BindView(R.id.entry_recyclerView)
    RecyclerView entry_recyclerView;

    public FragmentTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);

        entryArrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            entryArrayList.add(new Entry("1", "To paytm for metro recharge", "500000", "500000", "22 Mar"));

//        entry_recyclerView = (RecyclerView) findViewById(R.id.entry_recyclerView);
        EntryRecyclerAdapter entryRecyclerAdapter = new EntryRecyclerAdapter(entryArrayList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        entry_recyclerView.setLayoutManager(lm);
        entry_recyclerView.setAdapter(entryRecyclerAdapter);

        return view;
    }
}
