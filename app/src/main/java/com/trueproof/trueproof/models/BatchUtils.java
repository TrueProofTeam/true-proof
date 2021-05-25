package com.trueproof.trueproof.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.amplifyframework.datastore.generated.model.Batch;

public class BatchUtils {
    public static final DiffUtil.ItemCallback<Batch> DIFF_CALLBACK = new DiffUtil.ItemCallback<Batch>() {
        @Override
        public boolean areItemsTheSame(@NonNull Batch oldItem, @NonNull Batch newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Batch oldItem, @NonNull Batch newItem) {
            return oldItem.equals(newItem);
        }
    };

    @SuppressLint("DefaultLocale")
    public static String batchToString(Batch batch) {
        return String.format("%s batch #%d", batch.getType(), batch.getBatchNumber());
    }
}
