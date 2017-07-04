package me.ivanfenenko.tablebooking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ivanfenenko.tablebooking.R;
import me.ivanfenenko.tablebooking.model.Customer;

import static me.ivanfenenko.tablebooking.adapter.TableAdapter.VIEW_TYPE_ERROR;
import static me.ivanfenenko.tablebooking.adapter.TableAdapter.VIEW_TYPE_ITEM;
import static me.ivanfenenko.tablebooking.adapter.TableAdapter.VIEW_TYPE_PROGRESS;

/**
 * Created by klickrent on 29.06.17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<Customer> customerList = new ArrayList<>();
    protected ItemClickListener itemClickListener;
    protected View.OnClickListener onErrorClickListener;

    protected STATE state = STATE.IDLE;

    @Inject public CustomerAdapter() {}

    public void setCustomerList(List<Customer> customerList) {
        this.state = STATE.IDLE;
        this.customerList = customerList;
        notifyDataSetChanged();
    }

    public void setState(STATE state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setonErrorClickListener(View.OnClickListener onErrorClickListener) {
        this.onErrorClickListener = onErrorClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new ItemViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false)
                );
            case VIEW_TYPE_PROGRESS:
                return new ProgressViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false)
                );
            default:
                return new ErrorViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error, parent, false)
                );
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Customer customer = customerList.get(position);
            viewHolder.userName.setText(String.format("%s %s", customer.customerFirstName, customer.customerLastName));
            viewHolder.content.setOnClickListener((v) -> {
                if (itemClickListener != null) itemClickListener.onItemClicked(customer);
            });
        } else if (viewType == VIEW_TYPE_ERROR) {
            ErrorViewHolder errorViewHolder = (ErrorViewHolder) holder;
            errorViewHolder.itemView.setOnClickListener(onErrorClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return state.equals(STATE.IDLE) ? customerList.size() : 1;
    }


    @Override
    public int getItemViewType(int position) {
        switch (state) {
            case IDLE:
                return VIEW_TYPE_ITEM;
            case ERROR:
                return VIEW_TYPE_ERROR;
            case PROGRESS:
                return VIEW_TYPE_PROGRESS;
            default:
                return VIEW_TYPE_ERROR;
        }
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.customerName) TextView userName;
        @Bind(R.id.content) View content;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(Customer customer);
    }

}
