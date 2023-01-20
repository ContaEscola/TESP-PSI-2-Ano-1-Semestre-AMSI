package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.TicketMessageAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivitySupportTicketInfoBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.SupportTicketListener;

public class SupportTicketInfoActivity extends AppCompatActivity implements SupportTicketListener {

    public static final String SUPPORT_TICKET_ID = "support_ticket_id";

    private SupportTicket supportTicket;

    private ActivitySupportTicketInfoBinding binding;

    private TicketMessageAdapter adapter;

    private String message;
    private Integer support_ticket_id;

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

        support_ticket_id = getIntent().getIntExtra(SUPPORT_TICKET_ID, -1);

        getSupportTicketId();

    }

    private void getSupportTicketId(){
        if (support_ticket_id != -1){
            supportTicket = SingletonUser.getInstance(this).getSupportTicketById(support_ticket_id);
            binding.SupportTicketInfoIBtSend.setOnClickListener(v -> saveData());
            binding.SupportTicketInfoBtClose.setOnClickListener(v -> closeSupportTicket());
            supportTicketMessage();
        } else Toast.makeText(this, R.string.error_on_support_ticket, Toast.LENGTH_SHORT).show();
    }

    private void saveData() {
        message = binding.editTextTextPersonName.getText().toString();
        SingletonUser.getInstance(this).setMessageSupportTicketAPI(this, message, supportTicket);
        binding.editTextTextPersonName.setText("");
    }

    private void closeSupportTicket(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SupportTicketInfoActivity.this);
        builder.setTitle(R.string.conclude_support_ticket);
        builder.setMessage("Se deseja realmente concluir o seu suporte ticket por favor confirme abaixo.");
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            SingletonUser.getInstance(this).updateSupportTicketAPI(this, supportTicket);
        });
        builder.setNegativeButton(R.string.cancel,(dialog,which) -> {});
        builder.show();
    }

    private void supportTicketMessage(){
        binding.SupportTicketInfoTvTitle.setText("Ticket nº" + supportTicket.getId() + " - " + supportTicket.getTitle());
        binding.SupportTicketInfoTvState.setText(supportTicket.getState());
        if (supportTicket.getMessages().size() > 0){
            binding.SupportTicketInfoRvTickets.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TicketMessageAdapter(this, supportTicket.getMessages());
            binding.SupportTicketInfoRvTickets.setAdapter(adapter);
            System.out.println(supportTicket.getMessages().get(0).getMessage());
            binding.SupportTicketInfoRvTickets.setItemAnimator(new DefaultItemAnimator());
        }
        if (supportTicket.getState().equals("Concluido")){
            binding.SupportTicketInfoBtClose.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRefreshSupportTicket() {
        binding.SupportTicketInfoBtClose.setVisibility(View.INVISIBLE);
        supportTicketMessage();
    }
}