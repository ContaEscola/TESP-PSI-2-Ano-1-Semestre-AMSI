package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewStoresAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.StoresListener;

public class StoresFragment extends Fragment implements StoresListener {

    private RecyclerViewStoresAdapter adapter;
    private RecyclerView recyclerView;

    private EditText tvSearch;

    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);

        recyclerView = view.findViewById(R.id.Stores_Rv_Stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SingletonEnterprises.getInstance(this.getContext()).setStoresListener(this);
        SingletonEnterprises.getInstance(this.getContext()).getStoresAPI(this.getContext());

        tvSearch = view.findViewById(R.id.Stores_Et_Search);

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Store> stores) {
        adapter = new RecyclerViewStoresAdapter(this.getContext(), stores);
        recyclerView.setAdapter(adapter);
    }
}