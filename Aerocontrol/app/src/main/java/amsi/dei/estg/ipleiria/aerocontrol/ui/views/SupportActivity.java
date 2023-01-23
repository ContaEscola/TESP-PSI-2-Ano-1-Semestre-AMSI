package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;

public class SupportActivity extends AppCompatActivity {

    ActivitySupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.SupportToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.SupportBtSend.setOnClickListener(v -> {
            if (binding.SupportEtEmail.getText().toString().trim().length() > 0 &&
                    binding.SupportEtMessage.getText().toString().trim().length() > 0 &&
                    Validations.validateEmail(binding.SupportEtEmail.getText().toString())){
                sendEmail();
            } else Toast.makeText(this, R.string.email_message_invalid, Toast.LENGTH_SHORT).show();
        });
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aerocontrol.acc@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Suporte Aerocontrol");
        intent.putExtra(Intent.EXTRA_TEXT, binding.SupportEtMessage.getText().toString().trim());
        try {
            startActivity(Intent.createChooser(intent, "Enviar email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SupportActivity.this, R.string.no_apps_to_emails, Toast.LENGTH_SHORT).show();
        }
    }
}