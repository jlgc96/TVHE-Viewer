package jlgc.tvheviewer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    //Variables
    private EditText usernameBox;
    private EditText passwordBox;
    private EditText serverBox;
    private Switch rememberSW;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Asign variables
        usernameBox = (EditText) findViewById(R.id.usernameBox);
        passwordBox = (EditText) findViewById(R.id.pwdBox);
        serverBox = (EditText) findViewById(R.id.serverBox);
        rememberSW = (Switch) findViewById(R.id.rememberSwitch);
        loginButton = (Button) findViewById(R.id.loginButton);

        // LoginButton Action
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login Data
                String username = usernameBox.getText().toString();
                String pwd = passwordBox.getText().toString();
                String server = serverBox.getText().toString();
                Boolean remember = rememberSW.isChecked();

                //Check information
                new CheckAndDownloadData().execute(server,username,pwd);
            }
        });
    }

    //Check login data and download playlist
    protected class CheckAndDownloadData extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            int statusCode = 400;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                //Handle Authentication
                final String loginData=strings[1]+":"+strings[2];
                final String basicAuth = "Basic " + Base64.encodeToString(loginData.getBytes(), Base64.NO_WRAP);
                connection.setRequestProperty ("Authorization", basicAuth);

                //Make Connection and return status
                connection.connect();
                statusCode = connection.getResponseCode();
                System.out.println(statusCode);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return statusCode;
        }
    }
}
