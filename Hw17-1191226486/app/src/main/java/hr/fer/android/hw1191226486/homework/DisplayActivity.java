package hr.fer.android.hw1191226486.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayActivity extends AppCompatActivity {
    @BindView(R.id.okBtn)
    Button okBtn;

    @BindView(R.id.sendEmail)
    Button sendEmailBtn;

    @BindView(R.id.status)
    TextView statusLabel;

    @BindView(R.id.message)
    TextView messageText;

    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        if (extras.containsKey(CalculusActivity.ERROR_KEY)) {
            message = extras.getString(CalculusActivity.ERROR_KEY);
            statusLabel.setText("Doslo je do pogreske.");
        } else if (extras.containsKey(CalculusActivity.SUCCESS_KEY)) {
            message = extras.getString(CalculusActivity.SUCCESS_KEY);
        }

        messageText.setText(message);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, CalculusActivity.class);
                startActivity(intent);
            }
        });

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ana@baotic.org"});
                i.putExtra(Intent.EXTRA_SUBJECT, " <1191226486>: dz report");
                i.putExtra(Intent.EXTRA_TEXT   , message);
                try {
                    startActivity(Intent.createChooser(i, "Izabri klijenta za slanje maila: "));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DisplayActivity.this, "Nema klijenata za slanje maila.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
