package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentStoresBinding;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewStoresAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.StoresListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;

public class StoresFragment extends Fragment implements StoresListener {

    private RecyclerViewStoresAdapter adapter;
    private FragmentStoresBinding binding;

    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStoresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.StoresRvStores.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.StoresRvStores.setItemAnimator(new DefaultItemAnimator());
		
        SingletonEnterprises.getInstance(this.getContext()).setStoresListener(this);
        SingletonEnterprises.getInstance(this.getContext()).getStoresAPI(this.getContext());

        binding.StoresEtSearch.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Store> stores) {
        adapter = new RecyclerViewStoresAdapter(this.getContext(), stores);
        binding.StoresRvStores.setAdapter(adapter);
    }
}