package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
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

        SingletonUser.getInstance(this).setLoginListener(this);

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
        if(!username.trim().equals("")&&!password.trim().equals(""))
            SingletonUser.getInstance(this).getLoginAPI(username, password, this);
        else Toast.makeText(this, R.string.insert_all_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateLogin(User user, Context context) {
        UserPreferences.getInstance(this).setUser(user);    // Coloca o user no SharedPreferences
        SingletonUser.getInstance(this).setUser(user);      // Coloca o user na Singleton
        SingletonUser.getInstance(this).setLoggedIn(true);  // Dá Set à variável do LoggedIn para true

        Toast.makeText(context, "Dados válidos", Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);    // Dá return à atividade com resultado OK
        finish();
    }
}