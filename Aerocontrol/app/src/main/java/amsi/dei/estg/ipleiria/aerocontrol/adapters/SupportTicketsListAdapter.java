package amsi.dei.estg.ipleiria.aerocontrol.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities.SupportTicketActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class SupportTicketsListAdapter extends RecyclerView.Adapter<SupportTicketsListAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SupportTicket> supportTickets;

    public SupportTicketsListAdapter(Context context, ArrayList<SupportTicket> supportTickets){
        this.context = context;
        this.supportTickets = supportTickets;
    }

    @NonNull
    @Override
    public ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_ticket, parent, false);
        return new ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderList holder, int position) {
        SupportTicket supportTicket = supportTickets.get(position);
        holder.updateSupportTicket(supportTicket);
    }

    @Override
    public int getItemCount() {
        return supportTickets.size();
    }

    public class ViewHolderList extends RecyclerView.ViewHolder {

        TextView tvTicket, tvState;
        Button btDetails, btImage;
        public ViewHolderList(@NonNull View view) {
            super(view);
            this.tvTicket = view.findViewById(R.id.SupportTicket_Tv_Ticket);
            this.tvState = view.findViewById(R.id.SupportTicket_Tv_State);
            this.btDetails = view.findViewById(R.id.SupportTicket_Bt_Details);
            this.btImage = view.findViewById(R.id.SupportTicket_Bt_Item);
        }

        public void updateSupportTicket(SupportTicket supportTicket) {
            this.tvTicket.setText("Ticket nº" + supportTicket.getId() + " - " + supportTicket.getTitle());
            this.tvState.setText(supportTicket.getState());
            this.btImage.setVisibility(View.GONE); // Reforçar o GONE porque o Android dá conflito e mostra as imagens quando não deve
            if (supportTicket.getItems() != null && supportTicket.getItems().size() > 0){
                this.btImage.setVisibility(View.VISIBLE);
                this.btImage.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle(R.string.support_ticket_image);
                    ImageView ivLostItem = new ImageView(view.getRootView().getContext());

                    if (NetworkUtils.isConnectedInternet(itemView.getContext())) {
                        Glide.with(this.itemView.getContext())
                                .load(ApiEndPoint.LOST_ITEM_IMAGE_FOLDER + supportTicket.getItems().get(0).getImage())
                                .placeholder(R.drawable.placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(ivLostItem);
                    }

                    builder.setView(ivLostItem);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> {});
                    builder.show();
                });
            }
            this.btDetails.setOnClickListener(view -> {
                ((SupportTicketActivity) context).showSupportTicketDetails(supportTicket.getId());
            });
        }
    }
}
