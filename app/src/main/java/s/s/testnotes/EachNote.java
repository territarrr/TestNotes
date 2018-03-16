package s.s.testnotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import static s.s.testnotes.Keys.BUTTON_CANCEL_TXT;
import static s.s.testnotes.Keys.BUTTON_OK_TXT;

import static s.s.testnotes.Keys.MENU_DEL;
import static s.s.testnotes.Keys.NOTE;


public class EachNote extends AppCompatActivity implements View.OnClickListener{

    TextView tvTextEachNote;
    Intent intent;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_note);

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
                intent.putExtra(NOTE, note);
                setResult(Activity.RESULT_FIRST_USER, intent);
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3) {
            if(resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
