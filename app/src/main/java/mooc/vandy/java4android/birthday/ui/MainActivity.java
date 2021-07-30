package mooc.vandy.java4android.birthday.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diamonds.R;

import mooc.vandy.java4android.birthday.logic.Logic;
import mooc.vandy.java4android.birthday.logic.LogicInterface;

/**
 * Main UI for the App.
 */
public class MainActivity
        extends AppCompatActivity
        implements OutputInterface {
    /**
     * String for LOGGING.
     */
    public final static String LOG_TAG =
            MainActivity.class.getCanonicalName();

    /**
     * Logic Instance.
     */
    private LogicInterface mLogic;

    /**
     * EditText that stores the output.
     */
    private TextView mOutput;

    /**
     * EditText that stores the size.
     */
    private EditText mSize;

    /**
     * EditText that stores the count.
     */
    private EditText mCount;

    /**
     * Called when the activity is starting.
     * <p>
     * Similar to 'main' in C/C++/Standalone Java
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // required
        super.onCreate(savedInstanceState);

        // create a new 'Logic' instance.
        mLogic = new Logic(this);

        // setup the UI.
        initializeUI();
    }

    /**
     * This method sets up/gets reference to the UI components
     */
    private void initializeUI() {
        // Set the layout.
        setContentView(R.layout.activity_main);

        // Initialize the views.
        mOutput = findViewById(R.id.outputET);
        mSize = findViewById(R.id.editTextGroupSize);
        mCount = findViewById(R.id.editTextCount);
    }

    /**
     * Called when Button is Pressed.
     *
     * @param buttonPress
     */
    public void buttonPressed(View buttonPress) {
        resetText();
        mLogic.process();
    }

    /**
     * Add @a string to the EditText.
     */
    private void addToEditText(String string) {
        mOutput.setText("" + mOutput.getText() + string);
    }

    /**
     * Get Size value from displayed UI.
     */
    public int getSize() {
        int value = 0;
        final String strValue =
                mSize.getText().toString();

        if (!strValue.isEmpty()) {
            value = Integer.parseInt(strValue);
        }

        return value;
    }

    /**
     * Get Count value from displayed UI.
     */
    public int getCount() {
        int value = 0;
        final String strValue =
                mCount.getText().toString();

        if (!strValue.isEmpty()) {
            value = Integer.parseInt(strValue);
        }

        return value;
    }

    /**
     * Make a Toast from Logic
     */
    @Override
    public void makeAlertToast(String alertText) {
        Toast.makeText(this, alertText, Toast.LENGTH_LONG).show();
    }

    /**
     * Reset the on-screen output (EditText box).
     */
    @Override
    public void resetText() {
        mOutput.setText("");
    }

    /**
     * Prints the string representation of the passed Java Object or primitive type.
     *
     * @param obj a String, int, double, float, boolean or any Java Object.
     */
    @Override
    public void print(Object obj) {
        String text = (obj != null ? obj.toString() : "null");
        addToEditText(text);
        String debug = text.replace("\n", "\\n");
        Log.d(LOG_TAG, "print(" + debug + ")");
    }
}
