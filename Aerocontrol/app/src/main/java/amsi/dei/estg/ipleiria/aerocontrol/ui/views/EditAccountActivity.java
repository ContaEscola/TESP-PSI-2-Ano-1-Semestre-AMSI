package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        enableActiveFragmentFocus(binding.EditAccountTvAccessData);

        binding.EditAccountTvContacts.setOnClickListener(v -> {
            replaceFragment(new EditContactsFragment());
            enableActiveFragmentFocus(binding.EditAccountTvContacts);
            disableActiveFragmentFocus(binding.EditAccountTvAccessData,binding.EditAccountTvPersonalData);
        });

        binding.EditAccountTvAccessData.setOnClickListener(v -> {
            replaceFragment(new EditAccessDataFragment());
            enableActiveFragmentFocus(binding.EditAccountTvAccessData);
            disableActiveFragmentFocus(binding.EditAccountTvContacts,binding.EditAccountTvPersonalData);
        });

        binding.EditAccountTvPersonalData.setOnClickListener(v -> {
            replaceFragment(new EditPersonalDataFragment());
            enableActiveFragmentFocus(binding.EditAccountTvPersonalData);
            disableActiveFragmentFocus(binding.EditAccountTvContacts,binding.EditAccountTvAccessData);
        });

        binding.EditAccountBtSave.setOnClickListener(v ->  {
            //TODO VALIDACOES
            AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
            builder.setTitle(R.string.cancel_ticket);
            builder.setMessage("Se deseja realmente apagar o seu bilhete por favor confirme abaixo.");
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                //SingletonUser.getInstance(this).deleteTicketAPI(this, ticket);
            });
            builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});
            builder.show();
        });
    }

    private void disableActiveFragmentFocus(TextView tv1, TextView tv2) {
        tv1.setBackgroundResource(R.drawable.menu_item_background);
        tv1.setTextColor(getResources().getColor(R.color.black_400));
        tv2.setBackgroundResource(R.drawable.menu_item_background);
        tv2.setTextColor(getResources().getColor(R.color.black_400));
    }

    private void enableActiveFragmentFocus(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.blue_400));
        tv.setBackgroundResource(R.drawable.underline_text_layer_list);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.EditAccount_Fragment,fragment).commit();
    }
}