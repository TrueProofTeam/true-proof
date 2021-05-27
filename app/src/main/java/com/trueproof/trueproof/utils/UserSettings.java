package com.trueproof.trueproof.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.ApiException;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserSettings {
    final static String TAG = "UserSettings";
    final static String SHARED_PREFERENCES_NAME = "trueproof.trueproof";

    private final SharedPreferences sharedPreferences;
    private final DistilleryRepository distilleryRepository;
    private final UserRepository userRepository;
    private Distillery cachedDistillery;
    private User cachedUserSettings;

    @Inject
    public UserSettings(@ApplicationContext Context context,
                        DistilleryRepository distilleryRepository,
                        UserRepository userRepository
    ) {
        this.distilleryRepository = distilleryRepository;
        this.userRepository = userRepository;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Amplify.Auth.getCurrentUser();
    }


    /**
     * Gets the email for the current user.
     *
     * @return A string containing the email for the current user. Returns
     * null if the user is not logged in. TODO: confirm this
     */
    @Nullable
    public String getEmail() {
        return Amplify.Auth.getCurrentUser().getUsername();
    }

    /**
     * Gets the distillery for the current user.
     *
     * @param success The callback called when the distillery is successfully grabbed
     * @param fail    The callback for when an error occurs. The ApiException is passed
     *                onto the user and the cause can be grabbed with getCause().
     */
    public void getDistillery(Consumer<Distillery> success, Consumer<ApiException> fail) {
        if (cachedDistillery != null) {
            success.accept(cachedDistillery);
            return;
        }
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser == null) fail.accept(
                new ApiException("user is not logged in",
                        new AuthException.SignedOutException(),
                        "maybe prompt the user to log in")
        );

        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    String distilleryId = getValueFromAuthUserAttributesByKey(attributes,
                            "custom:distilleryId");
                    if (distilleryId != null) {
                        distilleryRepository.getDistillery(distilleryId,
                                distillery -> {
                                    cachedDistillery = distillery;
                                    success.accept(distillery);
                                }
                                ,
                                fail
                        );
                    } else {
                        fail.accept(new ApiException("user does not have distilleryId attribute",
                                new Exception(),
                                "I dunno help"));
                    }
                },
                error -> {
                    Log.e("UserSettings", "getDistillery: ", error);
                }
        );
    }

    /**
     * Gets the User object for the current user, which contains user settings.
     *
     * @param success The callback called when the User is successfully grabbed
     * @param fail    The callback for when an error occurs. The ApiException is passed
     *                onto the user and the cause can be grabbed with getCause().
     */
    public void getUserSettings(Consumer<User> success, Consumer<Exception> fail) {
        if (cachedUserSettings != null) {

        }

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser == null) fail.accept(
                new ApiException("user is not logged in",
                        new Exception(),
                        "maybe prompt the user to log in")
        );
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    String userId = getValueFromAuthUserAttributesByKey(attributes,
                            "custom:userId");
                    Log.i(TAG, "userId: " + userId);
                    if (userId != null) {
                        userRepository.getById(userId,
                                user -> {
                                    if (user != null) {
                                        success.accept(user);
                                    } else {
                                        fail.accept(new Exception("user object was not found in the database"));
                                    }
                                },
                                apiException -> {
                                    fail.accept(apiException);
                                }
                        );
                    } else {
                        Log.i(TAG, "AuthUser custom:userId is null");
                        createNewUserEntity(authUser,
                                success, e -> {
                                    fail.accept(e);
                                });
                    }
                },
                error -> {
                    Log.e("UserSettings", "Couldn't get authUser attributes", error);
                }
        );
    }

    public void createNewUserEntity(AuthUser authUser, Consumer<User> success, Consumer<AmplifyException> fail) {
        User newUser = User.builder()
                .defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                .defaultHydrometerCorrection(0.0)
                .defaultTemperatureCorrection(0.0)
                .email(authUser.getUsername())
                .build();
        userRepository.save(newUser,
                r -> {
                    Log.i(TAG, "createNewUserEntity: User entity saved to database");
                    Amplify.Auth.updateUserAttribute(
                            new AuthUserAttribute(AuthUserAttributeKey.custom("custom:userId"), newUser.getId()),
                            r2 -> {
                                Log.i(TAG, "createNewUserEntity: Added userId to the authUser attributes");
                                cachedUserSettings = newUser;
                                success.accept(newUser);
                            },
                            authException -> {
                                fail.accept(authException);
                            }
                    );
                },
                e -> {
                    fail.accept(new ApiException(
                                    "Couldn't save user to the database",
                                    e,
                                    "Make sure user is logged in and amplify is set up correctly"
                            )
                    );
                });
    }

    /**
     * Updates the User in the database, which contains user settings.
     *
     * @param user    The user settings object to save.
     * @param success The callback to be called when the settings are successfully saved.
     * @param fail    The callback to be called when an error occurs.
     */
    public void saveUserSettings(User user, Consumer success, Consumer<ApiException> fail) {
        userRepository.update(user,
                r -> {
                    cachedUserSettings = user;
                    success.accept(r);
                },
                fail
        );
    }


    /**
     * @param user    The user object to be made into the database
     * @param success The callback to be called when the settings are successfully saved.
     * @param fail    the callback to be called when an errror occurs
     */
    public void addUser(User user, Consumer success, Consumer<ApiException> fail) {
        userRepository.save(user,
                r -> {
                    cachedUserSettings = user;
                    success.accept(r);
                },
                fail
        );

    }

    /**
     * Updates the Distillery in the database
     *
     * @param distillery The distillery settings object to update.
     * @param success    The callback to be called when the settings are successfully saved.
     * @param fail       The callback to be called when an error occurs.
     */
    public void updateDistillerySettings(Distillery distillery, Consumer success, Consumer<ApiException> fail) {
        distilleryRepository.updateDistillery(distillery,
                d -> {
                    cachedDistillery = distillery;
                    success.accept(d);
                }, fail);
    }

    /**
     * Gets the value from a list of AuthUserAttribute given a key. These AuthUserAttributes are
     * key-value pairs and contain all of the AuthUser's custom attributes as well as the built
     * in Cognito attributes.
     *
     * @param attributes The list of AuthUserAttributes given by Amplify.Auth.fetchUserAttributes
     * @param keyString  The key of the AuthUserAttribute
     * @return
     */
    private String getValueFromAuthUserAttributesByKey(List<AuthUserAttribute> attributes, String keyString) {
        for (AuthUserAttribute attribute : attributes) {
            if (attribute.getKey().getKeyString().equals(keyString)) {
                return attribute.getValue();
            }
        }
        return null;
    }

    /**
     * Gets the cached distillery object populated when the user signed in. If the cache has not been populated, returns null.
     *
     * @return
     */
    @Nullable
    public Distillery getCachedDistillery() {
        return cachedDistillery;
    }

    /**
     * Gets the cached user settings populated when the user signed in. If the cache has not been populated, returns null.
     *
     * @return
     */
    @Nullable
    public User getCachedUserSettings() {
        return cachedUserSettings;
    }

    /**
     * Run this on user log in, or sign up.
     * Performs database requests and populates the cache for userSettings and Distillery settings.
     */
    public void refreshCache(Consumer success, Consumer<Exception> failure) {
        AtomicBoolean distillery = new AtomicBoolean(false);
        AtomicBoolean fail = new AtomicBoolean(false);
        AtomicBoolean user = new AtomicBoolean(false);
        getDistillery(
                r -> {
                    if (!fail.get() && user.get()) {
                        success.accept(true);
                    }
                    distillery.set(true);
                },
                e -> {
                    if (!fail.get()) {
                        fail.set(true);
                        failure.accept(e);
                    }
                });
        getUserSettings(
                r -> {
                    user.set(true);
                    if (!fail.get() && distillery.get()) {
                        success.accept(true);
                    }
                },
                e -> {
                    if (!fail.get()) {
                        fail.set(true);
                        failure.accept(e);
                    }
                });
    }

    /**
     * Run this when you log out a user to clear the cached userSettings and distillerySettings
     */
    public void invalidateCache() {
        this.cachedUserSettings = null;
        this.cachedDistillery = null;
    }
}
