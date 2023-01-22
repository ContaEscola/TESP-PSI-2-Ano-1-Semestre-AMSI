package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.adapters.StoresListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentStoresBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListenerStore;

public class StoresFragment extends Fragment implements EnterprisesListenerStore {

    private StoresListAdapter adapter;

    private FragmentStoresBinding binding;

    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStoresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.StoresRvStores.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonEnterprises.getInstance(this.getContext()).setEnterprisesListenerStore(this);
        SingletonEnterprises.getInstance(this.getContext()).getStoresAPI(this.getContext());

        binding.StoresEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Store> auxStores = new ArrayList<>();
                for (Store store: SingletonEnterprises.getInstance(getContext()).getStores()) {
                    if (store.getName().toUpperCase().contains(s.toString().toUpperCase())){
                        auxStores.add(store);
                    }
                }
                binding.StoresRvStores.setAdapter(new StoresListAdapter(getContext(),auxStores));
                binding.StoresRvStores.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Store> stores) {
        adapter = new StoresListAdapter(this.getContext(),SingletonEnterprises.getInstance(getContext()).getStores());
        binding.StoresRvStores.setAdapter(adapter);
        binding.StoresRvStores.setItemAnimator(new DefaultItemAnimator());
    }
}