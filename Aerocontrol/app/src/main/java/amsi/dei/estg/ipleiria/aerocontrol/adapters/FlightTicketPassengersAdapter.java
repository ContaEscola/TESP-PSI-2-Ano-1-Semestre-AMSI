package amsi.dei.estg.ipleiria.aerocontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;
import amsi.dei.estg.ipleiria.aerocontrol.utils.MyTextWatcher;

public class FlightTicketPassengersAdapter extends RecyclerView.Adapter<FlightTicketPassengersAdapter.ViewHolderList>{

    private final Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Passenger> passengers;

    public FlightTicketPassengersAdapter(Context context, ArrayList<Passenger> passengers){
        this.context = context;
        this.passengers = passengers;
    }

    @NonNull
    @Override
    public FlightTicketPassengersAdapter.ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_ticket_passenger,parent,false);
        return new FlightTicketPassengersAdapter.ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightTicketPassengersAdapter.ViewHolderList holder, int position) {
       // Passenger passenger = passengers.get(position);
        holder.initializeCbGenders(context);

        holder.tvName.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passengers.get(holder.getAdapterPosition()).setName(s.toString());
            }
        });

        holder.cbGender.setOnItemClickListener((parent, view, genderPosition, id) -> {
            passengers.get(holder.getAdapterPosition()).setGender(User.GENDERS[genderPosition]);
        });

        holder.cbExtraBaggage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            passengers.get(holder.getAdapterPosition()).setExtraBaggage(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    public ArrayList<Passenger> getPassengers(){
        return this.passengers;
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

        CheckBox cbExtraBaggage;
        EditText tvName;
        AutoCompleteTextView cbGender;

        public ViewHolderList(@NonNull View view){
            super(view);
            this.cbGender = view.findViewById(R.id.TicketPassenger_ACTV_Gender);
            this.tvName = view.findViewById(R.id.TicketPassenger_Et_Name);
            this.cbExtraBaggage = view.findViewById(R.id.TicketPassenger_Cb_ExtraBaggage);
        }

        private void initializeCbGenders(Context context) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.gender_list_item, User.GENDERS);
            this.cbGender.setAdapter(adapter);
            this.cbGender.setText(User.GENDERS[0],false);
        }
    }
}

