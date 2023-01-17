package amsi.dei.estg.ipleiria.aerocontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.MainActivity;
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
        public ViewHolderList(@NonNull View view) {
            super(view);
            this.tvTicket = view.findViewById(R.id.SupportTicket_Tv_Ticket);
            this.tvState = view.findViewById(R.id.SupportTicket_Tv_State);
        }

        public void updateSupportTicket(SupportTicket supportTicket) {
            this.tvTicket.setText("Ticket nº" + supportTicket.getId() + " - " + supportTicket.getTitle());
            this.tvState.setText(supportTicket.getState());
        }
    }
}
