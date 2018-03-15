package hr.fer.android.hw1191226486.homework;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import hr.fer.android.hw1191226486.homework.models.Repo;
import hr.fer.android.hw1191226486.homework.networking.RetrofitService;


import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_PHONE = 100;
    @BindView(R.id.load)
    Button loadBtn;

    @BindView(R.id.pathInput)
    EditText pathInput;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.firstName)
    TextView firstName;

    @BindView(R.id.lastName)
    TextView lastName;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.spouse)
    TextView spouse;

    @BindView(R.id.age)
    TextView age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://m.uploadedit.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService service =
                    retrofit.create(RetrofitService.class);

            @Override
            public void onClick(View view) {

                service.getRepo(pathInput.getText().toString()).enqueue(new Callback<Repo>() {
                    @Override
                    public void onResponse(Call<Repo> call, Response<Repo> response) {
                        Repo repo = response.body();
                        if (repo == null) {
                            Toast.makeText(FormActivity.this, "Nema podataka", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        avatar.setVisibility(View.INVISIBLE);
                        if (repo.getAvatarlocation() != null && repo.getAvatarlocation().length() != 0) {
                            Glide.with(FormActivity.this)
                                    .load(repo.getAvatarlocation())
                                    .into(avatar);
                            avatar.setVisibility(View.VISIBLE);
                        }

                        firstName.setText(repo.getFirstName());
                        lastName.setText(repo.getLastName());
                        email.setText(repo.getEmail());
                        phone.setText(repo.getPhone());
                        spouse.setText(repo.getSpouse());
                        age.setText(String.valueOf(repo.getAge()));
                    }

                    @Override
                    public void onFailure(Call<Repo> call, Throwable t) {
                        Toast.makeText(FormActivity.this, "Pogreska", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phone.getText().toString();
                if (phoneNumber.length() != 0) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));

                    int checkPermission = ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.CALL_PHONE);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                FormActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                REQUEST_CALL_PHONE);
                    }
                    startActivity(intent);
                } 
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();
                if (emailInput.length() != 0) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailInput));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Poruka");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                    startActivity(Intent.createChooser(emailIntent, "Odaberi korisnika"));
                }
            }
        });
    }
}
