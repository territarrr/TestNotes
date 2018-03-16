package s.s.testnotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static s.s.testnotes.Keys.BTN_EDIT;
import static s.s.testnotes.Keys.BUTTON_CANCEL_TXT;
import static s.s.testnotes.Keys.BUTTON_OK_TXT;
import static s.s.testnotes.Keys.ID;
import static s.s.testnotes.Keys.TEXT;
import static s.s.testnotes.Keys.TV_TXT_EDIT;

/**
 * Created by Sovochka on 16.03.2018.
 */

public class EditNote extends AppCompatActivity implements View.OnClickListener{
    EditText etText;
    Intent intent;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        intent = getIntent();

        btn = (Button) findViewById(R.id.btnAdd);
        btn.setText(BTN_EDIT);
        btn.setOnClickListener(this);

        TextView tvText = (TextView) findViewById(R.id.tvText);
        tvText.setText(TV_TXT_EDIT);

        etText = findViewById(R.id.etEdit);
        etText.setText(intent.getStringExtra(TEXT));

        setResult(Activity.RESULT_CANCELED, intent);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(BTN_EDIT);
        ad.setPositiveButton(BUTTON_OK_TXT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.putExtra(ID, intent.getStringExtra(ID));
                intent.putExtra(TEXT, etText.getText().toString());
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
