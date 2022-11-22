package amsi.dei.estg.ipleiria.aerocontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<EditText> editTextsList;
    private ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextsList = new ArrayList<>();
        mLayout =  findViewById(R.id.Login_Wrapper);
        getAllEditTexts();
        setFocusEventOnEditTexts();
    }

    private void getAllEditTexts() {
        for (int i = 0; i < mLayout.getChildCount(); i++)
            if (mLayout.getChildAt(i) instanceof EditText)
                editTextsList.add((EditText) mLayout.getChildAt(i));
    }

    private void setFocusEventOnEditTexts() {
        for (EditText editText : editTextsList) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(hasFocus)
                        editText.setBackgroundResource(R.drawable.edit_text_border_focused);
                    else
                        editText.setBackgroundResource(R.drawable.edit_text_border);
                }
            });
        }
    }
}