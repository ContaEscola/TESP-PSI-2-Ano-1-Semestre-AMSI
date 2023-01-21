package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
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
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class RecyclerViewRestaurantItemsAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantItemsAdapter.ViewHolder> {

    private Context context;
    private Restaurant relativeRestaurant;
    private ArrayList<RestaurantItem> restaurantItems;

    public RecyclerViewRestaurantItemsAdapter(Context context, Restaurant relativeRestaurant){
        this.context = context;
        this.relativeRestaurant = relativeRestaurant;
        this.restaurantItems = relativeRestaurant.getMenu();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_menu_item,parent,false);
        return new ViewHolder(item, relativeRestaurant);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantItem item = restaurantItems.get(position);
        holder.updateItem(item);
    }

    @Override
    public int getItemCount() {
        return restaurantItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Restaurant relativeRestaurant;
        ImageView ivItem;
        TextView tvItem;

        public ViewHolder(@NonNull View view, Restaurant relativeRestaurant){
            super(view);
            this.relativeRestaurant = relativeRestaurant;
            this.ivItem = view.findViewById(R.id.RestaurantItem_Iv_Image);
            this.tvItem = view.findViewById(R.id.RestaurantItem_Tv_Name);
        }

        public void updateItem(RestaurantItem item){
            this.tvItem.setText(item.getItem());
            if (!item.getState()){
                this.tvItem.setTextColor(itemView.getContext().getResources().getColor(R.color.orange_red));
                this.tvItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if (NetworkUtils.isConnectedInternet(itemView.getContext())){
                Glide.with(this.itemView.getContext())
                        .load(item.getImageAPIPath(relativeRestaurant))
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivItem);
            }

        }
    }
}
