package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityResetPasswordBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.ResetPasswordListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordListener {

    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.ResetPassToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setResetPasswordListener(this);

        binding.ResetPassBtReset.setOnClickListener(v -> {
            if (binding.ResetPassEtEmail.getText().length() > 0 && Validations.validateEmail(String.valueOf(binding.ResetPassEtEmail.getText()))){
                SingletonUser.getInstance(this).resetPasswordAPI(String.valueOf(binding.ResetPassEtEmail.getText()).trim(),this);
            } else
                Toast.makeText(this, R.string.email_error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onEmailSent(String message) {
        Toast.makeText(this, R.string.signup_verification, Toast.LENGTH_SHORT).show();
        finish();
    }
}