package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.RestaurantItemsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;

public class RestaurantDetailsActivity extends AppCompatActivity {

    public static final String RESTAURANT_ID = "restaurant_id";

    private RestaurantItemsAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvName, tvSchedule, tvDescription, tvPhone, tvWebsite, tvMenuLabel;

    private Restaurant restaurant;
    private int idRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.RestaurantDetails_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        getRestaurantId();

    }

    private void initialize() {
        recyclerView = findViewById(R.id.RestaurantDetails_Rv_Menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvName = findViewById(R.id.RestaurantDetails_Tv_Name);
        tvSchedule = findViewById(R.id.RestaurantDetails_Tv_Schedule);
        tvDescription = findViewById(R.id.RestaurantDetails_Tv_Description);
        tvPhone = findViewById(R.id.RestaurantDetails_Tv_Phone);
        tvWebsite = findViewById(R.id.RestaurantDetails_Tv_Website);
        tvMenuLabel = findViewById(R.id.RestaurantDetails_Tv_Menu);
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
            adapter = new RestaurantItemsAdapter(this, restaurant.getMenu());
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else tvMenuLabel.setText("");

        if (!restaurant.getName().equals("null")) tvName.setText(restaurant.getName());
        if (!restaurant.getOpenTime().equals("null") && !restaurant.getCloseTime().equals("null")) tvSchedule.setText(restaurant.getOpenTime() + " - " + restaurant.getCloseTime());
        if (!restaurant.getDescription().equals("null")) tvDescription.setText(restaurant.getDescription());
        if (!restaurant.getPhone().equals("null")) tvPhone.setText(restaurant.getPhone());
        if (!restaurant.getWebsite().equals("null")) tvWebsite.setText(restaurant.getWebsite());
    }
}