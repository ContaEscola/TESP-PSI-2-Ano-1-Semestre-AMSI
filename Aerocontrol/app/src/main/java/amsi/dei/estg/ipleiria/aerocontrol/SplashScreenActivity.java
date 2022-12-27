package amsi.dei.estg.ipleiria.aerocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import amsi.dei.estg.ipleiria.aerocontrol.ui.views.LoginActivity;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.MainActivity;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.PaymentActivity;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.SupportTicketActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },3000);
    }
}