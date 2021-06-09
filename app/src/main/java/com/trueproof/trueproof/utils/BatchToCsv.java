package com.trueproof.trueproof.utils;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchToCsv {

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    CSVWriter csvWriter = new CSVWriter(new FileWriter(baseDir));

    public BatchToCsv() throws IOException {
    }

    public void convertToCsv (Batch batch) throws IOException {
//        still need to use intent to save file or email
        List<Measurement> batchMeasurements = batch.getMeasurements();
        csvWriter.writeNext(new String[] {"Type", "Number", "Identifier", "Created At", "Updated At"});
        csvWriter.writeNext(new String [] {batch.getType(), String.valueOf(batch.getBatchNumber()), batch.getBatchIdentifier()});
        csvWriter.writeNext(new String [] {""});
        csvWriter.writeNext(new String [] {"True Proof", "Temperature", "Temp. Correction", "Hydrometer", "Hydro Correction"});
        for (Measurement m : batchMeasurements
             ) {
            csvWriter.writeNext(new String [] {String.valueOf(m.getTrueProof()), String.valueOf(m.getTemperature()),
                    String.valueOf(m.getTemperatureCorrection()), String.valueOf(m.getHydrometer()), String.valueOf(m.getHydrometerCorrection())});
        }
        csvWriter.close();
    }
}
