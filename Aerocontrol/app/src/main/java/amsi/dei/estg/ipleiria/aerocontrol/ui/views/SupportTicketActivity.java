package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.SupportTicketsListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportTicketListBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketsListener;

public class SupportTicketActivity extends AppCompatActivity implements SupportTicketListener {

    private ActivitySupportTicketListBinding binding;

    private SupportTicketsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportTicketListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.SupportTicketToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.SupportTicketRvTickets.setLayoutManager(new LinearLayoutManager(this));

        SingletonUser.getInstance(this).setSupportTicketsListener(this::onRefreshList);
        SingletonUser.getInstance(this).getSupportTicketsAPI(this);
    }

    public void showSupportTicketDetails(int id){
        SupportTicket supportTicket = SingletonUser.getInstance(this).getSupportTicketById(id);
        Intent intent = new Intent(this, SupportTicketInfoActivity.class);
        intent.putExtra(SupportTicketInfoActivity.SUPPORT_TICKET_ID, (int) supportTicket.getId());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRefreshList(ArrayList<SupportTicket> supportTickets) {
        adapter = new SupportTicketsListAdapter(this, SingletonUser.getInstance(this).getSupportTickets());
        binding.SupportTicketRvTickets.setAdapter(adapter);
        binding.SupportTicketRvTickets.setItemAnimator(new DefaultItemAnimator());
    }
}