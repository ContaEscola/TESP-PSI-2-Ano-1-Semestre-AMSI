package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityRegisterBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SignupListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.DateDisplayFormatUtils;
import amsi.dei.estg.ipleiria.aerocontrol.utils.UserValidations;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;


public class RegisterActivity extends AppCompatActivity implements SignupListener {

    ActivityRegisterBinding binding;
    Calendar calendar;
    Date birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.RegisterToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setSignupListener(this);

        startCalendar();
        initializeCbGenders();
        setTextChangedListeners();

        binding.RegisterEtBirth.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });

        binding.RegisterBtSignup.setOnClickListener(v -> {
            if (validate()) {
                signup();
            } else Toast.makeText(this, R.string.fill_fields_without_errors, Toast.LENGTH_SHORT).show();
        });

    }

    private void startCalendar(){
        birthDate = new Date();

        calendar = new GregorianCalendar();
        calendar.setTime(birthDate);
    }

    private void showDatePicker(){
        DatePickerDialog picker = new DatePickerDialog(this, R.style.customDatePickerStyle,
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) -> {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = cal.getTime();
                birthDate = date;
                binding.RegisterEtBirth.setText(DateDisplayFormatUtils.formatDateToString(date, Validations.VALIDATION_DATE_FORMAT));
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        picker.setTitle(getString(R.string.insert_birth_date));

        picker.setOnShowListener(dialog -> {
            Button positiveBt = picker.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeBt = picker.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveBt.setTextColor(getResources().getColor(R.color.black_400));
            positiveBt.setText(R.string.confirm);
            negativeBt.setTextColor(getResources().getColor(R.color.black_400));
            negativeBt.setText(R.string.cancel);
        });

        picker.show();
    }

    private void initializeCbGenders() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.gender_list_item, User.GENDERS);
        binding.RegisterACTVGender.setAdapter(adapter);
        binding.RegisterACTVGender.setText(User.GENDERS[0],false);
        binding.RegisterACTVGender.setOnItemClickListener((parent, view, position, id) -> {
            if (!UserValidations.validateGender(User.GENDERS[position]))
                binding.RegisterACTVGender.setError(UserValidations.genderError);
        });
    }

    private void setTextChangedListeners(){
        binding.RegisterEtUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                if (UserValidations.validateUsername(String.valueOf(binding.RegisterEtUsername.getText())))
                    binding.RegisterEtUsername.disableError();
                else binding.RegisterEtUsername.enableError(UserValidations.usernameError);
            }
        });

        binding.RegisterEtPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validatePassword(String.valueOf(binding.RegisterEtPassword.getText())))
                    binding.RegisterEtPassword.disableError();
                else binding.RegisterEtPassword.enableError(UserValidations.passwordError);
            }
        });

        binding.RegisterEtFirstName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateFirstName(String.valueOf(binding.RegisterEtFirstName.getText())))
                    binding.RegisterEtFirstName.disableError();
                else binding.RegisterEtFirstName.enableError(UserValidations.firstNameError);
            }
        });

        binding.RegisterEtLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateLastName(String.valueOf(binding.RegisterEtLastName.getText())))
                    binding.RegisterEtLastName.disableError();
                else binding.RegisterEtLastName.enableError(UserValidations.lastNameError);
            }
        });


        binding.RegisterEtCountry.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateCountry(String.valueOf(binding.RegisterEtCountry.getText())))
                    binding.RegisterEtCountry.disableError();
                else binding.RegisterEtCountry.enableError(UserValidations.countryError);
            }
        });

        binding.RegisterEtCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateCity(String.valueOf(binding.RegisterEtCity.getText())))
                    binding.RegisterEtCity.disableError();
                else binding.RegisterEtCity.enableError(UserValidations.cityError);
            }
        });

        binding.RegisterEtBirth.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateFirstName(String.valueOf(binding.RegisterEtFirstName.getText())))
                    binding.RegisterEtBirth.disableError();
                else binding.RegisterEtBirth.enableError(UserValidations.firstNameError);
            }
        });

        binding.RegisterEtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validateEmail(String.valueOf(binding.RegisterEtEmail.getText())))
                    binding.RegisterEtEmail.disableError();
                else binding.RegisterEtEmail.enableError(UserValidations.emailError);
            }
        });

        binding.RegisterEtPhoneCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validatePhoneCode(String.valueOf(binding.RegisterEtPhoneCode.getText())))
                    binding.RegisterEtPhoneCode.disableError();
                else binding.RegisterEtPhoneCode.enableError(UserValidations.phoneCodeError);
            }
        });

        binding.RegisterEtPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (UserValidations.validatePhone(String.valueOf(binding.RegisterEtPhone.getText())))
                    binding.RegisterEtPhone.disableError();
                else binding.RegisterEtPhone.enableError(UserValidations.phoneError);
            }
        });
    }

    private boolean validate() {
        boolean validate = true;

        if (!UserValidations.validateUsername(String.valueOf(binding.RegisterEtUsername.getText()))){
            validate = false;
            binding.RegisterEtUsername.enableError(UserValidations.usernameError);
        }

        if (!UserValidations.validatePassword(String.valueOf(binding.RegisterEtPassword.getText()))){
            validate = false;
            binding.RegisterEtPassword.enableError(UserValidations.passwordError);
        }

        if (!UserValidations.validateFirstName(String.valueOf(binding.RegisterEtFirstName.getText()))){
            validate = false;
            binding.RegisterEtFirstName.enableError(UserValidations.firstNameError);
        }

        if (!UserValidations.validateLastName(String.valueOf(binding.RegisterEtLastName.getText()))){
            validate = false;
            binding.RegisterEtLastName.enableError(UserValidations.lastNameError);
        }

        if (!UserValidations.validateGender(String.valueOf(binding.RegisterACTVGender.getText()))){
            validate = false;
            binding.RegisterComboBoxGender.setError(UserValidations.genderError);
        }

        if (!UserValidations.validateBirthdate(String.valueOf(binding.RegisterEtBirth.getText()))) {
            validate = false;
            binding.RegisterEtBirth.enableError(UserValidations.birthdateError);
        }

        if (!UserValidations.validateCountry(String.valueOf(binding.RegisterEtCountry.getText()))){
            validate = false;
            binding.RegisterEtCountry.enableError(UserValidations.countryError);
        }

        if (!UserValidations.validateCity(String.valueOf(binding.RegisterEtCity.getText()))){
            validate = false;
            binding.RegisterEtCity.enableError(UserValidations.cityError);
        }

        if (!UserValidations.validateEmail(String.valueOf(binding.RegisterEtEmail.getText()))){
            validate = false;
            binding.RegisterEtEmail.enableError(UserValidations.emailError);
        }

        if (!UserValidations.validatePhoneCode(String.valueOf(binding.RegisterEtPhoneCode.getText()))){
            validate = false;
            binding.RegisterEtPhoneCode.enableError(UserValidations.phoneCodeError);
        }

        if (!UserValidations.validatePhone(String.valueOf(binding.RegisterEtPhone.getText()))){
            validate = false;
            binding.RegisterEtPhone.enableError(UserValidations.phoneError);
        }

        return validate;
    }

    private void signup(){
        User user = new User();
        user.setUsername(String.valueOf(binding.RegisterEtUsername.getText()));
        user.setPassword(String.valueOf(binding.RegisterEtPassword.getText()));
        user.setFirstName(String.valueOf(binding.RegisterEtFirstName.getText()));
        user.setLastName(String.valueOf(binding.RegisterEtLastName.getText()));
        user.setGender(String.valueOf(binding.RegisterACTVGender.getText()));
        user.setBirthdate(birthDate);
        user.setCountry(String.valueOf(binding.RegisterEtCountry.getText()));
        user.setCity(String.valueOf(binding.RegisterEtCity.getText()));
        user.setEmail(String.valueOf(binding.RegisterEtEmail.getText()));
        user.setPhoneCountryCode(String.valueOf(binding.RegisterEtPhoneCode.getText()));
        user.setPhone(String.valueOf(binding.RegisterEtPhone.getText()));
        SingletonUser.getInstance(this).signupAPI(user,this);
    }

    @Override
    public void onSignup() {
        Toast.makeText(this, R.string.signup_verification, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onErrorSignup(String message) {
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