package s.s.testnotes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static s.s.testnotes.Keys.TEXT;

public class AddNote extends AppCompatActivity implements View.OnClickListener{

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText etNote = (EditText) findViewById(R.id.etEdit);
        Intent data = new Intent();
        data.putExtra(TEXT, etNote.getText().toString());
        setResult(Activity.RESULT_OK, data);
        finish();

    }
}
