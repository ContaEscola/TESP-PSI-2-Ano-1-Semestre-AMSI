package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class EditPersonalDataFragment extends Fragment {

    public EditPersonalDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_personal_data, container, false);
    }
}