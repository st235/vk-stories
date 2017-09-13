package com.github.sasd97.vk_stories.data.net;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import io.reactivex.Single;

/**
 * Created by alexander on 13/09/2017.
 */

public class VkApiWrapper {

    private final int UNSET = -1;
    private final int SINGLE_OBJECT = 0;

    private final String ID = "id";
    private final String RESPONSE = "response";
    private final String ATTACHMENTS = "attachments";

    public Single<Integer> getUserId() {
        return Single
                .fromCallable(() -> {
                    final JSONObject[] array = {null};
                    VKApi.users().get().executeSyncWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            array[0] = response.json;
                        }
                    });
                    return array;
                }).map(array -> {
                    JSONObject object = array[SINGLE_OBJECT];
                    return obtainIdFromJson(object);
                });
    }

    public Single<Integer[]> uploadPhoto(int uid,
                                       @NonNull Bitmap bitmap) {
        return Single.fromCallable(() -> {
            final JSONObject[] array = {null};
            VKUploadImage image = new VKUploadImage(bitmap, VKImageParameters.jpgImage(0.9f));
            VKApi.uploadWallPhotoRequest(image, uid, 0)
            .executeSyncWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    array[0] = response.json;
                }
            });
            return array;
        }).map(array -> {
            JSONObject object = array[SINGLE_OBJECT];
            return new Integer[]{ uid, obtainIdFromJson(object) };
        });
    }

    public Single<String> createPost(int uid, int mediaId) {
        return Single.fromCallable(() -> {
            final String[] json = {null};
            VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, uid,
                    ATTACHMENTS, String.format(Locale.US, "photo%1$d_%2$d", uid, mediaId)))
                    .executeSyncWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            json[0] = response.responseString;
                        }
                    });
            return json[SINGLE_OBJECT];
        });
    }

    private int obtainIdFromJson(JSONObject obj) throws Exception {
        JSONArray response = obj.getJSONArray(RESPONSE);
        JSONObject singleResponse = response.getJSONObject(SINGLE_OBJECT);
        return singleResponse.getInt(ID);
    }
}
