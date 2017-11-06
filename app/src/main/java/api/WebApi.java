package api;

import models.CommonPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Armaan on 01-11-2017.
 */

public interface WebApi {

@POST("{path}")
Call<CommonPojo> makeRequest(@Body WebRequestData webRequestData, @Path(value = "path",
        encoded = true) String urlEndPoint);
}
