package com.habitissimo.appchallenge;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    private GridLayout grid_category;
    private ListView list_subcat;
    private LinearLayout layout_bottom_sheet;

    int step_new_quotation = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_quotation_requests, container, false);

        add_quotation = (FloatingActionButton) view.findViewById(R.id.fab_add);
        layout_bottom_sheet = (LinearLayout) view.findViewById(R.id.layout_bottom_sheet);
        View bottomSheet = view.findViewById(R.id.bottom_sheet_new_quotation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        grid_category = (GridLayout) view.findViewById(R.id.grid_category);
        list_subcat = (ListView) view.findViewById(R.id.list_subcategory);

        ArrayList<Integer> cat_images = new ArrayList<>();
        cat_images.add(R.drawable.cat_construccion);
        cat_images.add(R.drawable.cat_reformas);
        cat_images.add(R.drawable.cat_mudanzas);
        cat_images.add(R.drawable.cat_tecnicos);
        cat_images.add(R.drawable.cat_obras_menores);
        cat_images.add(R.drawable.cat_mantenimiento);
        cat_images.add(R.drawable.cat_instaladores);
        cat_images.add(R.drawable.cat_tiendas);

        ArrayList<Integer> cat_texts = new ArrayList<>();
        cat_texts.add(R.string.cat_construccion);
        cat_texts.add(R.string.cat_reformas);
        cat_texts.add(R.string.cat_mudanzas);
        cat_texts.add(R.string.cat_tecnicos);
        cat_texts.add(R.string.cat_obras_menores);
        cat_texts.add(R.string.cat_mantenimiento);
        cat_texts.add(R.string.cat_instaladores);
        cat_texts.add(R.string.cat_tiendas);

        for(int i=0; i<cat_images.size(); i++){
            final View category = inflater.inflate(R.layout.item_category, grid_category, false);
            category.setId(View.generateViewId());
            ImageView cat_image = (ImageView) category.findViewById(R.id.image_category);
            cat_image.setImageResource(cat_images.get(i));
            TextView cat_text = (TextView) category.findViewById(R.id.text_category);
            cat_text.setText(cat_texts.get(i));
            grid_category.addView(category);
            final String selected_category = getString(cat_texts.get(i));

            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), selected_category, Toast.LENGTH_SHORT).show();
                    grid_category.setVisibility(View.GONE);
                    list_subcat.setVisibility(View.VISIBLE);
                }
            });
        }

        // Populate List View
        final String[] subcats = new String[]{"Pintores","Tapiceros","Cerrajeros"};
        list_subcat.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, subcats));

        // Modify scroll events to prevent Bottom Sheet from NestedScrollView to collapse
        list_subcat.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        list_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Toast.makeText(getContext(), subcats[position], Toast.LENGTH_SHORT).show();
            }
        });

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
                DialogBottomSheetOptions bottomSheetOptions = new DialogBottomSheetOptions();
                Bundle args = new Bundle();
                args.putSerializable("position", recView.getChildAdapterPosition(v));
                bottomSheetOptions.setArguments(args);
                bottomSheetOptions.show(getFragmentManager(), "dialog_bottom_sheet_options");
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
