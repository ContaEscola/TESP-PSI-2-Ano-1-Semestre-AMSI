package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityEditAccountBinding;

public class EditAccountActivity extends AppCompatActivity {

    ActivityEditAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.EditAccount_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        replaceFragment(new EditAccessDataFragment());

        //TODO binding.EditAccountTvContacts.setOnClickListener(v -> replaceFragment(new EditContactsFragment()));

        binding.EditAccountTvAccessData.setOnClickListener(v -> replaceFragment(new EditAccessDataFragment()));

        //TODO binding.EditAccountTvPersonalData.setOnClickListener(v -> replaceFragment(new EditPersonalDataFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.EditAccount_Fragment,fragment).commit();
    }


}