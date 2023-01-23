package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityLoginBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.LoginToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setLoginListener(this);

        binding.LoginBtLogin.setOnClickListener(view -> login());

        binding.LoginTvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(){
        String username = binding.LoginEtUsername.getText().toString();
        String password = binding.LoginEtPassword.getText().toString();
        if(!username.trim().equals("")&&!password.trim().equals(""))
            SingletonUser.getInstance(this).getLoginAPI(username.trim(), password.trim(), this);
        else Toast.makeText(this, R.string.insert_all_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateLogin(User user, Context context) {
        UserPreferences.getInstance(this).setUser(user);    // Coloca o user no SharedPreferences


        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);    // Dá return à atividade com resultado OK
        finish();
    }
}