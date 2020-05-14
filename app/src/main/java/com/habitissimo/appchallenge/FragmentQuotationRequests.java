package com.habitissimo.appchallenge;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

    private RecyclerView recView_quotation;
    private ArrayList<Quotation> quotations;

    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab_add_quotation;

    private GridLayout grid_category;
    private ListView list_subcat;

    private LinearLayout layout_bottom_sheet;
    private ImageView image_back;
    private TextView text_select_category;
    private TextView text_select_subcategory;
    private TextView text_category_selected;
    private TextView text_subcategory_selected;
    private TextView text_put_description;
    private TextView text_put_location;
    private TextView text_location_done;
    private EditText editText_description;
    private AutoCompleteTextView autoText_location;
    private Button button_send;

    int step_add_quotation = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_quotation_requests, container, false);

        recView_quotation = (RecyclerView) view.findViewById(R.id.rec_view_quotation);
        fab_add_quotation = (FloatingActionButton) view.findViewById(R.id.fab_add);
        layout_bottom_sheet = (LinearLayout) view.findViewById(R.id.layout_bottom_sheet);
        View bottomSheet = view.findViewById(R.id.bottom_sheet_add_quotation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        grid_category = (GridLayout) view.findViewById(R.id.grid_category);
        list_subcat = (ListView) view.findViewById(R.id.list_subcategory);

        image_back = (ImageView) view.findViewById(R.id.image_back);
        text_select_category = (TextView) view.findViewById(R.id.text_select_category);
        text_select_subcategory = (TextView) view.findViewById(R.id.text_select_subcategory);
        text_category_selected = (TextView) view.findViewById(R.id.text_category_selected);
        text_subcategory_selected = (TextView) view.findViewById(R.id.text_subcategory_selected);
        text_put_description = (TextView) view.findViewById(R.id.text_put_description);
        text_put_location = (TextView) view.findViewById(R.id.text_put_location);
        text_location_done = (TextView) view.findViewById(R.id.text_location_done);
        editText_description = (EditText) view.findViewById(R.id.edittext_description);
        autoText_location = (AutoCompleteTextView) view.findViewById(R.id.autotext_location);
        button_send = (Button) view.findViewById(R.id.button_send);

        // Category images
        ArrayList<Integer> cat_images = new ArrayList<>();
        cat_images.add(R.drawable.cat_construccion);
        cat_images.add(R.drawable.cat_reformas);
        cat_images.add(R.drawable.cat_mudanzas);
        cat_images.add(R.drawable.cat_tecnicos);
        cat_images.add(R.drawable.cat_obras_menores);
        cat_images.add(R.drawable.cat_mantenimiento);
        cat_images.add(R.drawable.cat_instaladores);
        cat_images.add(R.drawable.cat_tiendas);

        // Category names (must be same order as cat_images)
        ArrayList<Integer> cat_texts = new ArrayList<>();
        cat_texts.add(R.string.cat_construccion);
        cat_texts.add(R.string.cat_reformas);
        cat_texts.add(R.string.cat_mudanzas);
        cat_texts.add(R.string.cat_tecnicos);
        cat_texts.add(R.string.cat_obras_menores);
        cat_texts.add(R.string.cat_mantenimiento);
        cat_texts.add(R.string.cat_instaladores);
        cat_texts.add(R.string.cat_tiendas);

        // Inflate GridLayout with Category items
        for(int i=0; i<cat_images.size(); i++){
            final View category = inflater.inflate(R.layout.item_category, grid_category, false);
            category.setId(View.generateViewId());
            ImageView cat_image = (ImageView) category.findViewById(R.id.image_category);
            cat_image.setImageResource(cat_images.get(i));
            TextView cat_text = (TextView) category.findViewById(R.id.text_category);
            cat_text.setText(cat_texts.get(i));
            grid_category.addView(category);
            final String selected_category = getString(cat_texts.get(i));

            // Category Item selected
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    go_forward_add_quotation(selected_category);
                }
            });
        }

        // Populate ListView with Subcategories
        final String[] subcats = new String[]{"Pintores","Tapiceros","Cerrajeros"};
        list_subcat.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, subcats));

        // Populate AutoCompleteTextView with Locations
        final String[] locations = new String[]{ "Palma de Mallorca, 07013", "Barcelona, 08112", "Madrid, 28451", "Murcia, 30110"};
        autoText_location.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, locations));
        autoText_location.setThreshold(1);

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

        // Subcategory Item selected
        list_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                go_forward_add_quotation(subcats[position]);
            }
        });

        // Create contact examples
        final Contact contact_antonio = new Contact("Antonio Llinares", "626 42 39 01", "antonito@email.com", "Palma de Mallorca, 07013");
        Contact contact_maria = new Contact("María Salvado", "692 39 29 10", "maria@email.com", "Murcia, 30110");

        // Create quotation examples
        quotations = new ArrayList<Quotation>();
        quotations.add(new Quotation(R.drawable.cat_construccion, "Construir muro", "Desearía construir un muro en el patio de mi casa", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_reformas,"Reparación tubería", "Quiero reparar el baño que el cuaarto piso tiene goteras. Necesito ayuda urgente", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_mudanzas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_mantenimiento,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_tecnicos,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));
        quotations.add(new Quotation(R.drawable.cat_tiendas,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_instaladores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_antonio));
        quotations.add(new Quotation(R.drawable.cat_obras_menores,"Traslado muebles", "Quiero trasladar mi mueble de la tienda a otro lado", contact_maria));

        // Button Contact from RecyclerView Item selected
        final QuotationAdapter quotationAdapter = new QuotationAdapter(quotations, new QuotationAdapter.ItemClickListener() {
            @Override
            public void onPositionClicked(int position) {
                openContactDialog(quotations.get(position).contact);
            }
        });

        // RecyclerView Item selected
        quotationAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBottomSheetOptions bottomSheetOptions = new DialogBottomSheetOptions();
                Bundle args = new Bundle();
                args.putSerializable("position", recView_quotation.getChildAdapterPosition(v));
                bottomSheetOptions.setArguments(args);
                bottomSheetOptions.show(getFragmentManager(), "dialog_bottom_sheet_options");
            }
        });

        // Populate RecyclerView Quotations
        recView_quotation.setHasFixedSize(false);
        recView_quotation.setAdapter(quotationAdapter);
        recView_quotation.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        // Floating Action Button add new quotation clicked
        fab_add_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // Collapsed Bottom Sheet new quotation clicked
        layout_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // Step back inside New Quotation
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back_add_quotation();
            }
        });

        // Notify user about max length reached
        editText_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() == 500){
                    Toast.makeText(getContext(), getString(R.string.max_edittext), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });

        autoText_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                go_forward_add_quotation(autoText_location.getText().toString());
            }
        });

        // Send Quotation button clicked
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Send clicked", Toast.LENGTH_SHORT).show();
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

    private void go_back_add_quotation(){
        step_add_quotation--;
        update_add_quotation();
    }

    private void go_forward_add_quotation(String param){
        step_add_quotation++;
        update_add_quotation();

        switch (step_add_quotation){
            case 1:
                text_category_selected.setText(param);
                break;
            case 2:
                text_subcategory_selected.setText(param);
                break;
            case 3:
                text_location_done.setText(param);
                break;
            default:
                break;
        }
    }

    private void update_add_quotation(){
        switch (step_add_quotation){
            case 0:
                image_back.setVisibility(View.INVISIBLE);
                text_category_selected.setVisibility(View.GONE);
                text_select_subcategory.setVisibility(View.GONE);
                text_subcategory_selected.setVisibility(View.GONE);
                text_put_description.setVisibility(View.GONE);
                text_put_location.setVisibility(View.GONE);
                text_location_done.setVisibility(View.GONE);
                grid_category.setVisibility(View.VISIBLE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 1:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.GONE);
                text_put_description.setVisibility(View.GONE);
                text_put_location.setVisibility(View.GONE);
                text_location_done.setVisibility(View.GONE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.VISIBLE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 2:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.VISIBLE);
                text_put_description.setVisibility(View.GONE);
                text_put_location.setVisibility(View.VISIBLE);
                text_location_done.setVisibility(View.GONE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.VISIBLE);
                editText_description.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 3:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.VISIBLE);
                text_put_description.setVisibility(View.VISIBLE);
                text_put_location.setVisibility(View.VISIBLE);
                text_location_done.setVisibility(View.VISIBLE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.VISIBLE);
                button_send.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}
