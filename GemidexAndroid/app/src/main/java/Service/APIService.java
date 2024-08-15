package Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Constant.AppConstants;
import Model.ApiResponse;
import Model.UserObject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL) // Your laptop's IP and API port
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @POST(AppConstants.REGISTER_ENDPOINT)
    Call<ApiResponse<Void>> register(@Body UserObject userObject);

    @GET(AppConstants.USER_ENDPOINT)
    Call<ApiResponse<UserObject>> getUserByEmail(@Query("email") String email);
}
