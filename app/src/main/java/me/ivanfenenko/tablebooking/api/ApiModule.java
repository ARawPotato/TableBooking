package me.ivanfenenko.tablebooking.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import me.ivanfenenko.tablebooking.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by klickrent on 29.06.17.
 */

@Module()
public class ApiModule {

    @Provides
    ApiService ProvideAuthService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient httpClient,
                             Context context,
                                 GsonConverterFactory gsonConverterFactory,
                                 CallAdapter.Factory factory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(factory)
                .build();
    }

    @Provides
    OkHttpClient provideHttpClient(AuthInterceptor authInterceptor,
                                   HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    CallAdapter.Factory provideCallFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Provides
    AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

}