package hi.soft.ge;

/**
 * Created by kakha on 11/16/16.
 */

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("rest/softge")
    Call<JSONResponse> getJSON();

}
