package com.habitissimo.appchallenge;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

        quotations.add(new Quotation(R.drawable.construccion, "Construir muro", "Desearía construir un muro en el patio de mi casa"));
        quotations.add(new Quotation(R.drawable.reformas,"Reparación tubería", "Quiero reparar el baño que el cuaarto piso tiene goteras. Necesito ayuda urgente" ));
        quotations.add(new Quotation(R.drawable.mudanzas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado"));

        recView = (RecyclerView) view.findViewById(R.id.rec_view_quotation);
        recView.setHasFixedSize(false);

        final QuotationAdapter adaptador = new QuotationAdapter(quotations, new QuotationAdapter.ItemClickListener() {
            @Override
            public void onPositionClicked(int position) {
                Toast.makeText(getContext(), "Pulsado Contacto " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Pulsado Presupuesto " + recView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();
            }
        });

        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        recView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

}
