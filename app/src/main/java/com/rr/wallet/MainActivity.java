package com.rr.wallet;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rr.wallet.adapter.ViewPagerAdapter;
import com.rr.wallet.dao.Entry;
import com.rr.wallet.db.DbHelper;
import com.rr.wallet.fragment.AddEntryDialogFragment;
import com.rr.wallet.provider.SqliteProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private ContentResolver myCR;
    private Context context;
    //    private TabLayout tabLayout;
//    private RecyclerView entry_recyclerView;
    private ArrayList<Entry> entryArrayList;

    @BindView(R.id.img_toolbar_add_entry)
    ImageView img_toolbar_add_entry;
    //    @BindView(R.id.entry_recyclerView)
//    RecyclerView entry_recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);

        initialiseViewControls();

        myCR = context.getContentResolver();
//        insertEntry();
//        queryEntry("bank");
//        deleteEntry("Bank");
//        updateEntry("ICICI");

    }

    private void initialiseViewControls() {
/*        entryArrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            entryArrayList.add(new Entry("1", "To paytm for metro recharge", "500000", "500000", "500000"));

//        entry_recyclerView = (RecyclerView) findViewById(R.id.entry_recyclerView);
        EntryRecyclerAdapter entryRecyclerAdapter = new EntryRecyclerAdapter(entryArrayList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(context);
        entry_recyclerView.setLayoutManager(lm);
        entry_recyclerView.setAdapter(entryRecyclerAdapter);*/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("ICICI"));
        tabLayout.addTab(tabLayout.newTab().setText("PAYTM"));
        tabLayout.addTab(tabLayout.newTab().setText("WALLET"));

        tabLayout.setupWithViewPager(viewPager);

    }

    @OnClick(R.id.img_toolbar_add_entry)
    void addEntry(View view) {
        if (view.getId() == R.id.img_toolbar_add_entry) {
            AddEntryDialogFragment newFragment = new AddEntryDialogFragment();
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    @OnClick(R.id.img_toolbar_add_tab)
    void addNewTab(View view) {
        if (view.getId() == R.id.img_toolbar_add_tab) {
            Toast.makeText(context, "Add new Tab", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertEntry() {

        ContentValues values = new ContentValues();
        values.put(DbHelper.NOTE, "Salary");
        values.put(DbHelper.AMOUNT, 85000);
        values.put(DbHelper.TYPE, "ICICI");
        values.put(DbHelper.DATE, "07/03/2018 05:27 PM");

        myCR.insert(SqliteProvider.CONTENT_URI, values);
    }

    public Entry queryEntry(String type) {

        String[] projection = {DbHelper.ID, DbHelper.NOTE, DbHelper.AMOUNT, DbHelper.TYPE, DbHelper.DATE};

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        Cursor cursor = myCR.query(
                SqliteProvider.CONTENT_URI,
                projection,
                selection,
                null,
                null
        );

        Entry entry = new Entry();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            entry.setId(cursor.getString(0));
            entry.setNote(cursor.getString(1));
            entry.setAmount(cursor.getString(2));
            entry.setType(cursor.getString(3));
            entry.setDate(cursor.getString(4));
            cursor.close();
        } else {
            entry = null;
        }

        return entry;
    }

    public void deleteEntry(String type) {

//        boolean result = false;

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        int rowsDeleted = myCR.delete(SqliteProvider.CONTENT_URI, selection, null);
        Toast.makeText(context, "" + rowsDeleted, Toast.LENGTH_SHORT).show();

//        if (rowsDeleted > 0)
//            result = true;
//
//        return result;
    }

    public void updateEntry(String type) {

        String[] projection = {DbHelper.ID, DbHelper.NOTE, DbHelper.AMOUNT, DbHelper.TYPE, DbHelper.DATE};

        String selection = DbHelper.TYPE + " = \"" + type + "\"";

        ContentValues values = new ContentValues();
        values.put(DbHelper.NOTE, "BALANCE");
//        values.put(DbHelper.AMOUNT, 19200);
        values.put(DbHelper.TYPE, "bank");
        values.put(DbHelper.DATE, "10/03/2018 05:30 PM");

        int rowsUpdated = myCR.update(
                SqliteProvider.CONTENT_URI,
                values,
                selection,
                null
        );

        Toast.makeText(context, "" + rowsUpdated, Toast.LENGTH_SHORT).show();

    }

}
