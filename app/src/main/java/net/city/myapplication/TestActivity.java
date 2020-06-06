package net.city.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TestActivity extends AppCompatActivity {

    private HashMap map;
    private EditText addText;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ussd_message);
        map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));

        addText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        textView = findViewById(R.id.message);

        button.setOnClickListener(v -> {
            checknumber();
        });
    }

    private void checknumber() {

        USSDApi ussdApi = USSDController.Companion.getInstance(this);
        ussdApi.callUSSDInvoke(addText.getText().toString(), map, new USSDController.CallbackInvoke() {
            @Override
            public void responseInvoke(String message) {
                // message has the response string data
                String dataToSend = "data";
                // <- send "data" into USSD's input text
                textView.setText(message);
            }

            @Override
            public void over(String message) {
                Toast.makeText(TestActivity.this, message, Toast.LENGTH_LONG).show();
                // message has the response string data from USSD or error
                // response no have input text, NOT SEND ANY DATA
            }
        });

//        ussdApi.send("",(s,d)-> {
//textView.setText("");
//
//        },);

    }
}
