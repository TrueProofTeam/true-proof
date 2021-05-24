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
  public static final QueryField TEMPERATURE = field("Measurement", "temperature");
  public static final QueryField HYDROMETER = field("Measurement", "hydrometer");
  public static final QueryField TEMPERATURE_CORRECTION = field("Measurement", "temperatureCorrection");
  public static final QueryField HYDROMETER_CORRECTION = field("Measurement", "hydrometerCorrection");
  public static final QueryField NOTE = field("Measurement", "note");
  public static final QueryField FLAG = field("Measurement", "flag");
  public static final QueryField CREATED_AT = field("Measurement", "createdAt");
  public static final QueryField UPDATED_AT = field("Measurement", "updatedAt");
  public static final QueryField BATCH = field("Measurement", "batchID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int", isRequired = true) Integer temperature;
  private final @ModelField(targetType="Int", isRequired = true) Integer hydrometer;
  private final @ModelField(targetType="Int", isRequired = true) Integer temperatureCorrection;
  private final @ModelField(targetType="Int", isRequired = true) Integer hydrometerCorrection;
  private final @ModelField(targetType="String") String note;
  private final @ModelField(targetType="Boolean") Boolean flag;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  private final @ModelField(targetType="Batch") @BelongsTo(targetName = "batchID", type = Batch.class) Batch batch;
  public String getId() {
      return id;
  }
  
  public Integer getTemperature() {
      return temperature;
  }
  
  public Integer getHydrometer() {
      return hydrometer;
  }
  
  public Integer getTemperatureCorrection() {
      return temperatureCorrection;
  }
  
  public Integer getHydrometerCorrection() {
      return hydrometerCorrection;
  }
  
  public String getNote() {
      return note;
  }
  
  public Boolean getFlag() {
      return flag;
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
  
  private Measurement(String id, Integer temperature, Integer hydrometer, Integer temperatureCorrection, Integer hydrometerCorrection, String note, Boolean flag, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Batch batch) {
    this.id = id;
    this.temperature = temperature;
    this.hydrometer = hydrometer;
    this.temperatureCorrection = temperatureCorrection;
    this.hydrometerCorrection = hydrometerCorrection;
    this.note = note;
    this.flag = flag;
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
              ObjectsCompat.equals(getTemperature(), measurement.getTemperature()) &&
              ObjectsCompat.equals(getHydrometer(), measurement.getHydrometer()) &&
              ObjectsCompat.equals(getTemperatureCorrection(), measurement.getTemperatureCorrection()) &&
              ObjectsCompat.equals(getHydrometerCorrection(), measurement.getHydrometerCorrection()) &&
              ObjectsCompat.equals(getNote(), measurement.getNote()) &&
              ObjectsCompat.equals(getFlag(), measurement.getFlag()) &&
              ObjectsCompat.equals(getCreatedAt(), measurement.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), measurement.getUpdatedAt()) &&
              ObjectsCompat.equals(getBatch(), measurement.getBatch());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTemperature())
      .append(getHydrometer())
      .append(getTemperatureCorrection())
      .append(getHydrometerCorrection())
      .append(getNote())
      .append(getFlag())
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
      .append("temperature=" + String.valueOf(getTemperature()) + ", ")
      .append("hydrometer=" + String.valueOf(getHydrometer()) + ", ")
      .append("temperatureCorrection=" + String.valueOf(getTemperatureCorrection()) + ", ")
      .append("hydrometerCorrection=" + String.valueOf(getHydrometerCorrection()) + ", ")
      .append("note=" + String.valueOf(getNote()) + ", ")
      .append("flag=" + String.valueOf(getFlag()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("batch=" + String.valueOf(getBatch()))
      .append("}")
      .toString();
  }
  
  public static TemperatureStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      temperature,
      hydrometer,
      temperatureCorrection,
      hydrometerCorrection,
      note,
      flag,
      createdAt,
      updatedAt,
      batch);
  }
  public interface TemperatureStep {
    HydrometerStep temperature(Integer temperature);
  }
  

  public interface HydrometerStep {
    TemperatureCorrectionStep hydrometer(Integer hydrometer);
  }
  

  public interface TemperatureCorrectionStep {
    HydrometerCorrectionStep temperatureCorrection(Integer temperatureCorrection);
  }
  

  public interface HydrometerCorrectionStep {
    BuildStep hydrometerCorrection(Integer hydrometerCorrection);
  }
  

  public interface BuildStep {
    Measurement build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep note(String note);
    BuildStep flag(Boolean flag);
    BuildStep createdAt(Temporal.DateTime createdAt);
    BuildStep updatedAt(Temporal.DateTime updatedAt);
    BuildStep batch(Batch batch);
  }
  

  public static class Builder implements TemperatureStep, HydrometerStep, TemperatureCorrectionStep, HydrometerCorrectionStep, BuildStep {
    private String id;
    private Integer temperature;
    private Integer hydrometer;
    private Integer temperatureCorrection;
    private Integer hydrometerCorrection;
    private String note;
    private Boolean flag;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private Batch batch;
    @Override
     public Measurement build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Measurement(
          id,
          temperature,
          hydrometer,
          temperatureCorrection,
          hydrometerCorrection,
          note,
          flag,
          createdAt,
          updatedAt,
          batch);
    }
    
    @Override
     public HydrometerStep temperature(Integer temperature) {
        Objects.requireNonNull(temperature);
        this.temperature = temperature;
        return this;
    }
    
    @Override
     public TemperatureCorrectionStep hydrometer(Integer hydrometer) {
        Objects.requireNonNull(hydrometer);
        this.hydrometer = hydrometer;
        return this;
    }
    
    @Override
     public HydrometerCorrectionStep temperatureCorrection(Integer temperatureCorrection) {
        Objects.requireNonNull(temperatureCorrection);
        this.temperatureCorrection = temperatureCorrection;
        return this;
    }
    
    @Override
     public BuildStep hydrometerCorrection(Integer hydrometerCorrection) {
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
    private CopyOfBuilder(String id, Integer temperature, Integer hydrometer, Integer temperatureCorrection, Integer hydrometerCorrection, String note, Boolean flag, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Batch batch) {
      super.id(id);
      super.temperature(temperature)
        .hydrometer(hydrometer)
        .temperatureCorrection(temperatureCorrection)
        .hydrometerCorrection(hydrometerCorrection)
        .note(note)
        .flag(flag)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .batch(batch);
    }
    
    @Override
     public CopyOfBuilder temperature(Integer temperature) {
      return (CopyOfBuilder) super.temperature(temperature);
    }
    
    @Override
     public CopyOfBuilder hydrometer(Integer hydrometer) {
      return (CopyOfBuilder) super.hydrometer(hydrometer);
    }
    
    @Override
     public CopyOfBuilder temperatureCorrection(Integer temperatureCorrection) {
      return (CopyOfBuilder) super.temperatureCorrection(temperatureCorrection);
    }
    
    @Override
     public CopyOfBuilder hydrometerCorrection(Integer hydrometerCorrection) {
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
