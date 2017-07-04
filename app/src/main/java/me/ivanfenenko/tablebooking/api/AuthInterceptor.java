package me.ivanfenenko.tablebooking.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by klickrent on 29.06.17.
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(
                chain
                    .request()
                        .newBuilder()
                        .header("X-Api-Authentication", "intervIEW_TOK3n")
                        .build()
        );

        return response;
    }
}

