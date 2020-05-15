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
    public interface ItemClickListener {
        void onPositionClicked(int position);
    }

    private View.OnClickListener listener;
    private ArrayList<Quotation> list;
    private final ItemClickListener item_listener;

    public static class QuotationViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView image_category;
        private TextView text_subcategory;
        private TextView text_description;
        private Button button_contact;
        private WeakReference<ItemClickListener> listenerRef;

        Quotation quotation;

        public QuotationViewHolder(View itemView, ItemClickListener item_listener) {
            super(itemView);

            listenerRef = new WeakReference<>(item_listener);
            image_category = (ImageView) itemView.findViewById(R.id.image_category);
            text_subcategory = (TextView) itemView.findViewById(R.id.text_subcategory);
            text_description = (TextView) itemView.findViewById(R.id.text_description);
            button_contact = (Button) itemView.findViewById(R.id.button_contact);
            button_contact.setOnClickListener(this);
            }

        public void bindAchievement(Quotation a) {
            quotation = a;
            image_category.setImageResource(a.category);
            if(a.subcategory != null) text_subcategory.setText(a.subcategory);
            if(a.description == null || a.description.isEmpty()){
                text_description.setVisibility(View.GONE);
            }else{
                text_description.setText(a.description);
            }
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

    public QuotationAdapter(ArrayList<Quotation> list, ItemClickListener item_listener) {
        setHasStableIds(true);
        this.list = list;
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
    public void onBindViewHolder(QuotationViewHolder viewHolder, int pos) {
        Quotation item = list.get(pos);
        viewHolder.bindAchievement(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
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
