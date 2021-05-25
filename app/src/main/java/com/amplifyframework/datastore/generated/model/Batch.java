package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Batch type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Batches")
@Index(name = "byDistillery", fields = {"distilleryID"})
public final class Batch implements Model {
  public static final QueryField ID = field("Batch", "id");
  public static final QueryField BATCH_IDENTIFIER = field("Batch", "batchIdentifier");
  public static final QueryField BATCH_NUMBER = field("Batch", "batchNumber");
  public static final QueryField STATUS = field("Batch", "status");
  public static final QueryField TYPE = field("Batch", "type");
  public static final QueryField COMPLETED_AT = field("Batch", "completedAt");
  public static final QueryField CREATED_AT = field("Batch", "createdAt");
  public static final QueryField UPDATED_AT = field("Batch", "updatedAt");
  public static final QueryField DISTILLERY = field("Batch", "distilleryID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String batchIdentifier;
  private final @ModelField(targetType="Int") Integer batchNumber;
  private final @ModelField(targetType="Status") Status status;
  private final @ModelField(targetType="String") String type;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime completedAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  private final @ModelField(targetType="Distillery") @BelongsTo(targetName = "distilleryID", type = Distillery.class) Distillery distillery;
  private final @ModelField(targetType="Measurement") @HasMany(associatedWith = "batch", type = Measurement.class) List<Measurement> measurements = null;
  public String getId() {
      return id;
  }
  
  public String getBatchIdentifier() {
      return batchIdentifier;
  }
  
  public Integer getBatchNumber() {
      return batchNumber;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public String getType() {
      return type;
  }
  
  public Temporal.DateTime getCompletedAt() {
      return completedAt;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public Distillery getDistillery() {
      return distillery;
  }
  
  public List<Measurement> getMeasurements() {
      return measurements;
  }
  
  private Batch(String id, String batchIdentifier, Integer batchNumber, Status status, String type, Temporal.DateTime completedAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Distillery distillery) {
    this.id = id;
    this.batchIdentifier = batchIdentifier;
    this.batchNumber = batchNumber;
    this.status = status;
    this.type = type;
    this.completedAt = completedAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.distillery = distillery;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Batch batch = (Batch) obj;
      return ObjectsCompat.equals(getId(), batch.getId()) &&
              ObjectsCompat.equals(getBatchIdentifier(), batch.getBatchIdentifier()) &&
              ObjectsCompat.equals(getBatchNumber(), batch.getBatchNumber()) &&
              ObjectsCompat.equals(getStatus(), batch.getStatus()) &&
              ObjectsCompat.equals(getType(), batch.getType()) &&
              ObjectsCompat.equals(getCompletedAt(), batch.getCompletedAt()) &&
              ObjectsCompat.equals(getCreatedAt(), batch.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), batch.getUpdatedAt()) &&
              ObjectsCompat.equals(getDistillery(), batch.getDistillery());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getBatchIdentifier())
      .append(getBatchNumber())
      .append(getStatus())
      .append(getType())
      .append(getCompletedAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getDistillery())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Batch {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("batchIdentifier=" + String.valueOf(getBatchIdentifier()) + ", ")
      .append("batchNumber=" + String.valueOf(getBatchNumber()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("completedAt=" + String.valueOf(getCompletedAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("distillery=" + String.valueOf(getDistillery()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Batch justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Batch(
      id,
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
      batchIdentifier,
      batchNumber,
      status,
      type,
      completedAt,
      createdAt,
      updatedAt,
      distillery);
  }
  public interface BuildStep {
    Batch build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep batchIdentifier(String batchIdentifier);
    BuildStep batchNumber(Integer batchNumber);
    BuildStep status(Status status);
    BuildStep type(String type);
    BuildStep completedAt(Temporal.DateTime completedAt);
    BuildStep createdAt(Temporal.DateTime createdAt);
    BuildStep updatedAt(Temporal.DateTime updatedAt);
    BuildStep distillery(Distillery distillery);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String batchIdentifier;
    private Integer batchNumber;
    private Status status;
    private String type;
    private Temporal.DateTime completedAt;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private Distillery distillery;
    @Override
     public Batch build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Batch(
          id,
          batchIdentifier,
          batchNumber,
          status,
          type,
          completedAt,
          createdAt,
          updatedAt,
          distillery);
    }
    
    @Override
     public BuildStep batchIdentifier(String batchIdentifier) {
        this.batchIdentifier = batchIdentifier;
        return this;
    }
    
    @Override
     public BuildStep batchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }
    
    @Override
     public BuildStep status(Status status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        this.type = type;
        return this;
    }
    
    @Override
     public BuildStep completedAt(Temporal.DateTime completedAt) {
        this.completedAt = completedAt;
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
     public BuildStep distillery(Distillery distillery) {
        this.distillery = distillery;
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
    private CopyOfBuilder(String id, String batchIdentifier, Integer batchNumber, Status status, String type, Temporal.DateTime completedAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, Distillery distillery) {
      super.id(id);
      super.batchIdentifier(batchIdentifier)
        .batchNumber(batchNumber)
        .status(status)
        .type(type)
        .completedAt(completedAt)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .distillery(distillery);
    }
    
    @Override
     public CopyOfBuilder batchIdentifier(String batchIdentifier) {
      return (CopyOfBuilder) super.batchIdentifier(batchIdentifier);
    }
    
    @Override
     public CopyOfBuilder batchNumber(Integer batchNumber) {
      return (CopyOfBuilder) super.batchNumber(batchNumber);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder completedAt(Temporal.DateTime completedAt) {
      return (CopyOfBuilder) super.completedAt(completedAt);
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
     public CopyOfBuilder distillery(Distillery distillery) {
      return (CopyOfBuilder) super.distillery(distillery);
    }
  }
  
}
