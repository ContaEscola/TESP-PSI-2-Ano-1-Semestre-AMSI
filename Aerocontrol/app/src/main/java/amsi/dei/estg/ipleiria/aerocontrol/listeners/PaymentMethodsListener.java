package amsi.dei.estg.ipleiria.aerocontrol.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.PaymentMethod;

public interface PaymentMethodsListener {
    void onPaymentMethodsRefresh(ArrayList<PaymentMethod> paymentMethods);
}
