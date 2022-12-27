package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;

public class AccountFragment extends Fragment {

    private Button btLogin, btLogout;
    private TextView tvUsername;
    private ConstraintLayout myTicketsLayout;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (SingletonUser.getInstance(this.getContext()).isLoggedIn()){
            view = inflater.inflate(R.layout.fragment_account_loggedin, container, false);
            initializeLoggedIn(view);
        }
        else {
            view = inflater.inflate(R.layout.fragment_account_loggedout, container, false);
            initializeLoggedOut(view);
        }

        return view;
    }

    private void initializeLoggedOut(View view) {
        btLogin = view.findViewById(R.id.AccountLoggedOut_Bt_Login);

        btLogin.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,1);
        });
    }

    private void initializeLoggedIn(View view) {
        btLogout = view.findViewById(R.id.AccountLoggedIn_Bt_Logout);
        tvUsername = view.findViewById(R.id.AccountLoggedIn_Tv_Username);
        myTicketsLayout = view.findViewById(R.id.AccountLoggedIn_ConsLayout_MyTickets);

        tvUsername.setText(SingletonUser.getInstance(this.getContext()).getUser().getUsername());

        btLogout.setOnClickListener(view1 -> logout());

        myTicketsLayout.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getContext(),TicketsActivity.class);
            startActivity(intent);
        });
    }

    private void logout() {
        SingletonUser.getInstance(this.getContext()).setLoggedIn(false);
        UserPreferences.getInstance(this.getContext()).clearUser();
        refreshFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1)
            if (resultCode == Activity.RESULT_OK){
                refreshFragment();
            }
    }

    private void refreshFragment(){
        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.ActivityMain_Fragment, AccountFragment.class, null);
        transaction.commit();
    }

}