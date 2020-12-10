package task.cse486.datetimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Spinner day_spinner;
    Spinner month_spinner;
    Spinner year_spinner;
    Spinner hour_spinner;
    Spinner minute_spinner;
    Spinner time_format_spinner;

    String day;
    String month;
    String year;
    String hour;
    String minute;
    String hourFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView(){
        day_spinner = findViewById(R.id.day_spinner);
        month_spinner = findViewById(R.id.month_spinner);
        year_spinner = findViewById(R.id.year_spinner);
        hour_spinner = findViewById(R.id.spinner_hour);
        minute_spinner = findViewById(R.id.spinner_minute);
        time_format_spinner = findViewById(R.id.spinner_time_format);
        setupDaySpinner();
        setupMonthSpinner();
        setupYearSpinner();
        setupHourSpinner();
        setupMinuteSpinner();
        setupTimeFormatSpinner();
    }

    private void setupDaySpinner(){
        if (day_spinner != null) {
            day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    day = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (day_spinner != null) {
            day_spinner.setAdapter(adapter);
        }
    }

    private void setupMonthSpinner(){
        if (month_spinner != null) {
            month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    month = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (month_spinner != null) {
            month_spinner.setAdapter(adapter);
        }
    }

    private void setupYearSpinner(){
        if (year_spinner != null) {
            year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    year = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (year_spinner != null) {
            year_spinner.setAdapter(adapter);
        }
    }

    private void setupHourSpinner(){
        if (hour_spinner != null) {
            hour_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    hour = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hour_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (hour_spinner != null) {
            hour_spinner.setAdapter(adapter);
        }
    }

    private void setupMinuteSpinner(){
        if (minute_spinner != null) {
            minute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    minute = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.minute_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (minute_spinner != null) {
            minute_spinner.setAdapter(adapter);
        }
    }

    private void setupTimeFormatSpinner(){
        if (time_format_spinner != null) {
            time_format_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String spinnerLabel = adapterView.getItemAtPosition(i).toString();
                    hourFormat = spinnerLabel;
                    displayToast(spinnerLabel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_format_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (time_format_spinner != null) {
            time_format_spinner.setAdapter(adapter);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openDetailActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDetailActivity(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hour);
        stringBuilder.append(":");
        stringBuilder.append(minute);
        stringBuilder.append(" ");
        stringBuilder.append(hourFormat);
        stringBuilder.append("   ");
        stringBuilder.append(day);
        stringBuilder.append("/");
        stringBuilder.append(month);
        stringBuilder.append("/");
        stringBuilder.append(year);
        stringBuilder.append("\n");

        Intent intent = new Intent(getApplicationContext(), SelectedTimeActivity.class);
        intent.putExtra("time", stringBuilder.toString());
        startActivity(intent);


    }
}