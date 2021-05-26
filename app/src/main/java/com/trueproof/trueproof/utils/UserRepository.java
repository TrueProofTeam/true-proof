package com.trueproof.trueproof.utils;

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

@Singleton
public class UserRepository {
    @Inject
    UserRepository() {
    }

    public void getById(String userId, Consumer<User> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(ModelQuery.get(User.class, userId),
                r -> {
                    onSuccess.accept(r.getData());
                },
                onFail);
    }

    public void getByEmail(String email, Consumer<User> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(ModelQuery.list(User.class, User.EMAIL.eq(email)),
                r -> {
                    Iterator<User> it = r.getData().iterator();
                    if (it.hasNext()) onSuccess.accept(it.next());
                    else onFail.accept(null);
                },
                onFail);
    }

    public void save(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.create(user), onSuccess, onFail);
    }

    public void update(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(
                ModelMutation.update(user),
                onSuccess,
                onFail
        );
    }

    public void delete(User user, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.delete(user), onSuccess, onFail);
    }
}
