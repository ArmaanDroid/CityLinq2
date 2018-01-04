package api;

import models.CommonPojo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Armaan on 01-11-2017.
 */

public interface WebApi {

    @POST("{path}")
    Call<CommonPojo> makeRequest(@Body WebRequestData webRequestData, @Path(value = "path",
            encoded = true) String urlEndPoint);

    @GET()
    Call<CommonPojo> makeGetRequest(@Url String urlEndPoint);

    @GET("{path}")
    Call<CommonPojo> makeGetRequestWithoutEncode(@Path(value = "path",
            encoded = true) String urlEndPoint);

    @GET("user/transport")
    Call<CommonPojo> getRoutes(@Query("source") String source,
                               @Query("destination") String destination);

    @PUT("{path}")
    Call<CommonPojo> updateData(
            @Path(value = "path", encoded = true) String urlEndPoint,
            @Body WebRequestData webRequestData);


    @Multipart
    @PUT("{path}")
    Call<CommonPojo> updateProfile(
            @Path(value = "path", encoded = true) String urlEndPoint,
            @Part MultipartBody.Part file,
            @Part("data") RequestBody description);


}
