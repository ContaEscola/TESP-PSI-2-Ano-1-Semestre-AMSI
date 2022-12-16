package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FlightSearchFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.BottomNav_Account:
                    replaceFragment(new AccountFragment());
                    break;
                case R.id.BottomNav_Flights:
                    replaceFragment(new FlightSearchFragment());
                    break;
                case R.id.BottomNav_Restaurants:
                    replaceFragment(new RestaurantsFragment());
                    break;
                case R.id.BottomNav_Stores:
                    replaceFragment(new StoresFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ActivityMain_Fragment,fragment).commit();
    }

    public void showRestaurantDetails(final int id){
        Restaurant restaurant = SingletonEnterprises.getInstance(this).getRestaurantById(id);
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivity.RESTAURANT_ID,(int) restaurant.getId());
        startActivity(intent);
    }
}