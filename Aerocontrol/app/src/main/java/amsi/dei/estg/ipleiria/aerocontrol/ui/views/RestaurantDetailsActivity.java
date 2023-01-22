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
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewRestaurantItemsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;

public class RestaurantDetailsActivity extends AppCompatActivity {

    public static final String RESTAURANT_ID = "restaurant_id";

    private RecyclerViewRestaurantItemsAdapter adapter;
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
        getRestaurantIdFromIntent();

    }

    private void initialize() {
        recyclerView = findViewById(R.id.RestaurantDetails_Rv_Menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        tvName = findViewById(R.id.RestaurantDetails_Tv_Name);
        tvSchedule = findViewById(R.id.RestaurantDetails_Tv_Schedule);
        tvDescription = findViewById(R.id.RestaurantDetails_Tv_Description);
        tvPhone = findViewById(R.id.RestaurantDetails_Tv_Phone);
        tvWebsite = findViewById(R.id.RestaurantDetails_Tv_Website);
        tvMenuLabel = findViewById(R.id.RestaurantDetails_Tv_Menu);
    }

    private void getRestaurantIdFromIntent() {
        idRestaurant = getIntent().getIntExtra(RESTAURANT_ID,-1);

        if (idRestaurant != -1){
            restaurant = SingletonEnterprises.getInstance(this).getRestaurantById(idRestaurant);
            setRestaurantInView();
        } else Toast.makeText(this, R.string.error_no_restaurant, Toast.LENGTH_SHORT).show();
    }

    private void setRestaurantInView() {
        if (restaurant.getMenu().size() > 0){
            adapter = new RecyclerViewRestaurantItemsAdapter(this, restaurant);
            recyclerView.setAdapter(adapter);

        } else tvMenuLabel.setText("");

        if (!restaurant.getName().equals("null")) tvName.setText(restaurant.getName());

        if(restaurant.getOpenTime().equals(ApiConfig.API_CUSTOM_NULL) || restaurant.getCloseTime().equals(ApiConfig.API_CUSTOM_NULL) || restaurant.getOpenTime().equals(restaurant.getCloseTime()))
            tvSchedule.setText(R.string.open_anytime);
        else
            tvSchedule.setText(restaurant.getOpenTime() + " - " + restaurant.getCloseTime());

        if (!restaurant.getDescription().equals("null")) tvDescription.setText(restaurant.getDescription());
        if (!restaurant.getPhone().equals("null")) tvPhone.setText(restaurant.getPhone());
        if (!restaurant.getWebsite().equals("null")) tvWebsite.setText(restaurant.getWebsite());
    }
}