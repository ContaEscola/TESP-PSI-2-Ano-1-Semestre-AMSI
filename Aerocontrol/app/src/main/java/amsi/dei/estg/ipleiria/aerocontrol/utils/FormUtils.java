package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.view.View;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;


public class FormUtils {

    private static ArrayList<EditText> editTextsList;
    private static ConstraintLayout mLayout;

    public static void setFocusEventsOnInputs(ConstraintLayout formWrapperLayout) {
        editTextsList = new ArrayList<>();
        mLayout = formWrapperLayout;

        getAllEditTexts();
        setEvents();
    }


    private static void getAllEditTexts() {
        for (int i = 0; i < mLayout.getChildCount(); i++)
            if (mLayout.getChildAt(i) instanceof EditText)
                editTextsList.add((EditText) mLayout.getChildAt(i));
    }

    private static void setEvents() {
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
