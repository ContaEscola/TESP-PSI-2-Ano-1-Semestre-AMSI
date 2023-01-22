package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.RestaurantItemsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityRestaurantDetailsBinding;

public class RestaurantDetailsActivity extends AppCompatActivity {

    public static final String RESTAURANT_ID = "restaurant_id";

    private ActivityRestaurantDetailsBinding binding;

    private RestaurantItemsAdapter adapter;

    private Restaurant restaurant;
    private int idRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.RestaurantDetailsToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        getRestaurantId();

    }

    private void initialize() {
        binding.RestaurantDetailsRvMenu.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getRestaurantId() {
        idRestaurant = getIntent().getIntExtra(RESTAURANT_ID,-1);

        if (idRestaurant != -1){
            restaurant = SingletonEnterprises.getInstance(this).getRestaurantById(idRestaurant);
            restaurantDetails();
        } else Toast.makeText(this, R.string.error_no_restaurant, Toast.LENGTH_SHORT).show();
    }

    private void restaurantDetails() {
        if (restaurant.getMenu().size() > 0){
            adapter = new RestaurantItemsAdapter(this, restaurant.getMenu(), restaurant);
            binding.RestaurantDetailsRvMenu.setAdapter(adapter);
            binding.RestaurantDetailsRvMenu.setItemAnimator(new DefaultItemAnimator());
        } else binding.RestaurantDetailsTvMenu.setText("");

        if (!restaurant.getName().equals("null")) binding.RestaurantDetailsTvName.setText(restaurant.getName());
        if (!restaurant.getOpenTime().equals("null") && !restaurant.getCloseTime().equals("null")) binding.RestaurantDetailsTvSchedule.setText(restaurant.getOpenTime() + " - " + restaurant.getCloseTime());
        if (!restaurant.getDescription().equals("null")) binding.RestaurantDetailsTvDescription.setText(restaurant.getDescription());
        if (!restaurant.getPhone().equals("null")) binding.RestaurantDetailsTvPhone.setText(restaurant.getPhone());
        if (!restaurant.getWebsite().equals("null")) binding.RestaurantDetailsTvWebsite.setText(restaurant.getWebsite());
    }
}