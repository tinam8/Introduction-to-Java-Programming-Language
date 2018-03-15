package hr.fer.android.hw1191226486.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculusActivity extends AppCompatActivity {
    @BindView(R.id.calculate)
    Button calculateBtn;

    @BindView(R.id.firstInput)
    EditText firstInput;

    @BindView(R.id.secondInput)
    EditText secondInput;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    public static final String ERROR_KEY = "error";
    public static final String SUCCESS_KEY = "success";
    public static final int RESET_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        ButterKnife.bind(this);

        calculateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                double firstNumber = 0;
                double secondNumber = 0;

                int selectedIndex = radioGroup.getCheckedRadioButtonId();

                String selectedOperation = getOperation(selectedIndex);

                try {
                    firstNumber = Double.parseDouble(firstInput.getText().toString());
                    secondNumber = Double.parseDouble(secondInput.getText().toString());
                } catch (NumberFormatException ex) {
                    diaplayError("Neki od unosa nisu broj.", selectedOperation);
                    return;
                }

                Double result = getResult(selectedOperation, firstNumber, secondNumber);
                if (result == null) {
                    return;
                }

                Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
                Bundle extras = new Bundle();
                String successMsg = String.format("Rezultat operacije %s je %s.",
                        selectedOperation, result.toString());
                extras.putString(SUCCESS_KEY, successMsg);
                intent.putExtras(extras);
                startActivity(intent);
            }


            private void diaplayError(String error, String selectedOperation) {
                String errorMsg = String.format("Prilikom obavljanja operacije %s nad" +
                                " unosima %s i %s došlo je do sljedeće greške: %s.",
                        selectedOperation, firstInput.getText().toString(), secondInput.getText().toString(),
                        error
                );
                Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
                Bundle extras = new Bundle();
                extras.putString(ERROR_KEY, errorMsg);
                intent.putExtras(extras);
                startActivityForResult(intent, RESET_CODE);
            }

            private String getOperation(int selectedIndex) {
                switch (selectedIndex) {
                    case R.id.sum:
                        return "zbrajanja";
                    case R.id.min:
                        return "oduzimanja";
                    case R.id.mul:
                        return "mnozenja";
                    case R.id.div:
                        return "dijeljenja";
                }
                return null;
            }


            private Double getResult(String selectedOperation, double firstNumber, double secondNumber) {
                if (selectedOperation.equals("/") && secondNumber == 0.0) {
                    diaplayError("Dijeljenje nulom nije dozvoljeno.", selectedOperation);
                    return null;
                }

                switch (selectedOperation) {
                    case "zbrajanja":
                        return firstNumber + secondNumber;
                    case "oduzimanja":
                        return firstNumber - secondNumber;
                    case "mnozenja":
                        return firstNumber * secondNumber;
                    case "dijeljenja":
                        return firstNumber / secondNumber;
                }
                return null;
            }
        });
    }
}
