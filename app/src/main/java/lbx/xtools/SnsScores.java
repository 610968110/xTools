package lbx.xtools;


import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * @author lbx
 * @date 2018/3/12.
 * Http请求 sns累签榜
 */

public interface SnsScores {
    @FormUrlEncoded
    @POST("api/check_rank")
    Flowable<ScoresBean> initSign(@Field("version") String version,
                                  @Field("type") String type,
                                  @Field("access_token") String accessToken,
                                  @Field("method") String method);
}
