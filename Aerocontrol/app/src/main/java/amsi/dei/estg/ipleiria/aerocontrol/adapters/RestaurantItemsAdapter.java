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
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;

public class RestaurantItemsAdapter extends RecyclerView.Adapter<RestaurantItemsAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<RestaurantItem> items;

    public RestaurantItemsAdapter(Context context, ArrayList<RestaurantItem> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_menu_item,parent,false);
        return new RestaurantItemsAdapter.ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderList holder, int position) {
        RestaurantItem item = items.get(position);
        //final int itemPosition = position;

        holder.updateItem(item);
        holder.itemView.setOnClickListener(view -> {
            //TODO ITEM CLICK
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

        ImageView ivItem;
        TextView tvItem;

        public ViewHolderList(@NonNull View view){
            super(view);
            this.ivItem = view.findViewById(R.id.RestaurantItem_Iv_Image);
            this.tvItem = view.findViewById(R.id.RestaurantItem_Tv_Name);
        }

        public void updateItem(RestaurantItem item){
            //TODO SE ESTIVER STATE == 0 DIFERENCIAR
            this.tvItem.setText(item.getItem());
            System.out.println(ApiEndPoint.RESTAURANTS_ITEMS_IMAGE_FOLDER+item.getImage());
            Glide.with(this.itemView.getContext())
                    .load(ApiEndPoint.RESTAURANTS_ITEMS_IMAGE_FOLDER+item.getImage())
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivItem);
        }
    }
}
