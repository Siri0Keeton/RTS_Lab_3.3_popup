package com.example.lab3ml;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Дополнительное задание: вывести время выполнения через pop-up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onBotton(View v) {
        EditText editNumber = (EditText) findViewById(R.id.number);
        TextView resultView = (TextView) findViewById(R.id.resView);
        int inputInt;
        try {
            inputInt = Integer.parseInt(editNumber.getText().toString());
        } catch (NumberFormatException e) {
            resultView.setText("Too big number");
            return;
        }
        long startTime = System.nanoTime();
        String res = fermat_factor(inputInt);
        long duration = System.nanoTime() - startTime;

        resultView.setText(res);
        popup(v, "Execution time: " + String.valueOf(duration / 1e6) + " ms");

    }

    public void popup(View view, String message) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        ((TextView) popupWindow.getContentView().findViewById(R.id.popupMessage)).setText(message);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    public static String fermat_factor(int N) {
        if (N % 2 != 1) {
            return "Wrong number";
        }

        // Округлённое значение корня N
        double a = Math.ceil(Math.sqrt(N));

        // Разница между N и ближайшим бОльшим квадратом
        double b2 = a * a - N;

        int iter = 1;
//        System.out.println(String.format("Iteration %d, a = %f, b2 = %f", iter, a, b2));

        while (Math.ceil(Math.sqrt(b2)) != Math.floor(Math.sqrt(b2))) {
            a += 1;
            b2 = a * a - N;
            iter++;
//            System.out.println(String.format("Iteration %d, a = %f, b2 = %f", iter, a, b2));
        }
        int p = (int) (a - Math.sqrt(b2));
        int q = (int) (a + Math.sqrt(b2));

        System.out.println(String.format("Final result is: p = %d, q = %d\n", p, q));
        return "p = " + p + "\n" + "q = " + q;

    }
}

