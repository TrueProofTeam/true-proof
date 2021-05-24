package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Distillery type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Distilleries")
public final class Distillery implements Model {
  public static final QueryField ID = field("Distillery", "id");
  public static final QueryField NAME = field("Distillery", "name");
  public static final QueryField DSP_ID = field("Distillery", "dspID");
  public static final QueryField USERS = field("Distillery", "users");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String dspID;
  private final @ModelField(targetType="String") List<String> users;
  private final @ModelField(targetType="Batch") @HasMany(associatedWith = "distillery", type = Batch.class) List<Batch> batches = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDspId() {
      return dspID;
  }
  
  public List<String> getUsers() {
      return users;
  }
  
  public List<Batch> getBatches() {
      return batches;
  }
  
  private Distillery(String id, String name, String dspID, List<String> users) {
    this.id = id;
    this.name = name;
    this.dspID = dspID;
    this.users = users;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Distillery distillery = (Distillery) obj;
      return ObjectsCompat.equals(getId(), distillery.getId()) &&
              ObjectsCompat.equals(getName(), distillery.getName()) &&
              ObjectsCompat.equals(getDspId(), distillery.getDspId()) &&
              ObjectsCompat.equals(getUsers(), distillery.getUsers());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDspId())
      .append(getUsers())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Distillery {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("dspID=" + String.valueOf(getDspId()) + ", ")
      .append("users=" + String.valueOf(getUsers()))
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
  public static Distillery justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Distillery(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      dspID,
      users);
  }
  public interface BuildStep {
    Distillery build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep dspId(String dspId);
    BuildStep users(List<String> users);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String dspID;
    private List<String> users;
    @Override
     public Distillery build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Distillery(
          id,
          name,
          dspID,
          users);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep dspId(String dspId) {
        this.dspID = dspId;
        return this;
    }
    
    @Override
     public BuildStep users(List<String> users) {
        this.users = users;
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
    private CopyOfBuilder(String id, String name, String dspId, List<String> users) {
      super.id(id);
      super.name(name)
        .dspId(dspId)
        .users(users);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder dspId(String dspId) {
      return (CopyOfBuilder) super.dspId(dspId);
    }
    
    @Override
     public CopyOfBuilder users(List<String> users) {
      return (CopyOfBuilder) super.users(users);
    }
  }
  
}
