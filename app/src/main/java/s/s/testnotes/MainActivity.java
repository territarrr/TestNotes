package s.s.testnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static android.os.Build.ID;
import static s.s.testnotes.Keys.ADD_NOTE_SUCCSESS;
import static s.s.testnotes.Keys.DEL_NOTE_SUCCSESS;
import static s.s.testnotes.Keys.MENU_ADD;

import static s.s.testnotes.Keys.TEXT;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CM_DELETE_ID = 1;

    private ListView lvData;
    private DB db;
    private SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DB.getInstance(this);
        db.open();

        String[] from = new String[] { DB.COLUMN_TXT };
        int[] to = new int[] {R.id.tvText };

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        getSupportLoaderManager().initLoader(0, null,this);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EachNote.class);
                intent.putExtra(TEXT, db.getNote((int) id));
                intent.putExtra(ID, String.valueOf(id));
                startActivityForResult(intent, 2);
            }
        });

        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                db.delRec(arg3);

                Toast toast = Toast.makeText(MainActivity.this, DEL_NOTE_SUCCSESS, Toast.LENGTH_LONG);
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, MENU_ADD);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddNote.class);
        startActivityForResult(intent, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            db.addRec(data.getStringExtra(TEXT));
            getSupportLoaderManager().restartLoader(0, null, this);
            Toast toast = Toast.makeText(getApplicationContext(),
                    ADD_NOTE_SUCCSESS, Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (requestCode == 2)
        {
            if(resultCode == RESULT_OK) {
                db.delRec(Long.valueOf(data.getStringExtra(ID)));
                getSupportLoaderManager().restartLoader(0, null, this);
                Toast toast = Toast.makeText(getApplicationContext(),
                        DEL_NOTE_SUCCSESS, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        scAdapter.swapCursor(null);
    }

    static class MyCursorLoader extends android.support.v4.content.CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            return cursor;
        }

    }
}
