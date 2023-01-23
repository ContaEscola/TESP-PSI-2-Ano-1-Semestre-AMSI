package amsi.dei.estg.ipleiria.aerocontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.MainActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class StoresListAdapter extends RecyclerView.Adapter<StoresListAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Store> stores;

    public StoresListAdapter(Context context, ArrayList<Store> stores){
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public StoresListAdapter.ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        return new ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull StoresListAdapter.ViewHolderList holder, int position) {
        Store store = stores.get(position);
        holder.updateStore(store);
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity) context).showStoreDetails(store.getId());
        });
    }

    @Override
    public int getItemCount(){
        return stores.size();
    }

    public class ViewHolderList extends RecyclerView.ViewHolder{

        ImageView ivStore;
        TextView tvStore;

        public ViewHolderList(@NonNull View view) {
            super(view);
            this.ivStore = view.findViewById(R.id.Store_Iv_Image);
            this.tvStore = view.findViewById(R.id.Store_Tv_Name);
        }

        public void updateStore(Store store){
            this.tvStore.setText(store.getName());
            if (NetworkUtils.isConnectedInternet(itemView.getContext())) {
                Glide.with(this.itemView.getContext())
                        .load(ApiEndPoint.STORES_IMAGE_FOLDER + store.getName().replace(" ","_") + "/" +store.getLogo())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivStore);
            }
        }
    }
}
