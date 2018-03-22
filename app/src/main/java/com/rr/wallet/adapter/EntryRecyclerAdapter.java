package com.rr.wallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.wallet.R;
import com.rr.wallet.dao.Entry;

import java.util.ArrayList;

/**
 * Created by ritesh on 14-03-2018.
 */

public class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryViewHolder> {
    private ArrayList<Entry> entryArrayList;

    public EntryRecyclerAdapter(ArrayList<Entry> entryArrayList) {
        this.entryArrayList = entryArrayList;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry_recycler_view, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        holder.bind(entryArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return entryArrayList.size();
    }
}
