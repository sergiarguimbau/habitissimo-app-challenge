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
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentQuotationRequests extends Fragment {


    public FragmentQuotationRequests() {
        // Required empty public constructor
    }

    private RecyclerView recView;
    private ArrayList<Quotation> quotations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_quotation_requests, container, false);

        quotations = new ArrayList<Quotation>();

        quotations.add(new Quotation(R.drawable.cat_construccion, "Construir muro", "Desearía construir un muro en el patio de mi casa"));
        quotations.add(new Quotation(R.drawable.cat_reformas,"Reparación tubería", "Quiero reparar el baño que el cuaarto piso tiene goteras. Necesito ayuda urgente" ));
        quotations.add(new Quotation(R.drawable.cat_mudanzas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));
        quotations.add(new Quotation(R.drawable.cat_mantenimiento,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));
        quotations.add(new Quotation(R.drawable.cat_tecnicos,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));
        quotations.add(new Quotation(R.drawable.cat_tiendas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));
        quotations.add(new Quotation(R.drawable.cat_instaladores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));
        quotations.add(new Quotation(R.drawable.cat_obras_menores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));

        recView = (RecyclerView) view.findViewById(R.id.rec_view_quotation);
        recView.setHasFixedSize(false);

        final QuotationAdapter adaptador = new QuotationAdapter(quotations, new QuotationAdapter.ItemClickListener() {
            @Override
            public void onPositionClicked(int position) {
                openContactDialog();
            }
        });

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBottomSheetOptions bottomSheetPayment = new DialogBottomSheetOptions();
                bottomSheetPayment.show(getFragmentManager(), "dialog_bottom_sheet_options");
            }
        });

        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        recView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

    private void openContactDialog(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog_contact");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Contact Details fields
        String name = "Antonio Llinares";
        String phone = "626 42 39 01";
        String email = "antonito@email.com";
        String location = "Palma de Mallorca, 07013";

        // Create and show the dialog.
        DialogFragment newFragment = new DialogContact(name, phone, email, location);
        newFragment.show(ft, "dialog_contact");
    }

}
