package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import amsi.dei.estg.ipleiria.aerocontrol.R;

public class AccountFragment extends Fragment {

    private Button btLogin;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // If (loggedIn)
        //return inflater.inflate(R.layout.fragment_account_loggedin, container, false);
        // else
        //return inflater.inflate(R.layout.fragment_account_loggedout, container, false);
        View view = inflater.inflate(R.layout.fragment_account_loggedout, container, false);

        btLogin = view.findViewById(R.id.AccountLoggedOut_Bt_Login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });






        return view;
    }
}