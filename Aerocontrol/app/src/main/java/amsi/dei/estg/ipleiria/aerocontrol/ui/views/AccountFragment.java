package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentAccountLoggedInBinding;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentAccountLoggedOutBinding;

public class AccountFragment extends Fragment {

    private static final int REQUEST_LOGIN_ACTIVITY = 1;

    FragmentAccountLoggedInBinding bindingLoggedIn;
    FragmentAccountLoggedOutBinding bindingLoggedOut;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (SingletonUser.getInstance(this.getContext()).isLoggedIn()){
            bindingLoggedIn = FragmentAccountLoggedInBinding.inflate(getLayoutInflater());
            view = bindingLoggedIn.getRoot();
            initializeLoggedIn();
        }
        else {
            bindingLoggedOut = FragmentAccountLoggedOutBinding.inflate(getLayoutInflater());
            view = bindingLoggedOut.getRoot();
            initializeLoggedOut();
        }

        return view;
    }

    private void initializeLoggedOut() {
        bindingLoggedOut.AccountLoggedOutBtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN_ACTIVITY);
        });

        bindingLoggedOut.AccountLoggedOutConsLayoutSupport.setOnClickListener(v -> openSupportIntent());

        bindingLoggedOut.AccountLoggedOutBtCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initializeLoggedIn() {
        bindingLoggedIn.AccountLoggedInTvUsername.setText(SingletonUser.getInstance(this.getContext()).getUser().getUsername());

        bindingLoggedIn.AccountLoggedInBtLogout.setOnClickListener(v -> logout());

        bindingLoggedIn.AccountLoggedInConsLayoutMyTickets.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), FlightTicketsActivity.class);
            startActivity(intent);
        });

        bindingLoggedIn.AccountLoggedInConsLayoutEditData.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), EditAccountActivity.class);
            startActivity(intent);
        });

        bindingLoggedIn.AccountLoggedInConsLayoutSupportTicket.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), SupportTicketActivity.class);
            startActivity(intent);
        });

        bindingLoggedIn.AccountLoggedInConsLayoutSupport.setOnClickListener(v -> openSupportIntent());
    }

    private void openSupportIntent() {
        Intent intent = new Intent(this.getContext(), SupportActivity.class);
        startActivity(intent);
    }

    private void logout() {
        SingletonUser.getInstance(this.getContext()).setLoggedIn(false);
        UserPreferences.getInstance(this.getContext()).clearUser();
        Toast.makeText(getContext(), "Logout com successo!", Toast.LENGTH_SHORT).show();
        refreshFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_LOGIN_ACTIVITY)
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(getContext(), "Login com successo!", Toast.LENGTH_SHORT).show();
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