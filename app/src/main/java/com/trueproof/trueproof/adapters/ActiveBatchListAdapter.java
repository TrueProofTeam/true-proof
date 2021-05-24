package com.trueproof.trueproof.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.BatchUtils;

public class ActiveBatchListAdapter extends ListAdapter<Batch, ActiveBatchListAdapter.ActiveBatchViewHolder> {
    private final OnClickHandler onClickHandler;

    private ActiveBatchListAdapter(OnClickHandler onClickHandler) {
        super(BatchUtils.DIFF_CALLBACK);
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public ActiveBatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_batch_list_item, parent, false);
        return new ActiveBatchViewHolder(view, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveBatchViewHolder holder, int position) {
        final Batch batch = getItem(position);
        holder.bind(batch);
    }

    public interface OnClickHandler {
        void onClick(Batch batch);
    }

    protected class ActiveBatchViewHolder extends RecyclerView.ViewHolder {
        private Batch batch = null;
        // TODO: cache TextViews here

        public ActiveBatchViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
        }

        public void bind(Batch batch) {
            this.batch = batch;
            // TODO: set TextView text values here
        }
    }
}
