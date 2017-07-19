package liguo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * *    ┏┓　　　┏┓
 * *  ┏┛┻━━━┛┻┓
 * *  ┃　　　　　　　┃
 * *  ┃　　　━　　　┃
 * *  ┃　┳┛　┗┳　┃
 * *  ┃　　　　　　　┃
 * *  ┃　　　┻　　　┃
 * *  ┃　　　　　　　┃
 * *  ┗━┓　　　┏━┛
 * *      ┃　　　┃  神兽保佑
 * *      ┃　　　┃  代码无BUG！
 * *      ┃　　　┗━━━┓
 * *      ┃　　　　　　　┣┓
 * *      ┃　　　　　　　┏┛
 * *      ┗┓┓┏━┳┓┏┛
 * *        ┃┫┫　┃┫┫
 * *        ┗┻┛　┗┻┛
 * * Created by Extends on 2016/8/4 0004.
 */
public interface API {
    @FormUrlEncoded
    @POST("/")
    Call<String> getDefault(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST("/")
    Observable<String> getDefaults(@Query("api") String id, @FieldMap Map<String, String> options);

    @Streaming
    @GET("index.php")
    Observable<String> getDefaultForGet(@QueryMap Map<String, String> options);

    @Streaming
    @GET
    Observable<ResponseBody> getFile(@Url String url);

    /**
     * 上传头像
     */
    @Multipart
    @POST("/")
    Observable<String> uploadMemberIcon(@Query("api") String id, @Part List<MultipartBody.Part> partList);

}
