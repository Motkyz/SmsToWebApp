package com.example.smsforwardapp;

import android.content.Context;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sender {
    public static void sendToServer(Context context, String message) {
        String url = "http://" + MainActivity.IPAddress;

        OkHttpClient client = new OkHttpClient();

        String json = String.format("{\"code\":\"%s\"}", message);
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, mediaType);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // Выполнение запроса в отдельном потоке
        new Thread(() -> {
            try (Response response = client.newCall(request).execute()){
            } catch (Exception e) {
            }
        }).start();
    }
}
