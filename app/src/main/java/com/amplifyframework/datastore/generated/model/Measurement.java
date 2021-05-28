package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Measurement type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Measurements")
@Index(name = "byBatch", fields = {"batchID"})
public final class Measurement implements Model {
  public static final QueryField ID = field("Measurement", "id");
  public static final QueryField TRUE_PROOF = field("Measurement", "trueProof");
  public static final QueryField TEMPERATURE = field("Measurement", "temperature");
  public static final QueryField HYDROMETER = field("Measurement", "hydrometer");
  public static final QueryField TEMPERATURE_CORRECTION = field("Measurement", "temperatureCorrection");
  public static final QueryField HYDROMETER_CORRECTION = field("Measurement", "hydrometerCorrection");
  public static final QueryField NOTE = field("Measurement", "note");
  public static final QueryField FLAG = field("Measurement", "flag");
  public static final QueryField TAKEN_AT = field("Measurement", "takenAt");
  public static final QueryField CREATED_AT = field("Measurement", "createdAt");
  public static final QueryField UPDATED_AT = field("Measurement", "updatedAt");
  public static final QueryField BATCH = field("Measurement", "batchID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Float", isRequired = true) Double trueProof;
  private final @ModelField(targetType="Float", isRequired = true) Double temperature;
  private final @ModelField(targetType="Float", isRequired = true) Double hydrometer;
  private final @ModelField(targetType="Float", isRequired = true) Double temperatureCorrection;
  private final @ModelField(targetType="Float", isRequired = true) Double hydrometerCorrection;
  private final @ModelField(targetType="String") String note;
  private final @ModelField(targetType="Boolean") Boolean flag;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime takenAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  private final @ModelField(targetType="Batch") @BelongsTo(targetName = "batchID", type = Batch.class) Batch batch;
  public String getId() {
      return id;
  }
  
  public Double getTrueProof() {
      return trueProof;
  }
  
  public Double getTemperature() {
      return temperature;
  }
  
  public Double getHydrometer() {
      return hydrometer;
  }
  
  public Double getTemperatureCorrection() {
      return temperatureCorrection;
  }
  
  public Double getHydrometerCorrection() {
      return hydrometerCorrection;
  }
  
  public String getNote() {
      return note;
  }
  
  public Boolean getFlag() {
      return flag;
  }
  
  public Temporal.DateTime getTakenAt() {
      return takenAt;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public Batch getBatch() {
      return batch;
  }
  
  private Measurement(String id, Double trueProof, Double temperature, Double hydrometer, Double temperatureCorrection, Double hydrometerCorrection, String note, Boolean flag, Temporal.DateTime takenAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Batch batch) {
    this.id = id;
    this.trueProof = trueProof;
    this.temperature = temperature;
    this.hydrometer = hydrometer;
    this.temperatureCorrection = temperatureCorrection;
    this.hydrometerCorrection = hydrometerCorrection;
    this.note = note;
    this.flag = flag;
    this.takenAt = takenAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.batch = batch;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Measurement measurement = (Measurement) obj;
      return ObjectsCompat.equals(getId(), measurement.getId()) &&
              ObjectsCompat.equals(getTrueProof(), measurement.getTrueProof()) &&
              ObjectsCompat.equals(getTemperature(), measurement.getTemperature()) &&
              ObjectsCompat.equals(getHydrometer(), measurement.getHydrometer()) &&
              ObjectsCompat.equals(getTemperatureCorrection(), measurement.getTemperatureCorrection()) &&
              ObjectsCompat.equals(getHydrometerCorrection(), measurement.getHydrometerCorrection()) &&
              ObjectsCompat.equals(getNote(), measurement.getNote()) &&
              ObjectsCompat.equals(getFlag(), measurement.getFlag()) &&
              ObjectsCompat.equals(getTakenAt(), measurement.getTakenAt()) &&
              ObjectsCompat.equals(getCreatedAt(), measurement.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), measurement.getUpdatedAt()) &&
              ObjectsCompat.equals(getBatch(), measurement.getBatch());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTrueProof())
      .append(getTemperature())
      .append(getHydrometer())
      .append(getTemperatureCorrection())
      .append(getHydrometerCorrection())
      .append(getNote())
      .append(getFlag())
      .append(getTakenAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getBatch())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Measurement {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("trueProof=" + String.valueOf(getTrueProof()) + ", ")
      .append("temperature=" + String.valueOf(getTemperature()) + ", ")
      .append("hydrometer=" + String.valueOf(getHydrometer()) + ", ")
      .append("temperatureCorrection=" + String.valueOf(getTemperatureCorrection()) + ", ")
      .append("hydrometerCorrection=" + String.valueOf(getHydrometerCorrection()) + ", ")
      .append("note=" + String.valueOf(getNote()) + ", ")
      .append("flag=" + String.valueOf(getFlag()) + ", ")
      .append("takenAt=" + String.valueOf(getTakenAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("batch=" + String.valueOf(getBatch()))
      .append("}")
      .toString();
  }
  
  public static TrueProofStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Measurement justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Measurement(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      trueProof,
      temperature,
      hydrometer,
      temperatureCorrection,
      hydrometerCorrection,
      note,
      flag,
      takenAt,
      createdAt,
      updatedAt,
      batch);
  }
  public interface TrueProofStep {
    TemperatureStep trueProof(Double trueProof);
  }
  

  public interface TemperatureStep {
    HydrometerStep temperature(Double temperature);
  }
  

  public interface HydrometerStep {
    TemperatureCorrectionStep hydrometer(Double hydrometer);
  }
  

  public interface TemperatureCorrectionStep {
    HydrometerCorrectionStep temperatureCorrection(Double temperatureCorrection);
  }
  

  public interface HydrometerCorrectionStep {
    BuildStep hydrometerCorrection(Double hydrometerCorrection);
  }
  

  public interface BuildStep {
    Measurement build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep note(String note);
    BuildStep flag(Boolean flag);
    BuildStep takenAt(Temporal.DateTime takenAt);
    BuildStep createdAt(Temporal.DateTime createdAt);
    BuildStep updatedAt(Temporal.DateTime updatedAt);
    BuildStep batch(Batch batch);
  }
  

  public static class Builder implements TrueProofStep, TemperatureStep, HydrometerStep, TemperatureCorrectionStep, HydrometerCorrectionStep, BuildStep {
    private String id;
    private Double trueProof;
    private Double temperature;
    private Double hydrometer;
    private Double temperatureCorrection;
    private Double hydrometerCorrection;
    private String note;
    private Boolean flag;
    private Temporal.DateTime takenAt;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private Batch batch;
    @Override
     public Measurement build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Measurement(
          id,
          trueProof,
          temperature,
          hydrometer,
          temperatureCorrection,
          hydrometerCorrection,
          note,
          flag,
          takenAt,
          createdAt,
          updatedAt,
          batch);
    }
    
    @Override
     public TemperatureStep trueProof(Double trueProof) {
        Objects.requireNonNull(trueProof);
        this.trueProof = trueProof;
        return this;
    }
    
    @Override
     public HydrometerStep temperature(Double temperature) {
        Objects.requireNonNull(temperature);
        this.temperature = temperature;
        return this;
    }
    
    @Override
     public TemperatureCorrectionStep hydrometer(Double hydrometer) {
        Objects.requireNonNull(hydrometer);
        this.hydrometer = hydrometer;
        return this;
    }
    
    @Override
     public HydrometerCorrectionStep temperatureCorrection(Double temperatureCorrection) {
        Objects.requireNonNull(temperatureCorrection);
        this.temperatureCorrection = temperatureCorrection;
        return this;
    }
    
    @Override
     public BuildStep hydrometerCorrection(Double hydrometerCorrection) {
        Objects.requireNonNull(hydrometerCorrection);
        this.hydrometerCorrection = hydrometerCorrection;
        return this;
    }
    
    @Override
     public BuildStep note(String note) {
        this.note = note;
        return this;
    }
    
    @Override
     public BuildStep flag(Boolean flag) {
        this.flag = flag;
        return this;
    }
    
    @Override
     public BuildStep takenAt(Temporal.DateTime takenAt) {
        this.takenAt = takenAt;
        return this;
    }
    
    @Override
     public BuildStep createdAt(Temporal.DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep updatedAt(Temporal.DateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep batch(Batch batch) {
        this.batch = batch;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Double trueProof, Double temperature, Double hydrometer, Double temperatureCorrection, Double hydrometerCorrection, String note, Boolean flag, Temporal.DateTime takenAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Batch batch) {
      super.id(id);
      super.trueProof(trueProof)
        .temperature(temperature)
        .hydrometer(hydrometer)
        .temperatureCorrection(temperatureCorrection)
        .hydrometerCorrection(hydrometerCorrection)
        .note(note)
        .flag(flag)
        .takenAt(takenAt)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .batch(batch);
    }
    
    @Override
     public CopyOfBuilder trueProof(Double trueProof) {
      return (CopyOfBuilder) super.trueProof(trueProof);
    }
    
    @Override
     public CopyOfBuilder temperature(Double temperature) {
      return (CopyOfBuilder) super.temperature(temperature);
    }
    
    @Override
     public CopyOfBuilder hydrometer(Double hydrometer) {
      return (CopyOfBuilder) super.hydrometer(hydrometer);
    }
    
    @Override
     public CopyOfBuilder temperatureCorrection(Double temperatureCorrection) {
      return (CopyOfBuilder) super.temperatureCorrection(temperatureCorrection);
    }
    
    @Override
     public CopyOfBuilder hydrometerCorrection(Double hydrometerCorrection) {
      return (CopyOfBuilder) super.hydrometerCorrection(hydrometerCorrection);
    }
    
    @Override
     public CopyOfBuilder note(String note) {
      return (CopyOfBuilder) super.note(note);
    }
    
    @Override
     public CopyOfBuilder flag(Boolean flag) {
      return (CopyOfBuilder) super.flag(flag);
    }
    
    @Override
     public CopyOfBuilder takenAt(Temporal.DateTime takenAt) {
      return (CopyOfBuilder) super.takenAt(takenAt);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(Temporal.DateTime updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder batch(Batch batch) {
      return (CopyOfBuilder) super.batch(batch);
    }
  }
  
}
