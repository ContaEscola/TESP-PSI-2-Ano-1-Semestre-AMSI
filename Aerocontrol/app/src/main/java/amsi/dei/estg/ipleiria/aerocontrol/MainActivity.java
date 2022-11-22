package amsi.dei.estg.ipleiria.aerocontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO Fragment Inicial
        //replaceFragment();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.BottomNav_Account:
                    //TODO Fragment Account
                    break;
                case R.id.BottomNav_Flights:
                    //TODO Fragment Flights
                    break;
                case R.id.BottomNav_Restaurants:
                    //TODO Fragment Restaurants
                    break;
                case R.id.BottomNav_Stores:
                    //TODO Fragment Stores
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ActivityMain_Fragment,fragment).commit();
    }
}