package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentEditAccessDataBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;

public class EditAccessDataFragment extends Fragment {

    FragmentEditAccessDataBinding binding;

    public EditAccessDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditAccessDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.EditAccessDataEtUsername.setText(SingletonUser.getInstance(this.getContext()).getUserToUpdate().getUsername());

        binding.EditAccessDataEtUsername.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateUsername(String.valueOf(s))){
                    binding.EditAccessDataEtUsername.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setUsername(String.valueOf(s));
                } else binding.EditAccessDataEtUsername.enableError(UserValidations.usernameError);
            }
        });
        binding.EditAccessDataEtPassword.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() == 0) return;
                if (UserValidations.validatePassword(String.valueOf(s))){
                    binding.EditAccessDataEtPassword.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setPassword(String.valueOf(s));
                } else binding.EditAccessDataEtPassword.enableError(UserValidations.passwordError);
            }
        });
        return view;
    }
}