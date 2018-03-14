package com.rr.wallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rr.wallet.R;
import com.rr.wallet.dao.Entry;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 14-03-2018.
 */

public class EntryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_note)
    TextView txt_note;
    @BindView(R.id.txt_debitAmount)
    TextView txt_debitAmount;
    @BindView(R.id.txt_creditAmount)
    TextView txt_creditAmount;
    @BindView(R.id.txt_balance)
    TextView txt_balance;

/*    private TextView txt_date;
    private TextView txt_note;
    private TextView txt_debitAmount;
    private TextView txt_creditAmount;
    private TextView txt_balance;*/


    public EntryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Entry entry) {
        txt_date.setText(entry.getDate());
        txt_note.setText(entry.getNote());
    }
}
