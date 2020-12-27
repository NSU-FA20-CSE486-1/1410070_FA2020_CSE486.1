package task.cse486.asynctask;

/**
 * Created by Yeahia Muhammad Arif on 28,December,2020
 */
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * The SimpleAsyncTask class extends AsyncTask to perform a very simple
 * background task -- in this case, it just sleeps for a random amount of time.
 */

public class SimpleAsyncTask extends AsyncTask<Void,Integer, String> {

    // The TextView where we will show results
    private WeakReference<TextView> mTextView;

    // Constructor that provides a reference to the TextView from the MainActivity
    SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    /**
     * Runs on the background thread.
     *
     * @param voids No parameters in this use case.
     * @return Returns the string including the amount of time that
     * the background thread slept.
     */
    @Override
    protected String doInBackground(Void... voids) {

        // Generate a random number between 0 and 10.
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running.
        int s = n * 200;

        // Sleep for the random amount of time.
        publishProgress(s);
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result.
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        new CountDownTimer(values[0], 200) {
            public void onFinish() {

            }
            public void onTick(long millisUntilFinished) {
                mTextView.get().setText("sleep remaing: " + millisUntilFinished);
            }
        }.start();

    }

    /**
     * Does something with the result on the UI thread; in this case
     * updates the TextView.
     */
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
