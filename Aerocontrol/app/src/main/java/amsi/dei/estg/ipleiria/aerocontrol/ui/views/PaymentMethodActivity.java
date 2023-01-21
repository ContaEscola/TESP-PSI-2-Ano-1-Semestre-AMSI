package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.PaymentMethod;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityPaymentMethodBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.PaymentMethodsListener;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentMethodsListener {

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    private int paymentMethodSelected = -1;

    private int num_passengers, flightGoId, flightBackId;
    private boolean two_way_trip;

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
        SingletonFlights.getInstance(this).getPaymentMethodsAPI(this);

        two_way_trip = getIntent().getBooleanExtra(TWO_WAY_TRIP,false);
        flightGoId = getIntent().getIntExtra(FLIGHT_GO_ID,-1);
        if (two_way_trip) flightBackId = getIntent().getIntExtra(FLIGHT_BACK_ID,-1);
        num_passengers = getIntent().getIntExtra(NUM_PASSENGERS,-1);

        binding.PaymentMethodBtConfirm.setOnClickListener( v -> {
            if (paymentMethodSelected != -1) {
                System.out.println("POSSO PAGAR");
                System.out.println("FLIGHTIDGO " + flightGoId);
                System.out.println("FLIGHTIDBACK " + flightBackId);
                System.out.println("NUMPASSENGERS " + num_passengers);
                System.out.println("TWO_WAY_TRIP "+ two_way_trip);
                System.out.println("PAYMENT_METHODID " + paymentMethodSelected);
            } else Toast.makeText(this, R.string.select_payment_method, Toast.LENGTH_SHORT).show();
        });
    }



    private void setPaymentMethodSelected(int paymentMethodId) {
        this.paymentMethodSelected = paymentMethodId;
        Toast.makeText(this, R.string.confirm_payment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentMethodsRefresh(ArrayList<PaymentMethod> paymentMethods) {
        if (!paymentMethods.get(0).getState()){
            setClickableFalse(binding.PaymentMethodCreditCard);
        } else binding.PaymentMethodCreditCard.setOnClickListener(v -> setPaymentMethodSelected(1));
        if (!paymentMethods.get(1).getState()){
            setClickableFalse(binding.PaymentMethodDebitCard);
        } else binding.PaymentMethodDebitCard.setOnClickListener(v -> setPaymentMethodSelected(2));
        if (!paymentMethods.get(2).getState()) {
            setClickableFalse(binding.PaymentMethodMbWay);
        } else binding.PaymentMethodMbWay.setOnClickListener(v -> setPaymentMethodSelected(3));
        if (!paymentMethods.get(3).getState()){
            setClickableFalse(binding.PaymentMethodMb);
        } else  binding.PaymentMethodMb.setOnClickListener(v -> setPaymentMethodSelected(4));
        if (!paymentMethods.get(4).getState()){
            setClickableFalse(binding.PaymentMethodPaypal);
        } else binding.PaymentMethodPaypal.setOnClickListener(v -> setPaymentMethodSelected(5));
    }

    private void setClickableFalse(ConstraintLayout constraintLayout){
        constraintLayout.setOnClickListener(v -> Toast.makeText(this, R.string.payment_method_not_available, Toast.LENGTH_SHORT).show());
    }
}