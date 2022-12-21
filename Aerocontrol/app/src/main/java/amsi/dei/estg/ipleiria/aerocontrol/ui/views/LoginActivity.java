package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;

public class LoginActivity extends AppCompatActivity {

    private TextView etUsername, etPassword;
    private Button btLogin;

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
}