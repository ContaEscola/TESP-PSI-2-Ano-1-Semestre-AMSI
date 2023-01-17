package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.SupportTicketsListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportBinding;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportTicketListBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;

public class SupportTicketActivity extends AppCompatActivity implements SupportTicketListener {

    private SupportTicketsListAdapter adapter;

    private RecyclerView recyclerView;
    private ActivitySupportTicketListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_ticket_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.SupportTicket_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.SupportTicketRvTickets.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRefreshList(ArrayList<SupportTicket> supportTickets) {
        adapter = new SupportTicketsListAdapter(this, SingletonUser.getInstance(this).getSupportTickets());
    }
}