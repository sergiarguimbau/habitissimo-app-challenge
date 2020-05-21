package com.habitissimo.appchallenge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class QuotationAdapter
        extends RecyclerView.Adapter<QuotationAdapter.QuotationViewHolder>
        implements View.OnClickListener
{
    // Interface for Button Contact clicked
    public interface ItemClickListener {
        void onContactClicked(int position);
    }

    private View.OnClickListener listener;
    private ArrayList<Quotation> quotation_list;
    private final ItemClickListener item_listener;

    public static class QuotationViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Declare Views for Quotation Item
        private ImageView image_category;
        private TextView text_subcategory;
        private TextView text_description;
        private Button button_contact;
        private WeakReference<ItemClickListener> listenerRef;

        public QuotationViewHolder(View itemView, ItemClickListener item_listener) {
            super(itemView);

            // Find views
            listenerRef = new WeakReference<>(item_listener);
            image_category = (ImageView) itemView.findViewById(R.id.image_category);
            text_subcategory = (TextView) itemView.findViewById(R.id.text_subcategory);
            text_description = (TextView) itemView.findViewById(R.id.text_description);
            button_contact = (Button) itemView.findViewById(R.id.button_contact);
            button_contact.setOnClickListener(this);
        }

        public void bindAchievement(Quotation quot) {
            // Set Texts/Images to Quotation item
            image_category.setImageResource(quot.getCategory().getImage());
            if(quot.getSubcategory() != null) text_subcategory.setText(quot.getSubcategory().getName());
            if(quot.getDescription() == null || quot.getDescription().isEmpty()){
                text_description.setVisibility(View.GONE);
            }else{
                text_description.setVisibility(View.VISIBLE);
                text_description.setText(quot.getDescription());
            }
        }

        // onClick Listener for Button Contact
        @Override
        public void onClick(View v) {
            listenerRef.get().onContactClicked(getAdapterPosition());
        }
    }

    public QuotationAdapter(ArrayList<Quotation> list, ItemClickListener item_listener) {
        setHasStableIds(true);
        this.quotation_list = list;
        this.item_listener = item_listener;
    }

    @Override
    public QuotationViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_quotation, viewGroup, false);

        itemView.setOnClickListener(this);

        QuotationViewHolder quotationViewHolder = new QuotationViewHolder(itemView, item_listener);
        return quotationViewHolder;
    }

    @Override
    public void onBindViewHolder(QuotationViewHolder viewHolder, int position) {
        Quotation item = quotation_list.get(position);
        viewHolder.bindAchievement(item);
    }

    @Override
    public int getItemCount() {
        return quotation_list.size();

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) listener.onClick(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
