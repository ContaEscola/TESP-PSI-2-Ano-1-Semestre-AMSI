package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.SupportTicketsListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportTicketListBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketsListener;

public class SupportTicketActivity extends AppCompatActivity implements SupportTicketsListener, SupportTicketListener {

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

        SingletonUser.getInstance(this).setSupportTicketsListener(this);
        SingletonUser.getInstance(this).getSupportTicketsAPI(this);

        binding.SupportTicketFabAdd.setOnClickListener(v -> createSupportTicket());
    }

    public void showSupportTicketDetails(int id){
        SupportTicket supportTicket = SingletonUser.getInstance(this).getSupportTicketById(id);
        Intent intent = new Intent(this, SupportTicketInfoActivity.class);
        intent.putExtra(SupportTicketInfoActivity.SUPPORT_TICKET_ID, (int) supportTicket.getId());
        startActivityForResult(intent, 1);
    }

    public void createSupportTicket(){
        SingletonUser.getInstance(this).setSupportTicketListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.create_support_ticket);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText title = new EditText(this);
        title.setHint(getString(R.string.title));
        layout.addView(title);

        EditText message = new EditText(this);
        message.setSingleLine(false);
        message.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        message.setHint(getString(R.string.message));
        layout.addView(message);

        builder.setView(layout);

        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            SingletonUser.getInstance(this).createSupportTicketAPI(this, title.getText().toString(), message.getText().toString());
        });
        builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});

        builder.show();
    }

    private void setAdapter(){
        ArrayList<SupportTicket> reversedList = new ArrayList<>(SingletonUser.getInstance(this).getSupportTickets());
        Collections.reverse(reversedList);
        adapter = new SupportTicketsListAdapter(this, reversedList);
        binding.SupportTicketRvTickets.setAdapter(adapter);
        binding.SupportTicketRvTickets.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRefreshList(ArrayList<SupportTicket> supportTickets) {
        setAdapter();
    }

    @Override
    public void onRefreshSupportTicket() {
        setAdapter();
    }
}