package com.trueproof.trueproof.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Measurement;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.models.MeasurementUtils;

public class MeasurementListAdapter extends ListAdapter<Measurement, MeasurementListAdapter.MeasurementViewHolder> {
    private final OnClickHandler onClickHandler;

    private MeasurementListAdapter(OnClickHandler onClickHandler) {
        super(MeasurementUtils.DIFF_CALLBACK);
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measurement_list_item, parent, false);
        return new MeasurementViewHolder(view, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        final Measurement measurement = getItem(position);
        holder.bind(measurement);
    }

    public interface OnClickHandler {
        void onClick(Measurement measurement);
    }

    protected class MeasurementViewHolder extends RecyclerView.ViewHolder {
        private Measurement measurement = null;
        // TODO: cache TextViews here

        public MeasurementViewHolder(@NonNull View itemView, OnClickHandler onClick) {
            super(itemView);
        }

        public void bind(Measurement measurement) {
            this.measurement = measurement;
            // TODO: set TextView text values here
        }
    }
}
