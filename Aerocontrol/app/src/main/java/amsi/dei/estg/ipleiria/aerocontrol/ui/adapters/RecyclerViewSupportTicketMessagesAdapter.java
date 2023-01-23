package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.TicketMessage;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;

public class RecyclerViewSupportTicketMessagesAdapter extends RecyclerView.Adapter<RecyclerViewSupportTicketMessagesAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<TicketMessage> messages;

    public RecyclerViewSupportTicketMessagesAdapter(Context context, ArrayList<TicketMessage> messages){
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerViewSupportTicketMessagesAdapter.ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_ticket_message, parent, false);
        return new ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSupportTicketMessagesAdapter.ViewHolderList holder, int position) {
        TicketMessage message = messages.get(position);
        holder.showMessage(message, context);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolderList extends RecyclerView.ViewHolder {

        private TextView tvUsernameLeft, tvMessageLeft, tvUsernameRight, tvMessageRight;
        private ConstraintLayout consLayoutLeft, consLayoutRight;
        public ViewHolderList(@NonNull View view) {
            super(view);
            this.tvUsernameLeft = view.findViewById(R.id.SupportTicketMessage_Tv_NameLeft);
            this.tvMessageLeft = view.findViewById(R.id.SupportTicketMessage_Tv_MessageLeft);
            this.tvUsernameRight = view.findViewById(R.id.SupportTicketMessage_Tv_NameRight);
            this.tvMessageRight = view.findViewById(R.id.SupportTicketMessage_Tv_MessageRight);
            this.consLayoutLeft = view.findViewById(R.id.SupportTicketMessage_ConsLayout_Left);
            this.consLayoutRight = view.findViewById(R.id.SupportTicketMessage_ConsLayout_Right);
        }

        public void showMessage(TicketMessage message, Context context) {
            if (message.getSender().equals(SingletonUser.getInstance(context).getUser().getUsername()) ){
                this.tvUsernameRight.setText(message.getSender());
                this.tvMessageRight.setText(message.getMessage());
                this.tvUsernameLeft.setVisibility(View.GONE);
                this.consLayoutLeft.setVisibility(View.GONE);
            }else {
                this.tvUsernameLeft.setText(message.getSender());
                this.tvMessageLeft.setText(message.getMessage());
                this.tvUsernameRight.setVisibility(View.GONE);
                this.consLayoutRight.setVisibility(View.GONE);
            }
        }
    }
}
