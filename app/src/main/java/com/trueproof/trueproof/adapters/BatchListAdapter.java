package com.trueproof.trueproof.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.BatchUtils;

public class BatchListAdapter extends ListAdapter<Batch, BatchListAdapter.BatchViewHolder> {
    private final OnClickHandler onClickHandler;

    private BatchListAdapter(OnClickHandler onClickHandler) {
        super(BatchUtils.DIFF_CALLBACK);
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batch_list_item, parent, false);
        return new BatchViewHolder(view, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {
        final Batch batch = getItem(position);
        holder.bind(batch);
    }

    public interface OnClickHandler {
        void onClick(Batch batch);
    }

    protected class BatchViewHolder extends RecyclerView.ViewHolder {
        private Batch batch = null;
        // TODO: cache TextViews here

        public BatchViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
        }

        public void bind(Batch batch) {
            this.batch = batch;
            // TODO: set TextView text values here
        }
    }
}
