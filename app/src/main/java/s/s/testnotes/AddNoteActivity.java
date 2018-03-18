package s.s.testnotes;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import static s.s.testnotes.Keys.BUTTON_CANCEL_TXT;
import static s.s.testnotes.Keys.BUTTON_OK_TXT;
import static s.s.testnotes.Keys.MENU_EDT;
import static s.s.testnotes.Keys.NOTE;


public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private Note note;
    private Intent intent;
    private TextView tvText;
    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        intent = getIntent();

        note = (Note) intent.getParcelableExtra(NOTE);

        btn = (Button) findViewById(R.id.btnAdd);

        tvText = (TextView) findViewById(R.id.tvText);

        etText = (EditText) findViewById(R.id.etEdit);

        if (note == null) {
            setTitle(getString(R.string.add_title));
            tvText.setText(getString(R.string.add_tvText));
            btn.setText(getString(R.string.add_btn));
        } else {
            setTitle(getString(R.string.edt_title));
            tvText.setText(getString(R.string.edt_tvText));
            btn.setText(getString(R.string.edt_btn));
            etText.setText(note.getText());
        }

        btn.setOnClickListener(this);

        setResult(Activity.RESULT_CANCELED, intent);
    }

    @Override
    public void onClick(View v) {
        if (note == null) {
            EditText etNote = (EditText) findViewById(R.id.etEdit);
            note = new Note(0, etNote.getText().toString());
            intent = new Intent();
            intent.putExtra(NOTE, note);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(MENU_EDT);
            ad.setPositiveButton(BUTTON_OK_TXT, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    note.setText(etText.getText().toString());
                    intent = new Intent();
                    intent.putExtra(NOTE, note);
                    //Log.d("myLogs", note.getText() + " " + String.valueOf(note.getId()));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
            ad.setNegativeButton(BUTTON_CANCEL_TXT, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                }
            });
            ad.show();
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
