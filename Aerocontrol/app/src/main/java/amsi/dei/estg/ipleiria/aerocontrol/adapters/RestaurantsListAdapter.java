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
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities.MainActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Restaurant> restaurants;

    public RestaurantsListAdapter(Context context, ArrayList<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant,parent,false);
        return new ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderList holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.updateRestaurant(restaurant);
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity) context).showRestaurantDetails(restaurant.getId());
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

        ImageView ivRestaurant;
        TextView tvRestaurant;

        public ViewHolderList(@NonNull View view){
            super(view);
            this.ivRestaurant = view.findViewById(R.id.Restaurant_Iv_Image);
            this.tvRestaurant = view.findViewById(R.id.Restaurant_Tv_Name);
        }

        public void updateRestaurant(Restaurant restaurant){
            this.tvRestaurant.setText(restaurant.getName());
            if (NetworkUtils.isConnectedInternet(itemView.getContext())) {
                Glide.with(this.itemView.getContext())
                        .load(ApiEndPoint.RESTAURANTS_IMAGE_FOLDER + restaurant.getName().replace(" ","_") + "/" + restaurant.getLogo())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivRestaurant);
            }
        }
    }
}
