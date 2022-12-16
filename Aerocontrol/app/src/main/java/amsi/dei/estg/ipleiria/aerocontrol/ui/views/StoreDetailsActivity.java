package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;

public class StoreDetailsActivity extends AppCompatActivity {

    public static final String STORE_ID = "store_id";

    private TextView tvName, tvSchedule, tvDescription, tvPhone, tvWebsite, tvMenuLabel;

    private Store store;
    private int idStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.StoreDetails_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        getRestaurantId();
    }

    private void initialize() {
        tvName = findViewById(R.id.RestaurantDetails_Tv_Name);
        tvSchedule = findViewById(R.id.RestaurantDetails_Tv_Schedule);
        tvDescription = findViewById(R.id.RestaurantDetails_Tv_Description);
        tvPhone = findViewById(R.id.RestaurantDetails_Tv_Phone);
        tvWebsite = findViewById(R.id.RestaurantDetails_Tv_Website);
        tvMenuLabel = findViewById(R.id.RestaurantDetails_Tv_Menu);
    }

    private void getRestaurantId() {
        idStore = getIntent().getIntExtra(STORE_ID,-1);

        if (idStore != -1){
            store = SingletonEnterprises.getInstance(this).getStoreById(idStore);
            storeDetails();
        } else Toast.makeText(this, R.string.error_na_loja, Toast.LENGTH_SHORT).show();
    }

    private void storeDetails() {
        if (!store.getName().equals("null")) tvName.setText(store.getName());
        if (!store.getOpenTime().equals("null") && !store.getCloseTime().equals("null")) tvSchedule.setText(store.getOpenTime() + " - " + store.getCloseTime());
        if (!store.getDescription().equals("null")) tvDescription.setText(store.getDescription());
        if (!store.getPhone().equals("null")) tvPhone.setText(store.getPhone());
        if (!store.getWebsite().equals("null")) tvWebsite.setText(store.getWebsite());
    }
}