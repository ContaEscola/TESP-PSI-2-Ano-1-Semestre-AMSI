package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.MainActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class RecyclerViewStoresAdapter extends RecyclerView.Adapter<RecyclerViewStoresAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Store> storesOriginal;
    private ArrayList<Store> storesFiltered;

    public RecyclerViewStoresAdapter(Context context, ArrayList<Store> stores){
        this.context = context;
        this.storesOriginal = stores;
        this.storesFiltered = new ArrayList<>(stores);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = storesFiltered.get(position);
        holder.updateStore(store);
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity) context).showStoreDetails(store.getId());
        });
    }

    @Override
    public int getItemCount(){
        return storesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Store> filteredList = null;
                if(charSequence.length() == 0)
                    filteredList = new ArrayList<>(storesOriginal);
                else
                    filteredList = getFilteredList(charSequence.toString().toLowerCase());

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                storesFiltered = (ArrayList<Store>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private ArrayList<Store> getFilteredList(String query) {
        ArrayList<Store> results = new ArrayList<>();

        for (Store store : storesOriginal) {
            if(store.getName().toLowerCase().contains(query))
                results.add(store);
        }

        return results;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivStore;
        TextView tvStore;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.ivStore = view.findViewById(R.id.Store_Iv_Image);
            this.tvStore = view.findViewById(R.id.Store_Tv_Name);
        }

        public void updateStore(Store store){
            this.tvStore.setText(store.getName());
            if (NetworkUtils.isConnectedInternet(itemView.getContext())) {
                Glide.with(this.itemView.getContext())
                        .load(store.getLogoAPIPath())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivStore);
            }
        }
    }
}
