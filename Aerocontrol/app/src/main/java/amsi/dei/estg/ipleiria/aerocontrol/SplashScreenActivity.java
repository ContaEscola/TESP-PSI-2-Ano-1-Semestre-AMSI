package amsi.dei.estg.ipleiria.aerocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, Login.class);
            startActivity(intent);
            finish();
        },3000);

        //Retira a Action Bar provisóriamente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}