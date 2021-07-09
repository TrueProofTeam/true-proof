package com.trueproof.trueproof.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.amplifyframework.datastore.generated.model.Batch;
import com.amplifyframework.datastore.generated.model.Measurement;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchToCsv {

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    CSVWriter csvWriter;

    public BatchToCsv() throws IOException {
    }

    public void convertToCsv (Batch batch) throws IOException {
        String path = baseDir + File.separator + "BatchData.csv";
        File f = new File(path);
        if (f.exists()&& f.isDirectory()){
            csvWriter= new CSVWriter(new FileWriter(path, true));
        } else{
            csvWriter= new CSVWriter(new FileWriter(path));
        }

//        still need to use intent to save file or email
        List<Measurement> batchMeasurements = batch.getMeasurements();
        csvWriter.writeNext(new String[] {"Type", "Number", "Identifier", "Created At", "Updated At"});
        csvWriter.writeNext(new String [] {batch.getType(), String.valueOf(batch.getBatchNumber()), batch.getBatchIdentifier(), batch.getCreatedAt().toString(), batch.getUpdatedAt().toString()});
        csvWriter.writeNext(new String [] {""});
        csvWriter.writeNext(new String [] {"True Proof", "Temperature", "Temp. Correction", "Hydrometer", "Hydro Correction", "Created At"});
        for (Measurement m : batchMeasurements
             ) {
            csvWriter.writeNext(new String [] {String.valueOf(m.getTrueProof()), String.valueOf(m.getTemperature()),
                    String.valueOf(m.getTemperatureCorrection()), String.valueOf(m.getHydrometer()), String.valueOf(m.getHydrometerCorrection())
                    , m.getCreatedAt().toString()});
        }
        csvWriter.close();
    }

}
