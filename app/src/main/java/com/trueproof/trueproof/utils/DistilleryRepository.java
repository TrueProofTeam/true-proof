package com.trueproof.trueproof.utils;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Distillery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DistilleryRepository {

    String TAG = "DistilleryRepo";

    @Inject
    DistilleryRepository() {
    }

    public void saveDistillery(Distillery distillery, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.create(distillery),
                onSuccess, onFail);
    }

    public void getDistillery(String id, Consumer<Distillery> onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.query(ModelQuery.get(Distillery.class, id),
                r -> {
                    onSuccess.accept(r.getData());
                },
                onFail);


    }

    public void updateDistillery(Distillery distillery, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.update(distillery),
                onSuccess,
                onFail);
    }

    public void getDistilleryByUser(String id, Consumer<Distillery> onSuccess, Consumer<ApiException> onFail) {
//        this can be modified to retrieve distillery by whatever connection we'll use
        List<Distillery> output = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Distillery.class, Distillery.ID.contains(id)),
                response -> {
                    for (Distillery distillery : response.getData()) {
                        output.add(distillery);
                    }
                    onSuccess.accept(output.get(0));
                },

                onFail
        );
    }

    public void deleteDistillery(Distillery distillery, Consumer onSuccess, Consumer<ApiException> onFail) {
        Amplify.API.mutate(ModelMutation.delete(distillery), onSuccess, onFail);
    }

}
