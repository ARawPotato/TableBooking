package me.ivanfenenko.tablebooking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ivanfenenko.tablebooking.R;
import me.ivanfenenko.tablebooking.model.Table;

/**
 * Created by klickrent on 29.06.17.
 */

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final static int VIEW_TYPE_ITEM = 1;
    protected final static int VIEW_TYPE_PROGRESS = 2;
    protected final static int VIEW_TYPE_ERROR = 3;

    protected STATE state = STATE.IDLE;

    protected List<Table> routeList = new ArrayList<>();
    protected ItemClickListener itemClickListener;
    protected View.OnClickListener onErrorClickListener;

    @Inject public TableAdapter() {}

    public void setData(List<Table> routeList) {
        this.state = STATE.IDLE;
        this.routeList = routeList;
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false)
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
            Table table = routeList.get(position);
            viewHolder.tableName.setText(String.format("Table #%d", table.tableId));
            viewHolder.tableImage.setColorFilter(getContext(viewHolder).getResources().getColor(
                    table.isAvailable ? R.color.colorTableNotBooked : R.color.colorTableBooked)
            );
            viewHolder.content.setOnClickListener((v) -> {
                if (itemClickListener != null) itemClickListener.onItemClicked(table);
            });
        }  else if (viewType == VIEW_TYPE_ERROR) {
            ErrorViewHolder errorViewHolder = (ErrorViewHolder) holder;
            errorViewHolder.itemView.setOnClickListener(onErrorClickListener);
        }
    }

    public Context getContext(RecyclerView.ViewHolder v) {
        return v.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return state.equals(STATE.IDLE) ? routeList.size() : 1;
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

        @Bind(R.id.tableName) TextView tableName;
        @Bind(R.id.tableImage) ImageView tableImage;
        @Bind(R.id.content) View content;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(Table table);
    }

}
