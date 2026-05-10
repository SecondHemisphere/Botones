package ec.edu.ug.botonesbuenaspracticas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;

/**
 * MainActivity demuestra el uso correcto de Button, ToggleButton e ImageButton.
 * <p>
 * Buenas prácticas aplicadas:
 * 1. Los textos visibles están en strings.xml.
 * 2. Los colores y dimensiones están separados en archivos de recursos.
 * 3. Los ImageButton tienen contentDescription para accesibilidad.
 * 4. Los eventos se registran desde Java con setOnClickListener.
 * 5. El estado del ToggleButton y el contador se guardan con SharedPreferences.
 */
public class MainActivity extends Activity {

    private static final String PREFS_NAME = "button_demo_preferences";
    private static final String KEY_CLASS_MODE_ENABLED = "class_mode_enabled";
    private static final String KEY_PARTICIPATION_COUNTER = "participation_counter";
    private static final String KEY_LAST_DATE = "last_participation_date";
    private static final int MAX_PARTICIPATIONS = 10;

    private EditText etStudentName;
    private TextView tvStatus;
    private TextView tvCounter;
    private TextView tvObjectiveDisplay;
    private TextView tvLastDate;
    private Switch switchClassMode;
    private SharedPreferences preferences;
    private int participationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        bindViews();
        loadSavedState();
        configureEvents();
        refreshCounterText();
    }

    /**
     * Centraliza la búsqueda de controles para mantener onCreate ordenado.
     */
    private void bindViews() {
        etStudentName = findViewById(R.id.etStudentName);
        tvStatus = findViewById(R.id.tvStatus);
        tvCounter = findViewById(R.id.tvCounter);
        tvLastDate = findViewById(R.id.tvLastDate); //campo para mostrar la fecha de ultima participacion
        tvObjectiveDisplay = findViewById(R.id.tvObjectiveDisplay); //campo para mostrar el objetivo
        switchClassMode = findViewById(R.id.switchClassMode); //switch para cambiar entre modos de clase

        ImageButton ibAddParticipation = findViewById(R.id.ibAddParticipation);
        ImageButton ibResetCounter = findViewById(R.id.ibResetCounter);
        Button btnGreet = findViewById(R.id.btnGreet);
        Button btnMostrarObjetivo = findViewById(R.id.btnMostrarObjetivo);  //boton motrar objetivo agregado

        ibAddParticipation.setOnClickListener(this::onAddParticipationClicked);
        ibResetCounter.setOnClickListener(this::onResetCounterClicked);
        btnGreet.setOnClickListener(this::onGreetClicked);
        btnMostrarObjetivo.setOnClickListener(this::onShowObjectiveClicked); //evento del boton mostrar objetivo agregado
    }

    /**
     * Recupera estado simple guardado para que la pantalla no se reinicie siempre en cero.
     */
    private void loadSavedState() {
        boolean classModeEnabled = preferences.getBoolean(KEY_CLASS_MODE_ENABLED, false);
        participationCounter = preferences.getInt(KEY_PARTICIPATION_COUNTER, 0);

        switchClassMode.setChecked(classModeEnabled);
        switchClassMode.setText(classModeEnabled ? getString(R.string.toggle_on) : getString(R.string.toggle_off));

        String savedDate = preferences.getString(KEY_LAST_DATE, getString(R.string.last_participation_never));
        tvLastDate.setText(savedDate);
    }

    /**
     * Configura el evento propio del Switch.
     */
    private void configureEvents() {
        switchClassMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean(KEY_CLASS_MODE_ENABLED, isChecked).apply();
            switchClassMode.setText(isChecked ? getString(R.string.toggle_on) : getString(R.string.toggle_off));
            int messageId = isChecked ? R.string.status_toggle_enabled : R.string.status_toggle_disabled;
            showStatus(getString(messageId));
        });
    }

    private void onGreetClicked(View view) {
        hideKeyboard();

        String studentName = etStudentName.getText().toString().trim();
        if (studentName.isEmpty()) {
            showStatus(getString(R.string.status_empty_name));
            etStudentName.requestFocus();
            return;
        }

        if (studentName.length() < 3) {
            showStatus(getString(R.string.status_error_name_short));
            etStudentName.requestFocus();
            return;
        }

        showStatus(getString(R.string.status_greeting, studentName));
    }

    //metodo creado para el boton mostrar objetivo
    private void onShowObjectiveClicked(View view) {
        hideKeyboard();
        tvObjectiveDisplay.setText(getString(R.string.objective_text));
        showStatus(getString(R.string.status_objective_loaded));
    }

    private void onAddParticipationClicked(View view) {

        if (participationCounter >= MAX_PARTICIPATIONS) {
            showStatus(getString(R.string.status_counter_limit));
            return;
        }
        participationCounter++;

        String currentDateTime = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        String formattedDate = getString(R.string.last_participation_label, currentDateTime);

        saveCounter(formattedDate);

        refreshCounterText();
        tvLastDate.setText(formattedDate);
        showStatus(getString(R.string.status_counter_plus, participationCounter));
    }

    private void onResetCounterClicked(View view) {
        participationCounter = 0;
        String never = getString(R.string.last_participation_never);
        saveCounter(never);
        refreshCounterText();
        tvLastDate.setText(never);
        showStatus(getString(R.string.status_counter_reset));
    }

    private void saveCounter(String date) {
        preferences.edit()
                .putInt(KEY_PARTICIPATION_COUNTER, participationCounter)
                .putString(KEY_LAST_DATE, date)
                .apply();
    }

    private void refreshCounterText() {
        tvCounter.setText(getString(R.string.counter_label, participationCounter));
    }

    private void showStatus(String message) {
        tvStatus.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            return;
        }

        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
}