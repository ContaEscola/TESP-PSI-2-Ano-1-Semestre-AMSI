package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityEditAccountBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.UpdateUserListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;

public class EditAccountActivity extends AppCompatActivity implements UpdateUserListener {

    private ActivityEditAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.EditAccountToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setUpdateUserListener(this);
        SingletonUser.getInstance(this).setUserToUpdate(SingletonUser.getInstance(this).getUser());

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

        binding.EditAccountBtSave.setOnClickListener(v ->  saveData());
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

    private void saveData() {
        HashMap<String, ArrayList<String>> errors = UserValidations.validateUser(SingletonUser.getInstance(this).getUserToUpdate());
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
        if (errors != null){
            builder.setTitle(R.string.errors_found);
            StringBuilder errorsMessage = new StringBuilder(getString(R.string.solve_errors));
            if (errors.get("Contacts") != null){
                errorsMessage.append("\n" + "Contactos:");
                for (String erro: errors.get("Contacts")) {
                    errorsMessage.append("\n \t- ").append(erro);
                }
            }
            if (errors.get("AccessData") != null){
                errorsMessage.append("\n" + "Dados de Acesso:");
                for (String erro: errors.get("AccessData")) {
                    errorsMessage.append("\n \t- ").append(erro);
                }
            }
            if (errors.get("PersonalData") != null){
                errorsMessage.append("\n" + "Dados Pessoais:");
                for (String erro: errors.get("PersonalData")) {
                    errorsMessage.append("\n \t- ").append(erro);
                }
            }
            builder.setMessage(errorsMessage);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {});
        }
        else {
            builder.setTitle(R.string.save_data);
            builder.setMessage(R.string.verify_password);
            EditText password = new EditText(this);
            password.setHint(getString(R.string.password));
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            builder.setView(password);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                SingletonUser.getInstance(this).updateUserAPI(this,password.getText().toString());
            });
            builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});

        }
        builder.show();
    }

    @Override
    public void onUpdateUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorUpdate(String message) {
        if(message.isEmpty()) {
            Toast.makeText(this, R.string.common_error, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.errors_found);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {});
        builder.show();
    }
}