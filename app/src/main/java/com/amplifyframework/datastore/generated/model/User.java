package com.amplifyframework.datastore.generated.model;


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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField DEFAULT_TEMPERATURE_UNIT = field("User", "defaultTemperatureUnit");
  public static final QueryField DEFAULT_TEMPERATURE_CORRECTION = field("User", "defaultTemperatureCorrection");
  public static final QueryField DEFAULT_HYDROMETER_CORRECTION = field("User", "defaultHydrometerCorrection");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String email;
  private final @ModelField(targetType="TemperatureUnit") TemperatureUnit defaultTemperatureUnit;
  private final @ModelField(targetType="Float") Double defaultTemperatureCorrection;
  private final @ModelField(targetType="Float") Double defaultHydrometerCorrection;
  public String getId() {
      return id;
  }
  
  public String getEmail() {
      return email;
  }
  
  public TemperatureUnit getDefaultTemperatureUnit() {
      return defaultTemperatureUnit;
  }
  
  public Double getDefaultTemperatureCorrection() {
      return defaultTemperatureCorrection;
  }
  
  public Double getDefaultHydrometerCorrection() {
      return defaultHydrometerCorrection;
  }
  
  private User(String id, String email, TemperatureUnit defaultTemperatureUnit, Double defaultTemperatureCorrection, Double defaultHydrometerCorrection) {
    this.id = id;
    this.email = email;
    this.defaultTemperatureUnit = defaultTemperatureUnit;
    this.defaultTemperatureCorrection = defaultTemperatureCorrection;
    this.defaultHydrometerCorrection = defaultHydrometerCorrection;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getDefaultTemperatureUnit(), user.getDefaultTemperatureUnit()) &&
              ObjectsCompat.equals(getDefaultTemperatureCorrection(), user.getDefaultTemperatureCorrection()) &&
              ObjectsCompat.equals(getDefaultHydrometerCorrection(), user.getDefaultHydrometerCorrection());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getDefaultTemperatureUnit())
      .append(getDefaultTemperatureCorrection())
      .append(getDefaultHydrometerCorrection())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("defaultTemperatureUnit=" + String.valueOf(getDefaultTemperatureUnit()) + ", ")
      .append("defaultTemperatureCorrection=" + String.valueOf(getDefaultTemperatureCorrection()) + ", ")
      .append("defaultHydrometerCorrection=" + String.valueOf(getDefaultHydrometerCorrection()))
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
  public static User justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new User(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      email,
      defaultTemperatureUnit,
      defaultTemperatureCorrection,
      defaultHydrometerCorrection);
  }
  public interface BuildStep {
    User build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep email(String email);
    BuildStep defaultTemperatureUnit(TemperatureUnit defaultTemperatureUnit);
    BuildStep defaultTemperatureCorrection(Double defaultTemperatureCorrection);
    BuildStep defaultHydrometerCorrection(Double defaultHydrometerCorrection);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String email;
    private TemperatureUnit defaultTemperatureUnit;
    private Double defaultTemperatureCorrection;
    private Double defaultHydrometerCorrection;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          email,
          defaultTemperatureUnit,
          defaultTemperatureCorrection,
          defaultHydrometerCorrection);
    }
    
    @Override
     public BuildStep email(String email) {
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep defaultTemperatureUnit(TemperatureUnit defaultTemperatureUnit) {
        this.defaultTemperatureUnit = defaultTemperatureUnit;
        return this;
    }
    
    @Override
     public BuildStep defaultTemperatureCorrection(Double defaultTemperatureCorrection) {
        this.defaultTemperatureCorrection = defaultTemperatureCorrection;
        return this;
    }
    
    @Override
     public BuildStep defaultHydrometerCorrection(Double defaultHydrometerCorrection) {
        this.defaultHydrometerCorrection = defaultHydrometerCorrection;
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
    private CopyOfBuilder(String id, String email, TemperatureUnit defaultTemperatureUnit, Double defaultTemperatureCorrection, Double defaultHydrometerCorrection) {
      super.id(id);
      super.email(email)
        .defaultTemperatureUnit(defaultTemperatureUnit)
        .defaultTemperatureCorrection(defaultTemperatureCorrection)
        .defaultHydrometerCorrection(defaultHydrometerCorrection);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder defaultTemperatureUnit(TemperatureUnit defaultTemperatureUnit) {
      return (CopyOfBuilder) super.defaultTemperatureUnit(defaultTemperatureUnit);
    }
    
    @Override
     public CopyOfBuilder defaultTemperatureCorrection(Double defaultTemperatureCorrection) {
      return (CopyOfBuilder) super.defaultTemperatureCorrection(defaultTemperatureCorrection);
    }
    
    @Override
     public CopyOfBuilder defaultHydrometerCorrection(Double defaultHydrometerCorrection) {
      return (CopyOfBuilder) super.defaultHydrometerCorrection(defaultHydrometerCorrection);
    }
  }
  
}
