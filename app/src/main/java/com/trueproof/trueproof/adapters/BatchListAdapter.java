package com.trueproof.trueproof.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.BatchUtils;

public class BatchListAdapter extends ListAdapter<Batch, BatchListAdapter.BatchViewHolder> {
    private final OnClickHandler onClickHandler;

    public BatchListAdapter(OnClickHandler onClickHandler) {
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
        private Batch batch;
        private TextView batchType;
        private TextView batchNumber;
        private TextView trueProof;
        private TextView completedAtTime;

        public BatchViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
            itemView.setOnClickListener(v -> onClick.onClick(this.batch));
            batchType = itemView.findViewById(R.id.batchTypeBatchListItem);
            batchNumber = itemView.findViewById(R.id.batchNumberBatchListItem);
            trueProof = itemView.findViewById(R.id.measuredProofBatchListItem);
            completedAtTime = itemView.findViewById(R.id.completedAtTimeBatchListItem);
        }

        public void bind(Batch batch) {
            this.batch = batch;
            batchType.setText(batch.getType());
            batchNumber.setText(String.format("Batch no. %d", batch.getBatchNumber()));
            trueProof.setText(String.format("%.1f proof", batch.getTrueProof()));
            // TODO Date time formatting logic for this
            completedAtTime.setText("placeholder");
        }
    }
}
