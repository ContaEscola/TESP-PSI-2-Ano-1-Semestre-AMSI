package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Fragments;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentEditPersonalDataBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;

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
        getBirthdate();
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
        adapter = new ArrayAdapter<>(requireContext(), R.layout.gender_list_item, User.GENDERS);
        binding.EditPersonalDataACTVGender.setAdapter(adapter);
        binding.EditPersonalDataACTVGender.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getGender(),false);
        binding.EditPersonalDataACTVGender.setOnItemClickListener((parent, view, position, id) -> {
            if (UserValidations.validateGender(User.GENDERS[position]))
                SingletonUser.getInstance(getContext()).getUserToUpdate().setUsername(User.GENDERS[position]);
            else binding.EditPersonalDataACTVGender.setError(UserValidations.genderError);
        });
    }

    private void getBirthdate() {

        binding.EditPersonalDataEtBirthDate.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate());


        String stringBirthDate = SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            birthDate = format.parse(stringBirthDate);
            calendar = Calendar.getInstance();
            if (birthDate != null) {
                calendar.setTime(birthDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showDatePicker(){
        DatePickerDialog picker = new DatePickerDialog(getContext(),R.style.customDatePickerStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //String date_dd_mm_yyyy = String.format("%02/%02d/%04d",dayOfMonth,(monthOfYear)+1,year);
                        String date_dd_mm_yyyy = getString(R.string.date_format,dayOfMonth,monthOfYear+1,year);
                        SingletonUser.getInstance(getContext()).getUserToUpdate().setBirthdate(date_dd_mm_yyyy);
                        binding.EditPersonalDataEtBirthDate.setText(date_dd_mm_yyyy);
                    }
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        picker.setTitle(getString(R.string.insert_birth_date));

        picker.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button postiveBt = picker.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeBt = picker.getButton(DialogInterface.BUTTON_NEGATIVE);

                postiveBt.setTextColor(getResources().getColor(R.color.black_400));
                postiveBt.setText(R.string.confirm);
                negativeBt.setTextColor(getResources().getColor(R.color.black_400));
                negativeBt.setText(R.string.cancel);
            }
        });

        picker.show();
    }
}