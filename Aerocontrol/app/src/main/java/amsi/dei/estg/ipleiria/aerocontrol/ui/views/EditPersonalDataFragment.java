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
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setFirstName(String.valueOf(s));
                } else binding.EditPersonalDataEtFirstName.enableError(UserValidations.firstNameError);
            }
        });

        binding.EditPersonalDataEtLastName.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateLastName(String.valueOf(s))){
                    binding.EditPersonalDataEtLastName.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setLastName(String.valueOf(s));
                } else binding.EditPersonalDataEtLastName.enableError(UserValidations.lastNameError);
            }
        });

        binding.EditPersonalDataEtCountry.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateCountry(String.valueOf(s))){
                    binding.EditPersonalDataEtCountry.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setCountry(String.valueOf(s));
                } else binding.EditPersonalDataEtCountry.enableError(UserValidations.countryError);
            }
        });

        binding.EditPersonalDataEtCity.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateCity(String.valueOf(s))){
                    binding.EditPersonalDataEtCity.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setCity(String.valueOf(s));
                } else binding.EditPersonalDataEtCity.enableError(UserValidations.cityError);
            }
        });

        binding.EditPersonalDataEtBirthDate.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (UserValidations.validateBirthdate(String.valueOf(s))){
                    binding.EditPersonalDataEtBirthDate.disableError();
                    SingletonUser.getInstance(getContext()).getUserToUpdate().setBirthdate(String.valueOf(s));
                } else binding.EditPersonalDataEtBirthDate.enableError(UserValidations.birthdateError);
            }
        });
    }

    private void comboBoxGenders() {
        adapter = new ArrayAdapter<>(requireContext(), R.layout.gender_list_item, User.GENDERS);
        binding.EditPersonalDataACTVGender.setAdapter(adapter);
        binding.EditPersonalDataACTVGender.setText(SingletonUser.getInstance(getContext()).getUserToUpdate().getGender(),false);
        binding.EditPersonalDataACTVGender.setOnItemClickListener((parent, view, position, id) -> {
            if (UserValidations.validateGender(User.GENDERS[position]))
                SingletonUser.getInstance(getContext()).getUserToUpdate().setUsername(User.GENDERS[position]);
        });
    }

    private void getBirthdate() {
        String stringBirthDate = SingletonUser.getInstance(getContext()).getUserToUpdate().getBirthdate();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthDate = format.parse(stringBirthDate);
            calendar = Calendar.getInstance();
            if (birthDate != null) {
                calendar.setTime(birthDate);
            }
            // +1 no mês um porque o calendar vai de 0 a 11
            String newBirthdateFormat = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);
            binding.EditPersonalDataEtBirthDate.setText(newBirthdateFormat);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showDatePicker(){
        DatePickerDialog picker = new DatePickerDialog(getContext(),R.style.customDatePickerStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SingletonUser.getInstance(getContext()).getUserToUpdate().setBirthdate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        binding.EditPersonalDataEtBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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