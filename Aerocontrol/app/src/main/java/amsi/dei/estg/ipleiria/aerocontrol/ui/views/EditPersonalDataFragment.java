package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentEditPersonalDataBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.DateDisplayFormatUtils;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;

public class EditPersonalDataFragment extends Fragment {

    FragmentEditPersonalDataBinding binding;
    ArrayAdapter<String> adapter;
    Calendar calendar;
    Date birthDate;

    public EditPersonalDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditPersonalDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initializeEts();
        binding.EditPersonalDataEtBirthDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });
        validationsOnStart();

        return view;
    }

    private void initializeEts() {
        comboBoxGenders();
        setBirthdateInEditText();
        binding.EditPersonalDataEtFirstName.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getFirstName());
        binding.EditPersonalDataEtLastName.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getLastName());
        binding.EditPersonalDataEtCountry.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getCountry());
        binding.EditPersonalDataEtCity.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getCity());

        binding.EditPersonalDataEtFirstName.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateFirstName(String.valueOf(s))){
                    binding.EditPersonalDataEtFirstName.disableError();
                } else binding.EditPersonalDataEtFirstName.enableError(UserValidations.firstNameError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setFirstName(String.valueOf(s));
            }
        });

        binding.EditPersonalDataEtLastName.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateLastName(String.valueOf(s))){
                    binding.EditPersonalDataEtLastName.disableError();
                } else binding.EditPersonalDataEtLastName.enableError(UserValidations.lastNameError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setLastName(String.valueOf(s));
            }
        });

        binding.EditPersonalDataEtCountry.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateCountry(String.valueOf(s))){
                    binding.EditPersonalDataEtCountry.disableError();
                } else binding.EditPersonalDataEtCountry.enableError(UserValidations.countryError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setCountry(String.valueOf(s));
            }
        });

        binding.EditPersonalDataEtCity.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateCity(String.valueOf(s))){
                    binding.EditPersonalDataEtCity.disableError();
                } else binding.EditPersonalDataEtCity.enableError(UserValidations.cityError);
                SingletonUser.getInstance(getContext()).getUserToUpdate().setCity(String.valueOf(s));
            }
        });

        binding.EditPersonalDataEtBirthDate.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateBirthdate(String.valueOf(s))){
                    binding.EditPersonalDataEtBirthDate.disableError();
                } else binding.EditPersonalDataEtBirthDate.enableError(UserValidations.birthdateError);
            }
        });
    }

    private void validationsOnStart() {
        if (!UserValidations.validateFirstName(String.valueOf(binding.EditPersonalDataEtFirstName.getText())))
            binding.EditPersonalDataEtFirstName.enableError(UserValidations.firstNameError);

        if (!UserValidations.validateLastName(String.valueOf(binding.EditPersonalDataEtLastName.getText())))
            binding.EditPersonalDataEtLastName.enableError(UserValidations.lastNameError);

        if (!UserValidations.validateGender(String.valueOf(binding.EditPersonalDataACTVGender.getText())))
            binding.EditPersonalDataACTVGender.setError(UserValidations.genderError);

        if (!UserValidations.validateBirthdate(String.valueOf(binding.EditPersonalDataEtBirthDate.getText())))
            binding.EditPersonalDataEtBirthDate.enableError(UserValidations.birthdateError);

        if (!UserValidations.validateCountry(String.valueOf(binding.EditPersonalDataEtCountry.getText())))
            binding.EditPersonalDataEtCountry.enableError(UserValidations.countryError);

        if (!UserValidations.validateCity(String.valueOf(binding.EditPersonalDataEtCity.getText())))
            binding.EditPersonalDataEtCity.enableError(UserValidations.cityError);
    }

    private void comboBoxGenders() {
        adapter = new ArrayAdapter<>(getContext(), R.layout.gender_list_item, User.GENDERS);
        binding.EditPersonalDataACTVGender.setAdapter(adapter);
        binding.EditPersonalDataACTVGender.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getGender(),false);
        binding.EditPersonalDataACTVGender.setOnItemClickListener((parent, view, position, id) -> {
            if (UserValidations.validateGender(User.GENDERS[position]))
                SingletonUser.getInstance(getContext()).getUserToUpdate().setGender(User.GENDERS[position]);
            else binding.EditPersonalDataACTVGender.setError(UserValidations.genderError);
        });
    }

    private void setBirthdateInEditText() {
        binding.EditPersonalDataEtBirthDate.setText(DateDisplayFormatUtils.formatDateToString(SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate(), Validations.VALIDATION_DATE_FORMAT));

        calendar = Calendar.getInstance();
        calendar.setTime(SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate());
    }

    private void showDatePicker(){
        DatePickerDialog picker = new DatePickerDialog(getContext(),R.style.customDatePickerStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SingletonUser.getInstance(getContext()).getUserToUpdate().setBirthdate(cal.getTime());
                        binding.EditPersonalDataEtBirthDate.setText(DateDisplayFormatUtils.formatDateToString(SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate(), Validations.VALIDATION_DATE_FORMAT));
                    }
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        picker.setTitle(getString(R.string.insert_birth_date));

        picker.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBt = picker.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeBt = picker.getButton(DialogInterface.BUTTON_NEGATIVE);

                positiveBt.setTextColor(getResources().getColor(R.color.black_400));
                positiveBt.setText(R.string.confirm);
                negativeBt.setTextColor(getResources().getColor(R.color.black_400));
                negativeBt.setText(R.string.cancel);
            }
        });

        picker.show();
    }
}