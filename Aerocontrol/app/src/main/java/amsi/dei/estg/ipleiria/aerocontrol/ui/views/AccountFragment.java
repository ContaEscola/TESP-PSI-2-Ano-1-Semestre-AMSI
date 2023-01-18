package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.data.prefs.UserPreferences;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentAccountLoggedinBinding;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentAccountLoggedoutBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountLoggedinBinding bindingLoggedIn;
    private FragmentAccountLoggedoutBinding bindingLoggedOut;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (SingletonUser.getInstance(this.getContext()).isLoggedIn()){
            bindingLoggedIn = FragmentAccountLoggedinBinding.inflate(getLayoutInflater());
            view = bindingLoggedIn.getRoot();
            initializeLoggedIn();
        }
        else {
            bindingLoggedOut = FragmentAccountLoggedoutBinding.inflate(getLayoutInflater());
            view = bindingLoggedOut.getRoot();
            initializeLoggedOut();
        }

        return view;
    }

    private void initializeLoggedOut() {
        bindingLoggedOut.AccountLoggedOutBtLogin.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,1);
        });

        bindingLoggedOut.AccountLoggedOutConsLayoutSupport.setOnClickListener(v -> openSupportIntent());
    }

    private void initializeLoggedIn() {
        bindingLoggedIn.AccountLoggedInTvUsername.setText(SingletonUser.getInstance(this.getContext()).getUser().getUsername());

        bindingLoggedIn.AccountLoggedInBtLogout.setOnClickListener(view1 -> logout());

        bindingLoggedIn.AccountLoggedInConsLayoutMyTickets.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(),TicketsActivity.class);
            startActivity(intent);
        });

        bindingLoggedIn.AccountLoggedInConsLayoutEditData.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), EditAccountActivity.class);
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