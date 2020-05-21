package com.habitissimo.appchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.List;

public class QuotationActivity extends AppCompatActivity implements DialogBottomSheetOptions.BottomSheetOptionsListener {

    // Declare Views
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
    private TextView text_description_done;
    private TextView text_put_location;
    private TextView text_location_done;
    private TextView text_put_contact;
    private TextView text_name;
    private TextView text_phone;
    private TextView text_email;
    private TextView text_location;

    private EditText editText_description;
    private AutoCompleteTextView autoText_location;
    private LinearLayout layout_contact;
    private Button button_send;

    // Declare variables
    int step_add_quotation = 0;
    final int LAST_STEP_ADD_QUOTATION = 4;
    HabitissimoAPI habitissimoAPI;
    final String TAG_API = "HabitissimoAPI";
    List<Category> categories = new ArrayList<>();
    List<Category> subcategories = new ArrayList<>();
    Category category_selected;
    Category subcategory_selected;
    QuotationAdapter quotationAdapter;

    @SuppressLint("ClickableViewAccessibility") // Supress Warning ListView setOnTouchListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        setTitle(getString(R.string.quotation_requests));

        // Main Layout Views
        recView_quotation = (RecyclerView) findViewById(R.id.rec_view_quotation);
        fab_add_quotation = (FloatingActionButton) findViewById(R.id.fab_add);
        layout_bottom_sheet = (LinearLayout) findViewById(R.id.layout_bottom_sheet);
        View bottomSheet = findViewById(R.id.bottom_sheet_add_quotation);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Bottom Sheet Layout Views
        image_back = (ImageView) findViewById(R.id.image_back);
        grid_category = (GridLayout) findViewById(R.id.grid_category);
        list_subcat = (ListView) findViewById(R.id.list_subcategory);
        editText_description = (EditText) findViewById(R.id.edittext_description);
        autoText_location = (AutoCompleteTextView) findViewById(R.id.autotext_location);
        layout_contact = (LinearLayout) findViewById(R.id.layout_contact);
        button_send = (Button) findViewById(R.id.button_send);

        text_select_category = (TextView) findViewById(R.id.text_select_category);
        text_select_subcategory = (TextView) findViewById(R.id.text_select_subcategory);
        text_category_selected = (TextView) findViewById(R.id.text_category_selected);
        text_subcategory_selected = (TextView) findViewById(R.id.text_subcategory_selected);
        text_put_description = (TextView) findViewById(R.id.text_put_description);
        text_description_done = (TextView) findViewById(R.id.text_description_done);
        text_put_location = (TextView) findViewById(R.id.text_put_location);
        text_location_done = (TextView) findViewById(R.id.text_location_done);
        text_put_contact = (TextView) findViewById(R.id.text_put_contact);
        text_name = (TextView) findViewById(R.id.text_name);
        text_phone = (TextView) findViewById(R.id.text_phone);
        text_email = (TextView) findViewById(R.id.text_email);
        text_location = (TextView) findViewById(R.id.text_location);

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.habitissimo.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        habitissimoAPI = retrofit.create(HabitissimoAPI.class);

        // Read from REST API the Category items
        habitissimoAPICallCategories();

        // Read from REST API the Locations
        habitissimoAPICallLocations();

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
                subcategory_selected = subcategories.get(position);
                go_forward_add_quotation(subcategory_selected.getName());
            }
        });


        // Create quotation examples
        quotations = new ArrayList<Quotation>();

        // Button Contact from RecyclerView Item selected
        quotationAdapter = new QuotationAdapter(quotations, new QuotationAdapter.ItemClickListener() {
            @Override
            public void onContactClicked(int position) {
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
                bottomSheetOptions.show(getSupportFragmentManager(), "dialog_bottom_sheet_options");
            }
        });

        // Populate RecyclerView Quotations
        recView_quotation.setHasFixedSize(true);
        recView_quotation.setAdapter(quotationAdapter);
        recView_quotation.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));

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

        // Step back inside Add Quotation
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
                    Toast.makeText(getApplicationContext(), getString(R.string.max_edittext), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });

        // Location selected
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
                if(step_add_quotation == LAST_STEP_ADD_QUOTATION){
                    reset_add_quotation();
                    Contact contact = new Contact(text_name.getText().toString(), text_phone.getText().toString(), text_email.getText().toString(), text_location.getText().toString());
                    quotations.add(new Quotation(category_selected, subcategory_selected, text_description_done.getText().toString(), contact));
                    quotationAdapter.notifyDataSetChanged();
                }else{
                    openFillContactDialog();
                }
            }
        });

    }

    @Override
    public void onOptionMethodClicked(int position, String option) {

        if (option.equals(getString(R.string.NT_options_share))) {
            // Share quotation as plain text
            String share_message = quotations.get(position).printQuotation(getApplicationContext());
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, share_message);
            startActivity(Intent.createChooser(share, getString(R.string.share_quotation)));

        } else if (option.equals(getString(R.string.NT_options_edit))) {
            Toast.makeText(getApplicationContext(), "Edit clicked " + position, Toast.LENGTH_SHORT).show();
        } else if (option.equals(getString(R.string.NT_options_delete))) {
            // Remove quotation item (without animations)
            quotations.remove(position);
            quotationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        // Close app if Bottom Sheet is not Expanded
        if(step_add_quotation == 0 && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else if(step_add_quotation > 0 && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            go_back_add_quotation();
        }else{
            super.onBackPressed();
        }
    }

    private void habitissimoAPICallCategories(){
        Call<List<Category>> call = habitissimoAPI.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                // Response not successful
                if (!response.isSuccessful()) {
                    Log.e(TAG_API, "Response Code: " + response.code());
                    return;
                }

                // Response is successful
                categories = response.body();
                for (Category category : categories) {

                    // Print JSON Object
                    Log.d(TAG_API, "Category ID: " + category.getId() + ", name: " + category.getResName());

                    // Build category resource name to find image and text resources
                    String res_name = "cat_" + category.getResName().replace("-","_");
                    int image_id = getResources().getIdentifier(res_name, "drawable", getPackageName());

                    // Validate resource IDs
                    if(image_id != 0){

                        category.setImage(image_id);

                        // Create new item category
                        final View view_category = getLayoutInflater().inflate(R.layout.item_category, grid_category, false);
                        view_category.setId(View.generateViewId());

                        // Set Text and Image (validated resource IDs)
                        ImageView cat_image = (ImageView) view_category.findViewById(R.id.image_category);
                        cat_image.setImageResource(category.getImage());
                        TextView cat_text = (TextView) view_category.findViewById(R.id.text_category);
                        cat_text.setText(category.getName());

                        // Add View to parent Grid Layout
                        grid_category.addView(view_category);

                        // Category Item selected
                        view_category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Populate ListView with Subcategories
                                category_selected = category;
                                habitissimoAPICallSubCategories(category_selected);
                            }
                        });
                    }else{
                        Log.e(TAG_API, "Category resource name '" + res_name + "' not found");
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Any Retrofit kind of failure
                Log.e(TAG_API, t.getMessage());
            }
        });
    }

    private void habitissimoAPICallSubCategories(Category category){
        Call<List<Category>> call = habitissimoAPI.getSubcategories(category.getId());
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                // Response not successful
                if (!response.isSuccessful()) {
                    Log.e(TAG_API, "Response Code: " + response.code());
                    return;
                }

                List<String> subcat_names = new ArrayList<>();

                // Response is successful
                subcategories = response.body();
                for (Category subcategory : subcategories) {

                    // Print JSON Object
                    Log.d(TAG_API, "Subcategory ID: " + subcategory.getId() + ", name: " + subcategory.getResName());

                    subcat_names.add(subcategory.getName());
                }

                // Populate ListView with Subcategories
                list_subcat.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, subcat_names));
                go_forward_add_quotation(category.getName());
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Any Retrofit kind of failure
                Log.e(TAG_API, t.getMessage());
            }
        });
    }

    private void habitissimoAPICallLocations(){
        Call<List<Location>> call = habitissimoAPI.getLocations();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                // Response not successful
                if (!response.isSuccessful()) {
                    Log.e(TAG_API, "Response Code: " + response.code());
                    return;
                }

                List<String> location_list = new ArrayList<>();

                // Response is successful
                List<Location> locations = response.body();
                for (Location location : locations) {

                    // Print JSON Object
                    Log.d(TAG_API, "Location ID: " + location.getId() + ", name: " + location.getName() + ", zip: " + location.getZip());

                    location_list.add(location.getName().concat(", ").concat(location.getZip()));
                }

                // Populate AutoCompleteTextView with Locations
                autoText_location.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_dropdown_item_1line, location_list));
                autoText_location.setThreshold(1);
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // Any Retrofit kind of failure
                Log.e(TAG_API, t.getMessage());
            }
        });
    }

    private void openContactDialog(Contact contact){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog_contact");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new DialogContact(contact);
        newFragment.show(ft, "dialog_contact");
    }

    private void openFillContactDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog_fill_contact");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new DialogFillContact();
        newFragment.show(ft, "dialog_fill_contact");
    }

    public void setFillContact(String name, String phone, String email){
        text_name.setText(name);
        text_phone.setText(phone);
        text_email.setText(email);
        text_location.setText(text_location_done.getText());
        go_forward_add_quotation(editText_description.getText().toString());
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
            case 4:
                text_description_done.setText(param);
                break;
            default:
                break;
        }
    }

    private void reset_add_quotation(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        step_add_quotation = 0;
        autoText_location.getText().clear();
        editText_description.getText().clear();
        update_add_quotation();
    }

    private void update_add_quotation(){
        switch (step_add_quotation){
            case 0:
                image_back.setVisibility(View.INVISIBLE);
                text_category_selected.setVisibility(View.GONE);
                text_select_subcategory.setVisibility(View.GONE);
                text_subcategory_selected.setVisibility(View.GONE);
                text_put_description.setVisibility(View.GONE);
                text_description_done.setVisibility(View.GONE);
                text_put_location.setVisibility(View.GONE);
                text_location_done.setVisibility(View.GONE);
                text_put_contact.setVisibility(View.GONE);
                grid_category.setVisibility(View.VISIBLE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.GONE);
                layout_contact.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 1:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.GONE);
                text_put_description.setVisibility(View.GONE);
                text_description_done.setVisibility(View.GONE);
                text_put_location.setVisibility(View.GONE);
                text_location_done.setVisibility(View.GONE);
                text_put_contact.setVisibility(View.GONE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.VISIBLE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.GONE);
                layout_contact.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 2:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.VISIBLE);
                text_put_description.setVisibility(View.GONE);
                text_description_done.setVisibility(View.GONE);
                text_put_location.setVisibility(View.VISIBLE);
                text_location_done.setVisibility(View.GONE);
                text_put_contact.setVisibility(View.GONE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.VISIBLE);
                editText_description.setVisibility(View.GONE);
                layout_contact.setVisibility(View.GONE);
                button_send.setVisibility(View.GONE);
                break;
            case 3:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.VISIBLE);
                text_put_description.setVisibility(View.VISIBLE);
                text_description_done.setVisibility(View.GONE);
                text_put_location.setVisibility(View.VISIBLE);
                text_location_done.setVisibility(View.VISIBLE);
                text_put_contact.setVisibility(View.GONE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.VISIBLE);
                layout_contact.setVisibility(View.GONE);
                button_send.setVisibility(View.VISIBLE);
                button_send.setText(R.string.next);
                break;
            case 4:
                image_back.setVisibility(View.VISIBLE);
                text_category_selected.setVisibility(View.VISIBLE);
                text_select_subcategory.setVisibility(View.VISIBLE);
                text_subcategory_selected.setVisibility(View.VISIBLE);
                text_put_description.setVisibility(View.VISIBLE);
                text_description_done.setVisibility(View.VISIBLE);
                text_put_location.setVisibility(View.GONE);
                text_location_done.setVisibility(View.GONE);
                text_put_contact.setVisibility(View.VISIBLE);
                grid_category.setVisibility(View.GONE);
                list_subcat.setVisibility(View.GONE);
                autoText_location.setVisibility(View.GONE);
                editText_description.setVisibility(View.GONE);
                layout_contact.setVisibility(View.VISIBLE);
                button_send.setVisibility(View.VISIBLE);
                button_send.setText(R.string.send);
                break;
            default:
                break;
        }
    }
}
