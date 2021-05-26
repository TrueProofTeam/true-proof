package com.trueproof.trueproof.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Distillery;
import com.amplifyframework.datastore.generated.model.TemperatureUnit;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserSettings {
    final static String SHARED_PREFERENCES_NAME = "trueproof.trueproof";

    private final SharedPreferences sharedPreferences;
    private final DistilleryRepository distilleryRepository;
    private final UserRepository userRepository;
//
//    @Inject
//    UserSettings userSettings;

    @Inject
    UserSettings(@ApplicationContext Context context,
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
     * @return A string containing the email for the current user. Returns
     * null if the user is not logged in. TODO: confirm this
     */
    @Nullable
    public String getEmail() {
        return Amplify.Auth.getCurrentUser().getUserId();
    }

    /**
     * Gets the distillery for the current user.
     * @param success The callback called when the distillery is successfully grabbed
     * @param fail The callback for when an error occurs. The ApiException is passed
     *             onto the user and the cause can be grabbed with getCause().
     */
    public void getDistillery(Consumer<Distillery> success, Consumer<ApiException> fail) {
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
                                success,
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
     * @param success The callback called when the User is successfully grabbed
     * @param fail The callback for when an error occurs. The ApiException is passed
     *             onto the user and the cause can be grabbed with getCause().
     */
    public void getUserSettings(Consumer<User> success, Consumer<ApiException> fail) {
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
                    if (userId != null) {
                        userRepository.getById(userId,
                                success,
                                fail
                        );
                    } else {
                        User user = User.builder()
                                .defaultTemperatureUnit(TemperatureUnit.FAHRENHEIT)
                                .defaultHydrometerCorrection(0.0)
                                .defaultTemperatureCorrection(0.0)
                                .email(authUser.getUsername())
                                .build();
                        userRepository.save(user,
                                r -> {
                                    success.accept(user);
                                },
                                r -> {
                                    fail.accept(new ApiException(
                                                    "Couldn't save user to the database",
                                                    new Exception(),
                                                    "aoeu"
                                            )
                                    );
                                });
                    }
                },
                error -> {
                    Log.e("UserSettings", "getDistillery: ", error);
                }
        );
    }

    /**
     * Gets the value from a list of AuthUserAttribute given a key. These AuthUserAttributes are
     * key-value pairs and contain all of the AuthUser's custom attributes as well as the built
     * in Cognito attributes.
     * @param attributes The list of AuthUserAttributes given by Amplify.Auth.fetchUserAttributes
     * @param keyString The key of the AuthUserAttribute
     * @return
     */
    private String getValueFromAuthUserAttributesByKey(List<AuthUserAttribute> attributes, String keyString) {
        for (AuthUserAttribute attribute : attributes) {
            if (attribute.getKey().getKeyString().equals("custom:distilleryId")) {
                return attribute.getValue();
            }
        }
        return null;
    }
}
