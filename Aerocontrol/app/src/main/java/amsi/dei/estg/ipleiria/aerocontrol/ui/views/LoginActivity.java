package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.nio.charset.StandardCharsets;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private TextView etUsername, etPassword;
    private Button btLogin;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.Login_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();

        btLogin.setOnClickListener(view -> login());
    }

    private void initialize() {
        etUsername = findViewById(R.id.Login_Et_Username);
        etPassword = findViewById(R.id.Login_Et_Password);
        btLogin = findViewById(R.id.Login_Bt_Login);
    }

    private void login(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        SingletonUser.getInstance(this).getLoginAPI(username, password, this);
    }

    @Override
    public void onValidateLogin(String token, String username, Context context) {
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("username", username);
        editor.putString("token", token);
        editor.apply();

        Toast.makeText(context, "Dados válidos", Toast.LENGTH_SHORT).show();

        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
    }
}