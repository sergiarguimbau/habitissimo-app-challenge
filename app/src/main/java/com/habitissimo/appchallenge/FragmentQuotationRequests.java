package com.habitissimo.appchallenge;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentQuotationRequests extends Fragment {


    public FragmentQuotationRequests() {
        // Required empty public constructor
    }

    private RecyclerView recView;
    private ArrayList<Quotation> quotations;

    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton add_quotation;
    private LinearLayout layout_bottom_sheet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_quotation_requests, container, false);

        add_quotation = (FloatingActionButton) view.findViewById(R.id.fab_add);
        layout_bottom_sheet = (LinearLayout) view.findViewById(R.id.layout_bottom_sheet);
        View bottomSheet = view.findViewById(R.id.bottom_sheet_add_quotation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Create contact examples
        final Contact contact_antonio = new Contact("Antonio Llinares", "626 42 39 01", "antonito@email.com", "Palma de Mallorca, 07013");
        Contact contact_maria = new Contact("María Salvado", "692 39 29 10", "maria@email.com", "Murcia, 30110");

        quotations = new ArrayList<Quotation>();

        quotations.add(new Quotation(R.drawable.cat_construccion, "Construir muro", "Desearía construir un muro en el patio de mi casa", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_reformas,"Reparación tubería", "Quiero reparar el baño que el cuaarto piso tiene goteras. Necesito ayuda urgente", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_mudanzas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_mantenimiento,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_tecnicos,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_tiendas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_instaladores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_obras_menores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));

        recView = (RecyclerView) view.findViewById(R.id.rec_view_quotation);
        recView.setHasFixedSize(false);

        final QuotationAdapter adaptador = new QuotationAdapter(quotations, new QuotationAdapter.ItemClickListener() {
            @Override
            public void onPositionClicked(int position) {
                openContactDialog(quotations.get(position).contact);
            }
        });

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBottomSheetOptions bottomSheetPayment = new DialogBottomSheetOptions();
                Bundle args = new Bundle();
                args.putSerializable("position", recView.getChildAdapterPosition(v));
                bottomSheetPayment.setArguments(args);
                bottomSheetPayment.show(getFragmentManager(), "dialog_bottom_sheet_options");
            }
        });

        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        recView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

        add_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        layout_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return view;
    }

    private void openContactDialog(Contact contact){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog_contact");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new DialogContact(contact);
        newFragment.show(ft, "dialog_contact");
    }

}
