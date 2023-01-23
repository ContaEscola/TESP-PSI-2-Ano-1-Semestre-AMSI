package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentEditContactsBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;

public class EditContactsFragment extends Fragment {

    FragmentEditContactsBinding binding;

    public EditContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditContactsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.EditContactsEtEmail.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getEmail());
        binding.EditContactsEtPhone.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getPhone());
        binding.EditContactsEtPhoneCode.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getPhoneCountryCode());

        binding.EditContactsEtEmail.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateEmail(String.valueOf(s))){
                    binding.EditContactsEtEmail.disableError();
                } else binding.EditContactsEtEmail.enableError(UserValidations.emailError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setEmail(String.valueOf(s));
            }
        });
        binding.EditContactsEtPhone.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validatePhone(String.valueOf(s))){
                    binding.EditContactsEtPhone.disableError();
                } else binding.EditContactsEtPhone.enableError(UserValidations.phoneError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setPhone(String.valueOf(s));
            }
        });
        binding.EditContactsEtPhoneCode.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validatePhoneCode(String.valueOf(s))){
                    binding.EditContactsEtPhoneCode.disableError();
                } else binding.EditContactsEtPhoneCode.enableError(UserValidations.phoneCodeError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setPhoneCountryCode(String.valueOf(s));
            }
        });


        validationsOnStart();

        return view;
    }

    private void validationsOnStart() {
        if (!UserValidations.validateEmail(String.valueOf(binding.EditContactsEtEmail.getText())))
            binding.EditContactsEtEmail.enableError(UserValidations.emailError);

        if (!UserValidations.validatePhone(String.valueOf(binding.EditContactsEtPhone.getText())))
            binding.EditContactsEtPhone.enableError(UserValidations.phoneError);

        if (!UserValidations.validatePhoneCode(String.valueOf(binding.EditContactsEtPhoneCode.getText())))
            binding.EditContactsEtPhoneCode.enableError(UserValidations.phoneCodeError);

    }
}