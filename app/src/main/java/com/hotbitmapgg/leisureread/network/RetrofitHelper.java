package com.hotbitmapgg.leisureread.network;

import com.hotbitmapgg.leisureread.LeisureReadApp;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyListBean;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyTypeInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemesDetails;
import com.hotbitmapgg.leisureread.network.api.ApiService;
import com.hotbitmapgg.leisureread.utils.NetWorkUtil;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG Retrofit帮助类
 */
public class RetrofitHelper {

  private static final String ZHIHU_DAILY_URL = "http://news-at.zhihu.com/api/4/";

  private static final String ZHIHU_LAST_URL = "http://news-at.zhihu.com/api/3/";

  private static OkHttpClient mOkHttpClient;

  private final ApiService mZhiHuApiService;

  private static final int CACHE_TIME_LONG = 60 * 60 * 24 * 7;


  public static RetrofitHelper builder() {

    return new RetrofitHelper();
  }


  private RetrofitHelper() {

    initOkHttpClient();

    Retrofit mRetrofit = new Retrofit.Builder()
        .baseUrl(ZHIHU_DAILY_URL)
        .client(mOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    mZhiHuApiService = mRetrofit.create(ApiService.class);
  }


  public static ApiService getLastZhiHuApi() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(ZHIHU_LAST_URL)
        .client(new OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(ApiService.class);
  }


  /**
   * 初始化OKHttpClient
   */
  private void initOkHttpClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    if (mOkHttpClient == null) {
      synchronized (RetrofitHelper.class) {
        if (mOkHttpClient == null) {
          //设置Http缓存
          Cache cache = new Cache(
              new File(LeisureReadApp.getAppContext().getCacheDir(), "HttpCache"),
              1024 * 1024 * 100);

          mOkHttpClient = new OkHttpClient.Builder()
              .cache(cache)
              .addInterceptor(mRewriteCacheControlInterceptor)
              .addNetworkInterceptor(mRewriteCacheControlInterceptor)
              .addInterceptor(interceptor)
              .retryOnConnectionFailure(true)
              .connectTimeout(15, TimeUnit.SECONDS)
              .build();
        }
      }
    }
  }


  /**
   * 设置okHttp缓存
   */
  private Interceptor mRewriteCacheControlInterceptor = chain -> {

    Request request = chain.request();
    if (!NetWorkUtil.isNetworkConnected()) {
      request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
    }
    Response originalResponse = chain.proceed(request);
    if (NetWorkUtil.isNetworkConnected()) {
      String cacheControl = request.cacheControl().toString();
      return originalResponse.newBuilder()
          .header("Cache-Control", cacheControl)
          .removeHeader("Pragma")
          .build();
    } else {
      return originalResponse.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME_LONG)
          .removeHeader("Pragma")
          .build();
    }
  };


  /**
   * 知乎日报Api封装 方便直接调用
   **/

  public Observable<DailyListBean> getLatestNews() {

    return mZhiHuApiService.getlatestNews();
  }


  public Observable<DailyListBean> getBeforeNews(String date) {

    return mZhiHuApiService.getBeforeNews(date);
  }


  public Observable<DailyDetailsInfo> getNewsDetails(int id) {

    return mZhiHuApiService.getNewsDetails(id);
  }


  public Observable<DailyTypeInfo> getDailyType() {

    return mZhiHuApiService.getDailyType();
  }


  public Observable<ThemesDetails> getThemesDetailsById(int id) {

    return mZhiHuApiService.getThemesDetailsById(id);
  }


  public Observable<DailyExtraMessageInfo> getDailyExtraMessageById(int id) {

    return mZhiHuApiService.getDailyExtraMessageById(id);
  }


  public Observable<DailyCommentInfo> getDailyLongCommentById(int id) {

    return mZhiHuApiService.getDailyLongComment(id);
  }


  public Observable<DailyCommentInfo> getDailyShortCommentById(int id) {

    return mZhiHuApiService.getDailyShortComment(id);
  }
}