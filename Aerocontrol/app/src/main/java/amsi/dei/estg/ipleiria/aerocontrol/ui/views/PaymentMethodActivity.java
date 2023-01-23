package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.PaymentMethod;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonUser;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityPaymentMethodBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.PaymentMethodsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.TicketBoughtListener;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentMethodsListener, TicketBoughtListener {

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    private String paymentMethodSelected = "";

    private int num_passengers, flightGoId, flightBackId;
    private boolean two_way_trip;

    private AlertDialog.Builder dialog;

    ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.PaymentMethodToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonFlights.getInstance(this).setPaymentMethodsListener(this);
        SingletonFlights.getInstance(this).setTicketBoughtListener(this);
        SingletonFlights.getInstance(this).getPaymentMethodsAPI(this);

        two_way_trip = getIntent().getBooleanExtra(TWO_WAY_TRIP,false);
        flightGoId = getIntent().getIntExtra(FLIGHT_GO_ID,-1);
        if (two_way_trip) flightBackId = getIntent().getIntExtra(FLIGHT_BACK_ID,-1);
        else flightBackId = -1;
        num_passengers = getIntent().getIntExtra(NUM_PASSENGERS,-1);

        binding.PaymentMethodBtConfirm.setOnClickListener( v -> {
            if (!paymentMethodSelected.equals("")) {
                dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Compra de bilhete");
                dialog.setMessage("Compra a ser processada...");
                dialog.setPositiveButton(R.string.ok, (dialog1, which) -> {});
                dialog.show();
                SingletonFlights.getInstance(this).buyTicketAPI(this,
                        two_way_trip,
                        flightGoId,
                        flightBackId,
                        num_passengers,
                        paymentMethodSelected,
                        SingletonUser.getInstance(this).getUser().getToken());
            } else Toast.makeText(this, R.string.select_payment_method, Toast.LENGTH_SHORT).show();
        });
    }



    private void setPaymentMethodSelected(String paymentMethod) {
        this.paymentMethodSelected = paymentMethod;
        Toast.makeText(this, R.string.confirm_payment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentMethodsRefresh(ArrayList<PaymentMethod> paymentMethods) {
        if (!paymentMethods.get(0).getState()){
            setDisabled(binding.PaymentMethodCreditCard);
        } else binding.PaymentMethodCreditCard.setOnClickListener(v -> setPaymentMethodSelected("Cartão de crédito"));
        if (!paymentMethods.get(1).getState()){
            setDisabled(binding.PaymentMethodDebitCard);
        } else binding.PaymentMethodDebitCard.setOnClickListener(v -> setPaymentMethodSelected("Cartão de débito"));
        if (!paymentMethods.get(2).getState()) {
            setDisabled(binding.PaymentMethodMbWay);
        } else binding.PaymentMethodMbWay.setOnClickListener(v -> setPaymentMethodSelected("MBWay"));
        if (!paymentMethods.get(3).getState()){
            setDisabled(binding.PaymentMethodMb);
        } else  binding.PaymentMethodMb.setOnClickListener(v -> setPaymentMethodSelected("Multibanco"));
        if (!paymentMethods.get(4).getState()){
            setDisabled(binding.PaymentMethodPaypal);
        } else binding.PaymentMethodPaypal.setOnClickListener(v -> setPaymentMethodSelected("Paypal"));
    }

    private void setDisabled(ConstraintLayout constraintLayout){
        constraintLayout.setBackgroundResource(R.drawable.payment_method_disabled_background);
        constraintLayout.setOnClickListener(v -> Toast.makeText(this, R.string.payment_method_not_available, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onTicketBought() {
        Toast.makeText(this, "O seu bilhete foi comprado com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}