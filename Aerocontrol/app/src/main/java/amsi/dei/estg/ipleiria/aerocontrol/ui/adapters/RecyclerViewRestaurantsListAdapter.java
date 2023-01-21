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
import java.util.Locale;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.MainActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class RecyclerViewRestaurantsListAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantsListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Restaurant> restaurantsOriginal;
    private ArrayList<Restaurant> restaurantsFiltered;

    public RecyclerViewRestaurantsListAdapter(Context context, ArrayList<Restaurant> restaurants){
        this.context = context;
        this.restaurantsOriginal = restaurants;
        this.restaurantsFiltered = new ArrayList<>(restaurants);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantsFiltered.get(position);
        holder.updateRestaurant(restaurant);
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity) context).showRestaurantDetails(restaurant.getId());
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Restaurant> filteredList = null;
                if(charSequence.length() == 0)
                    filteredList = new ArrayList<>(restaurantsOriginal);
                else
                    filteredList = getFilteredList(charSequence.toString().toLowerCase());

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                restaurantsFiltered = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private ArrayList<Restaurant> getFilteredList(String query) {
        ArrayList<Restaurant> results = new ArrayList<>();

        for (Restaurant restaurant : restaurantsOriginal) {
            if(restaurant.getName().toLowerCase().contains(query))
                results.add(restaurant);
        }

        return results;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivRestaurant;
        TextView tvRestaurant;

        public ViewHolder(@NonNull View view){
            super(view);
            this.ivRestaurant = view.findViewById(R.id.Restaurant_Iv_Image);
            this.tvRestaurant = view.findViewById(R.id.Restaurant_Tv_Name);
        }

        public void updateRestaurant(Restaurant restaurant){
            this.tvRestaurant.setText(restaurant.getName());
            if (NetworkUtils.isConnectedInternet(itemView.getContext())) {
                Glide.with(this.itemView.getContext())
                        .load(restaurant.getLogoAPIPath())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivRestaurant);
            }
        }
    }
}
