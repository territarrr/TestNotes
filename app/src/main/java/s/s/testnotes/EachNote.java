package s.s.testnotes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static s.s.testnotes.Keys.ID;
import static s.s.testnotes.Keys.TEXT;

public class EachNote extends AppCompatActivity implements View.OnClickListener{

    TextView tvTextEachNote;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_note);

        intent = getIntent();
        String textNote = intent.getStringExtra(TEXT);

        tvTextEachNote = (TextView) findViewById(R.id.tvTextEachNote);
        tvTextEachNote.setText(textNote);

        Button btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        setResult(Activity.RESULT_CANCELED, intent);

    }

    @Override
    public void onClick(View v) {
        intent.putExtra(ID, intent.getStringExtra(ID));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
