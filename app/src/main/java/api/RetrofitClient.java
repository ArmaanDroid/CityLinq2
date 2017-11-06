package api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.AppConstants;

/**
 * Created by Armaan on 01-11-2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit;
    private static WebApi restClient;

    private RetrofitClient () {

    }
    static {
        setUpRestClient();
    }

    private static void setUpRestClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES);
            builder.readTimeout(1, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);
            builder.addInterceptor(interceptor);
            OkHttpClient client = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            restClient=retrofit.create(WebApi.class);
        }
    }

    public static WebApi getRestClient() {
        return restClient;
    }
}
