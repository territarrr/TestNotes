package s.s.testnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EachNote extends AppCompatActivity {

    TextView tvTextEachNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_note);

        Intent intent = getIntent();
        String textNote = intent.getStringExtra("textNote");
        //Log.d("myLogs", "selectrd pos = " + textNote);
        tvTextEachNote = (TextView) findViewById(R.id.tvTextEachNote);
        tvTextEachNote.setText(textNote);
    }
}
