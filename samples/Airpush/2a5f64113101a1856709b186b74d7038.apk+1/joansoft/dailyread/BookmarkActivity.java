package joansoft.dailyread;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import java.util.Random;

public class BookmarkActivity extends Activity implements OnTabChangeListener, OnItemClickListener {
    public static int[] adColors = new int[]{14605278, -526345, 16776669, 15792121, 16381894};
    SimpleCursorAdapter[] adapters = new SimpleCursorAdapter[3];
    Cursor[] cursors = new Cursor[3];
    DBAdapter db;
    String[] options = new String[]{"Saved Words", "Saved Quotes", "Saved Jokes"};
    private Random random = new Random();
    TabHost tabHost;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_layout);
        this.db = new DBAdapter(this);
        this.db.open();
        this.tabHost = (TabHost) findViewById(R.id.tabhost);
        this.tabHost.setOnTabChangedListener(this);
        this.tabHost.setup();
        int[] id = new int[]{R.id.wordBkList, R.id.quoteBkList, R.id.jokeBkList};
        for (int i = 0; i < 3; i++) {
            ListView lView = (ListView) findViewById(id[i]);
            this.tabHost.addTab(this.tabHost.newTabSpec(this.options[i]).setIndicator(this.options[i]).setContent(id[i]));
            this.cursors[i] = this.db.getAll(DBAdapter.DATABASE_TABLES[i]);
            this.adapters[i] = new SimpleCursorAdapter(this, R.layout.dr_news_main, this.cursors[i], new String[]{DBAdapter.KEY_TITLE}, new int[]{R.id.text});
            lView.setAdapter(this.adapters[i]);
            lView.setOnItemClickListener(this);
            lView.setOnCreateContextMenuListener(this);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.db.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bk_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back /*2131165210*/:
                finish();
                break;
        }
        return true;
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int selection, long arg3) {
        int currentTab = this.tabHost.getCurrentTab();
        if (currentTab >= 0 && currentTab < 3) {
            Cursor cursor = (Cursor) this.adapters[currentTab].getItem(selection);
            if (cursor != null) {
                Intent intent = new Intent(this, BookmarkDisplayActivity.class);
                intent.putExtra("details", cursor.getString(2));
                startActivity(intent);
            }
        }
    }

    public void onTabChanged(String arg0) {
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        menu.add("Delete");
    }

    public boolean onContextItemSelected(MenuItem item) {
        try {
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            int currentTab = this.tabHost.getCurrentTab();
            if (currentTab >= 0 && currentTab < 3) {
                Cursor cursor = (Cursor) this.adapters[currentTab].getItem(info.position);
                if (cursor == null) {
                    return false;
                }
                this.db.delete((long) cursor.getInt(0), DBAdapter.DATABASE_TABLES[currentTab]);
                this.cursors[currentTab].requery();
                this.adapters[currentTab].notifyDataSetChanged();
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
