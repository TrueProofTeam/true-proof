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

public class ActiveBatchListAdapter extends ListAdapter<Batch, ActiveBatchListAdapter.ActiveBatchViewHolder> {
    private final OnClickHandler onClickHandler;

    public ActiveBatchListAdapter(OnClickHandler onClickHandler) {
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
        private Batch batch;
        private TextView batchType;
        private TextView batchNumber;
        private TextView trueProof;
        private TextView startedAtTime;
        private TextView lastMeasuredTime;

        public ActiveBatchViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
            itemView.setOnClickListener(v -> onClick.onClick(this.batch));
            batchType = itemView.findViewById(R.id.batchTypeBatchListItem);
            batchNumber = itemView.findViewById(R.id.batchNumberBatchListItem);
            trueProof = itemView.findViewById(R.id.measuredProofActiveBatchListItem);
            startedAtTime = itemView.findViewById(R.id.startedAtTimeActiveBatchListItem);
            lastMeasuredTime = itemView.findViewById(R.id.lastMeasuredTimeActiveBatchListItem);
        }

        public void bind(Batch batch) {
            this.batch = batch;
            batchType.setText(batch.getType());
            batchNumber.setText(String.format("Batch no. %d", batch.getBatchNumber()));
            // TODO logic for retrieving most recent measurement
            final double lastMeasuredTrueProof = 110.1;
            trueProof.setText(String.format("%.1f proof", lastMeasuredTrueProof));
            // TODO Date time formatting for these
            startedAtTime.setText("placeholder");
            lastMeasuredTime.setText("placeholder");
        }
    }
}
