package s.s.testnotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


import static s.s.testnotes.DB.getInstance;
import static s.s.testnotes.Keys.BUTTON_CANCEL_TXT;
import static s.s.testnotes.Keys.BUTTON_OK_TXT;

import static s.s.testnotes.Keys.MENU_DEL;
import static s.s.testnotes.Keys.NOTE;
import static s.s.testnotes.Keys.REQUEST_CODE_EDT;


public class EachNote extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTextEachNote;
    private Intent intent;
    private Note note;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_note);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        note = (Note) intent.getParcelableExtra(NOTE);;

        tvTextEachNote = (TextView) findViewById(R.id.tvTextEachNote);
        tvTextEachNote.setText(note.getText());

        Button btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);

        setResult(Activity.RESULT_CANCELED, intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnDel:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle(MENU_DEL);  // заголовок
                ad.setPositiveButton(BUTTON_OK_TXT, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        intent.putExtra(NOTE, note);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
                ad.setNegativeButton(BUTTON_CANCEL_TXT, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.show();
                break;
            case R.id.btnEdit:
                Intent intent1 = new Intent(this, AddNoteActivity.class);
                intent1.putExtra(NOTE, note);
                startActivityForResult(intent1, REQUEST_CODE_EDT);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EDT) {
            if(resultCode == RESULT_OK) {
                DB db = getInstance(EachNote.this);
                note = (Note) data.getParcelableExtra(NOTE);
                Log.d("myLogs", note.getText() + " " + String.valueOf(note.getId()));
                db.updRec(note.getId(), note.getText());
                tvTextEachNote.setText(note.getText());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return true;
        }
    }
}
