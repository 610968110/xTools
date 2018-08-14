package lbx.xtools;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * @author lbx
 * @date 2018/3/12.
 * Http请求 sns累签榜
 */

public interface SnsScores {
    @Headers(value = {"HeadersName1:HeadersValues1", "HeadersName2:HeadersValues2"})
    @FormUrlEncoded
    @POST("auth")
    Observable<Result<Config>> getConfigFromService(@Field("idCard") String idCard);

    @FormUrlEncoded
    @POST("api/authorization")
    Observable<SnsLoginRequest> loginSns(@Field("username") String userName,
                                         @Field("password") String password,
                                         @Field("access_token") String accessToken,
                                         @Field("method") String method);
}
