package lbx.xtoollib.net;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * ResponseBody
 */
public interface IFileUploadService<T> {
    @Multipart
    @POST("upload")
    Observable<T> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);
}

