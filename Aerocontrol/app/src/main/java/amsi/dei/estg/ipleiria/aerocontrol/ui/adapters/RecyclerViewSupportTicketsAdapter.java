package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.SupportTicketActivity;

public class RecyclerViewSupportTicketsAdapter extends RecyclerView.Adapter<RecyclerViewSupportTicketsAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SupportTicket> supportTickets;

    public RecyclerViewSupportTicketsAdapter(Context context, ArrayList<SupportTicket> supportTickets){
        this.context = context;
        this.supportTickets = supportTickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_ticket, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SupportTicket supportTicket = supportTickets.get(position);
        holder.updateSupportTicket(supportTicket);
    }

    @Override
    public int getItemCount() {
        return supportTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTicket, tvState;
        Button btDetails;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.tvTicket = view.findViewById(R.id.SupportTicket_Tv_Ticket);
            this.tvState = view.findViewById(R.id.SupportTicket_Tv_State);
            this.btDetails = view.findViewById(R.id.SupportTicket_Bt_Details);
        }

        public void updateSupportTicket(SupportTicket supportTicket) {
            this.tvTicket.setText("Ticket nº" + supportTicket.getId() + " - " + supportTicket.getTitle());
            this.tvState.setText(supportTicket.getState());
            this.btDetails.setOnClickListener(view -> {
                ((SupportTicketActivity) context).showSupportTicketDetails(supportTicket.getId());
            });
        }
    }
}
