package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityStoreDetailsBinding;

public class StoreDetailsActivity extends AppCompatActivity {

    public static final String STORE_ID = "store_id";

    private ActivityStoreDetailsBinding binding;

    private Store store;
    private int idStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.StoreDetailsToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getStoreIdFromIntent();
    }


    private void getStoreIdFromIntent() {
        idStore = getIntent().getIntExtra(STORE_ID,-1);

        if (idStore != -1){
            store = SingletonEnterprises.getInstance(this).getStoreById(idStore);
            setStoreInView();
        } else Toast.makeText(this, R.string.error_no_loja, Toast.LENGTH_SHORT).show();
    }

    private void setStoreInView() {
        if (!store.getName().equals("null")) binding.StoreDetailsTvName.setText(store.getName());
        if(store.getOpenTime().equals(ApiConfig.API_CUSTOM_NULL) || store.getCloseTime().equals(ApiConfig.API_CUSTOM_NULL) || store.getOpenTime().equals(store.getCloseTime()))
            binding.StoreDetailsTvSchedule.setText(R.string.open_anytime);
        else
            binding.StoreDetailsTvSchedule.setText(store.getOpenTime() + " - " + store.getCloseTime());

        if (!store.getDescription().equals("null")) binding.StoreDetailsTvDescription.setText(store.getDescription());
        if (!store.getPhone().equals("null")) binding.StoreDetailsTvPhone.setText(store.getPhone());
        if (!store.getWebsite().equals("null")) binding.StoreDetailsTvWebsite.setText(store.getWebsite());
    }
}