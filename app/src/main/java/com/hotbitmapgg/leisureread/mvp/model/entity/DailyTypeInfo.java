package com.hotbitmapgg.leisureread.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG
 */
public class DailyTypeInfo implements Serializable {

  int limit;

  List<String> subscribed;

  List<SubjectDaily> others;

  public static class SubjectDaily implements Serializable {

    int color;

    String thumbnail;

    String description;

    int id;

    String name;


    public SubjectDaily(int color, String thumbnail, String description, int id, String name) {

      this.color = color;
      this.thumbnail = thumbnail;
      this.description = description;
      this.id = id;
      this.name = name;
    }


    public boolean isAdd() {

      return isAdd;
    }


    public void setIsAdd(boolean isAdd) {

      this.isAdd = isAdd;
    }


    boolean isAdd = false;


    public int getColor() {

      return color;
    }


    public void setColor(int color) {

      this.color = color;
    }


    public String getThumbnail() {

      return thumbnail;
    }


    public void setThumbnail(String thumbnail) {

      this.thumbnail = thumbnail;
    }


    public String getDescription() {

      return description;
    }


    public void setDescription(String description) {

      this.description = description;
    }


    public int getId() {

      return id;
    }


    public void setId(int id) {

      this.id = id;
    }


    public String getName() {

      return name;
    }


    public void setName(String name) {

      this.name = name;
    }
  }


  public int getLimit() {

    return limit;
  }


  public void setLimit(int limit) {

    this.limit = limit;
  }


  public List<String> getSubscribed() {

    return subscribed;
  }


  public void setSubscribed(List<String> subscribed) {

    this.subscribed = subscribed;
  }


  public List<SubjectDaily> getOthers() {

    return others;
  }


  public void setOthers(List<SubjectDaily> others) {

    this.others = others;
  }
}