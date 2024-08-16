package Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Constant.AppConstants;
import Model.ApiResponse;
import Model.GemiObject;
import Model.UserObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<ApiResponse<UserObject>> getUserById(@Query("googleId") String googleId);

    @POST(AppConstants.CREATE_GEMIDEX_ENTRY_ENDPOINT)
    Call<ApiResponse<Void>> createGemidexEntry(@Body GemiObject gemiObject);
}
