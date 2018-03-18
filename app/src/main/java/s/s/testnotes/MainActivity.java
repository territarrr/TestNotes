package s.s.testnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static s.s.testnotes.Keys.ADD_NOTE_SUCCSESS;
import static s.s.testnotes.Keys.BUTTON_CANCEL_TXT;
import static s.s.testnotes.Keys.BUTTON_OK_TXT;

import static s.s.testnotes.Keys.CTX_MENU_DEL_ID;
import static s.s.testnotes.Keys.DEL_NOTE_SUCCSESS;
import static s.s.testnotes.Keys.INT_NULL;


import static s.s.testnotes.Keys.MENU_DEL;
import static s.s.testnotes.Keys.NOTE;
import static s.s.testnotes.Keys.REQUEST_CODE_ADD;
import static s.s.testnotes.Keys.REQUEST_CODE_DEL;
import static s.s.testnotes.Keys.REQUEST_CODE_EDT;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvData;
    private DB db;
    private SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DB.getInstance(this);
        db.open();

        String[] from = new String[]{DB.COLUMN_TXT};
        int[] to = new int[]{R.id.tvText};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, INT_NULL);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        registerForContextMenu(lvData);
        getSupportLoaderManager().initLoader(INT_NULL, null, this);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = new Note(id, db.getNote((int) id));
                Intent intent = new Intent(MainActivity.this, EachNote.class);
                intent.putExtra(NOTE, note);
                startActivityForResult(intent, REQUEST_CODE_DEL);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(INT_NULL, CTX_MENU_DEL_ID, INT_NULL, getString(R.string.del_btn));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CTX_MENU_DEL_ID:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle(MENU_DEL);  // заголовок
                ad.setPositiveButton(BUTTON_OK_TXT, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        db.delRec(info.id);

                        Toast toast = Toast.makeText(MainActivity.this, DEL_NOTE_SUCCSESS, Toast.LENGTH_LONG);
                        getSupportLoaderManager().restartLoader(INT_NULL, null, MainActivity.this);
                    }
                });
                ad.setNegativeButton(BUTTON_CANCEL_TXT, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getSupportLoaderManager().restartLoader(INT_NULL, null, MainActivity.this);

        Note note = (Note) data.getParcelableExtra(NOTE);

        switch (requestCode) {
            case REQUEST_CODE_ADD:
                if (resultCode == RESULT_OK) {
                    db.addRec(note.getText());
                    getSupportLoaderManager().restartLoader(INT_NULL, null, this);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            ADD_NOTE_SUCCSESS, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case REQUEST_CODE_DEL:
                if (resultCode == RESULT_OK) {
                    db.delRec(Long.valueOf(note.getId()));
                    getSupportLoaderManager().restartLoader(INT_NULL, null, MainActivity.this);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            DEL_NOTE_SUCCSESS, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case REQUEST_CODE_EDT:
                if (resultCode == RESULT_OK) {
                    db.updRec(note.getId(), note.getText());
                }
                break;
            default:
                break;
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
