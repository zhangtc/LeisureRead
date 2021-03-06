package com.hotbitmapgg.leisureread;

import com.hotbitmapgg.leisureread.app.AppComponent;
import com.hotbitmapgg.leisureread.app.AppModule;
import com.hotbitmapgg.leisureread.app.DaggerAppComponent;
import com.hotbitmapgg.rxzhihu.R;
import com.squareup.leakcanary.LeakCanary;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import android.app.Application;

/**
 * Created by hcc on 2016/12/26 17:08
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG
 */
public class LeisureReadApp extends Application {

  private AppComponent mAppComponent;

  public static LeisureReadApp mAppContext;

  public static String BMBO_KEY = "b0d3e44679384c31dd28855c08a7520d";


  public static LeisureReadApp getAppContext() {

    return mAppContext;
  }


  public AppComponent getAppComponent() {

    return mAppComponent;
  }


  @Override
  public void onCreate() {

    super.onCreate();
    mAppContext = this;

    initFont();
    initRealm();
    initAppComPonent();
    initLeakCanary();
  }


  /**
   * 初始化Realm数据库配置
   */
  private void initRealm() {

    Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this)
        .deleteRealmIfMigrationNeeded()
        .schemaVersion(1)
        .build());
  }


  /**
   * 初始化LeakCanary内存泄漏检测
   */
  private void initLeakCanary() {

    LeakCanary.install(this);
  }


  /**
   * 初始化组件
   */
  private void initAppComPonent() {

    mAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }


  /**
   * 初始化字体管理库
   */
  private void initFont() {

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/Lobster-1.4.otf")
        .setFontAttrId(R.attr.fontPath)
        .build()
    );
  }
}
