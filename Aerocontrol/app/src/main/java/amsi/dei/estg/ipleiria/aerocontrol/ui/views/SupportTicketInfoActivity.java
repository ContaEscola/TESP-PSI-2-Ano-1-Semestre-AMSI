package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportTicketInfoBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;

public class SupportTicketInfoActivity extends AppCompatActivity implements SupportTicketListener {

    public static final String SUPPORT_TICKET_ID = "support_ticket_id";

    private SupportTicket supportTicket;

    private ActivitySupportTicketInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportTicketInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.SupportTicketInfoToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonUser.getInstance(this).setSupportTicketListener(this);

        getSupportTicketId();
    }

    private void getSupportTicketId(){
        int idSupportTicket = getIntent().getIntExtra(SUPPORT_TICKET_ID, -1);

        if (idSupportTicket != -1){
            supportTicket = SingletonUser.getInstance(this).getSupportTicketById(idSupportTicket);
            binding.SupportTicketInfoTvTitle.setText("Ticket nº" + supportTicket.getId() + " - " + supportTicket.getTitle());
            binding.SupportTicketInfoTvState.setText(supportTicket.getState());
        } else Toast.makeText(this, R.string.error_on_support_ticket, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshList(ArrayList<SupportTicket> supportTickets) {

    }
}