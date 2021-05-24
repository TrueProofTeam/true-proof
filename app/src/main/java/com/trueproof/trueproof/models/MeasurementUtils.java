package com.trueproof.trueproof.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.amplifyframework.datastore.generated.model.Measurement;

public class MeasurementUtils {
    public static final DiffUtil.ItemCallback<Measurement> DIFF_CALLBACK = new DiffUtil.ItemCallback<Measurement>() {
        @Override
        public boolean areItemsTheSame(@NonNull Measurement oldItem, @NonNull Measurement newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Measurement oldItem, @NonNull Measurement newItem) {
            return oldItem.equals(newItem);
        }
    };
}
