package com.trueproof.trueproof.adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Batch;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.BatchUtils;
import com.trueproof.trueproof.utils.AWSDateTime;
import com.trueproof.trueproof.utils.AWSDateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActiveBatchListAdapter extends ListAdapter<Batch, ActiveBatchListAdapter.ActiveBatchViewHolder> {
    private static final String TAG = "ActiveBatch/LA";
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
        private TextView lastMeasuredProof;
        private TextView startedAtTime;
        private TextView lastMeasuredTime;

        public ActiveBatchViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
            itemView.setOnClickListener(v -> onClick.onClick(this.batch));
            batchType = itemView.findViewById(R.id.batchTypeBatchListItem);
            batchNumber = itemView.findViewById(R.id.batchNumberBatchListItem);
            lastMeasuredProof = itemView.findViewById(R.id.measuredProofActiveBatchListItem);
            startedAtTime = itemView.findViewById(R.id.startedAtTimeActiveBatchListItem);
            lastMeasuredTime = itemView.findViewById(R.id.lastMeasuredTimeActiveBatchListItem);
        }


        public void bind(Batch batch) {
            this.batch = batch;
            batchType.setText(batch.getType());
            batchNumber.setText(String.format("Batch no. %d", batch.getBatchNumber()));

            String lastMeasuredProofString = batch.getMeasurements()
                    .stream()
                    .max(AWSDateTime.measurementByDate)
                    .map(measurement -> {
                        Log.i(TAG, "bind: measurement.getTrueProof()" + measurement.getTrueProof());
                        return measurement.getTrueProof();
                    })
                    .map(trueProof -> String.format("%.1f proof", trueProof))
                    .orElseGet(() -> "");

            lastMeasuredProof.setText(lastMeasuredProofString);

            startedAtTime.setText(
                    AWSDateTimeFormatter.ofPattern("M/d hh:mm:ss")
                            .format(batch.getCreatedAt())
            );

            AWSDateTimeFormatter lastMeasuredFormatter = AWSDateTimeFormatter.ofPattern("M/d hh:mm:ss");

            String lastMeasuredTimeString = batch.getMeasurements()
                    .stream()
                    .map(measurement -> measurement.getCreatedAt())
                    .max(AWSDateTime.byDate)
                    .map(lastMeasuredFormatter::format)
                    .orElseGet(() -> "No measurements yet");

            lastMeasuredTime.setText(lastMeasuredTimeString);
        }
    }
}
