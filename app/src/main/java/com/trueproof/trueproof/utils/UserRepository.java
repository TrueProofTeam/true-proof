package com.trueproof.trueproof.utils;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.User;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is responsible for accessing User objects from the database. Currently it uses an
 * asynchronous API where every method takes a success callback and a failure callback.
 *
 * This class should not be used directly by the Activity/etc classes. Instead use the UserSettings
 * class.
 */
@Singleton
public class UserRepository {
    @Inject
    UserRepository() {
    }

    /**
     * Gets a User object given its id.
     * @param userId The UUID for the given User object
     * @param onSuccess This is called when the user object is found
     * @param onFail This is called when the user is not found. TODO: Figure out which exceptions are called when
     */
    public void getById(String userId, Consumer<User> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(ModelQuery.get(User.class, userId),
                r -> {
                    Log.i("UserRepository", "getById: " + r);
                    onSuccess.accept(r.getData());
                },
                onFail);
    }

    /**
     * Gets a User object given their email
     * @param email The UUID for the given User object
     * @param onSuccess This is called when the user object is found
     * @param onFail This is called when the user is not found. TODO: Figure out which exceptions are called when
     */
    public void getByEmail(String email, Consumer<User> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(ModelQuery.list(User.class, User.EMAIL.eq(email)),
                r -> {
                    Iterator<User> it = r.getData().iterator();
                    if (it.hasNext()) onSuccess.accept(it.next());
                    else onFail.accept(null);
                },
                onFail);
    }

    /**
     * Saves the User object to the database
     * @param user The User object to save to the database
     * @param onSuccess
     * @param onFail
     */
    public void save(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.create(user), onSuccess, onFail);
    }

    /**
     * Modifies the user object in the database
     * @param user The User object to save to the database
     * @param onSuccess
     * @param onFail
     */
    public void update(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(
                ModelMutation.update(user),
                onSuccess,
                onFail
        );
    }

    /**
     * Deletes the user object from the database.
     * @param user The User object to save to the database. This can be a fully hydrated User object
     *             or just the userId, built with User.justId(id).
     * @param onSuccess
     * @param onFail
     */
    public void delete(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.delete(user), onSuccess, onFail);
    }
}
