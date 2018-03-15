package s.s.testnotes;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static s.s.testnotes.MainActivity.ctx;

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
        MainActivity.db.addRec(etNote.getText().toString());
        Toast toast = Toast.makeText(getApplicationContext(),
                "Заметка добавлена", Toast.LENGTH_SHORT);
        toast.show();
        etNote.setText("");
        getLoaderManager().restartLoader(0, null, (android.app.LoaderManager.LoaderCallbacks<Cursor>) ctx);
        finish();
    }
}
