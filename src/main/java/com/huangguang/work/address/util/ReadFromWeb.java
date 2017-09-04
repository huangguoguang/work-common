package com.huangguang.work.address.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangguang.work.util.HttpClientUtil;

/**
 * 从国家统计局网站爬取省市区省乡行政区划
 * @author huangguang
 *
 */
public class ReadFromWeb {
  public static final String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";

  public static void main(String[] args) throws Exception {
    String url = baseUrl + "index.html";
    // 如果需要设置代理
    // initProxy("10.10.13.200", "80");
    String str = getContent(url).toUpperCase();
    String[] arrs = str.split("<A");

    List<Map<String, Object>> province = getProvince(arrs);
    List<Map<String, Object>> city = getCity(province);
    List<Map<String, Object>> county = getCounty(city);
    List<Map<String, Object>> town = getTown(county);
    System.out.println(province);
    System.out.println(city);
    System.out.println(county);
    System.out.println(town);
  }

  /**
   * 爬取街道信息
   * @param county
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> getTown(List<Map<String, Object>> county) throws Exception {
    List<Map<String, Object>> townList = new ArrayList<Map<String, Object>>();
    JSONArray townArray = new JSONArray();
    for (Iterator<Map<String, Object>> iterator = county.iterator(); iterator.hasNext();) {
      Map<String, Object> map = (Map<String, Object>) iterator.next();
      String countyUrl = (String) map.get("url");
      String parentId = (String) map.get("id");
      String prefix = (String) map.get("prefix");
      System.out.println(baseUrl + prefix + countyUrl);
      String content = getContent(baseUrl + prefix + countyUrl).toUpperCase();
      String[] towns = content.split("TOWNTR");
      for (int i = 1; i < towns.length; i++) {
        String townUrl = null;
        String townCode = null;
        String[] strs = towns[i].split("<A HREF='");
        JSONObject townJson = new JSONObject();
        Map<String, Object> townMap = new HashMap<String, Object>();
        for (int j = 1; j < 3; j++) {
          if (j == 1) {
            townUrl = strs[j].substring(0, strs[j].indexOf("'>"));
            townCode = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
            townJson.put("id", townCode);
            townMap.put("id", townCode);
            townMap.put("url", townUrl);
          } else {
            String streetName = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
            townJson.put("town", streetName);
            townMap.put("town", streetName);
          }
        }
        townJson.put("parent", parentId);
        townMap.put("parent", parentId);
        townArray.add(townJson);
        townList.add(townMap);
      }
    }
    System.out.println("townJson " + townArray);
    return townList;
  }

  /**
   * 爬取县区级信息
   * 
   * @param city
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> getCounty(List<Map<String, Object>> city) throws Exception {
    List<Map<String, Object>> countyList = new ArrayList<Map<String, Object>>();
    JSONArray countyArray = new JSONArray();
    for (Iterator<Map<String, Object>> iterator = city.iterator(); iterator.hasNext();) {
      Map<String, Object> map = (Map<String, Object>) iterator.next();
      String cityUrl = (String) map.get("url");
      String parentId = (String) map.get("id");
      String content = getContent(baseUrl + cityUrl).toUpperCase();
      String prefix = cityUrl.substring(0, cityUrl.indexOf("/") + 1);
      String[] citys = content.split("COUNTYTR");
      for (int i = 1; i < citys.length; i++) {
        if (citys[i].indexOf("<A HREF='") != -1) {
          String[] strs = citys[i].split("<A HREF='");
          JSONObject countyJson = new JSONObject();
          Map<String, Object> countyMap = new HashMap<String, Object>();
          for (int j = 1; j < 3; j++) {
            if (j == 1) {
              String countyUrl = strs[j].substring(0, strs[j].indexOf("'>"));
              String countyCode = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
              countyJson.put("id", countyCode);
              countyMap.put("url", countyUrl);
              countyMap.put("prefix", prefix);
              countyMap.put("id", countyCode);
            } else {
              String countyName = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
              countyJson.put("county", countyName);
              countyMap.put("county", countyName);
            }
          }
          countyJson.put("parent", parentId);
          countyMap.put("parent", parentId);
          countyArray.add(countyJson);
          countyList.add(countyMap);
        }
      }
    }
    System.out.println("countryJson  " + countyArray);
    return countyList;
  }

  /**
   * 爬取市级信息
   * 
   * @param province
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> getCity(List<Map<String, Object>> province) throws Exception {
    List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
    JSONArray cityArray = new JSONArray();
    for (Iterator<Map<String, Object>> iterator = province.iterator(); iterator.hasNext();) {
      Map<String, Object> map = (Map<String, Object>) iterator.next();
      String provinceUrl = (String) map.get("provinceUrl");
      String parentId = (String) map.get("id");
      String content = getContent(baseUrl + provinceUrl).toUpperCase();
      String[] citys = content.split("CITYTR");
      for (int c = 1; c < citys.length; c++) {
        String[] strs = citys[c].split("<A HREF='");
        String cityCode = null;
        JSONObject cityJson = new JSONObject();
        Map<String, Object> cityMap = new HashMap<String, Object>();
        for (int si = 1; si < 3; si++) {
          if (si == 1) {
            cityCode = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
            String cityUrl = strs[si].substring(0, strs[si].indexOf("'>"));
            cityJson.put("id", cityCode);
            cityMap.put("id", cityCode);
            cityMap.put("url", cityUrl);
          } else {
            String cityName = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
            cityJson.put("city", cityName);
            cityMap.put("city", cityName);
          }
        }
        cityJson.put("parent", parentId);
        cityMap.put("parent", parentId);
        cityArray.add(cityJson);
        cityList.add(cityMap);
      }
    }
    System.out.println("cityJson " + cityArray);
    return cityList;
  }

  /**
   * 爬取省级信息
   * 
   * @param arrs
   * @return
   */
  public static List<Map<String, Object>> getProvince(String[] arrs) {
    List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
    JSONArray provinceArray = new JSONArray();
    for (String s : arrs) {
      if (s.indexOf("HREF") != -1 && s.indexOf(".HTML") != -1) {
        String id = s.substring(s.indexOf("='") + 2, s.indexOf(".HTML"));
        String province = s.substring(s.indexOf("'>") + 2, s.indexOf("<BR/>"));
        String provinceUrl = s.substring(s.indexOf("='") + 2, s.indexOf("'>"));
        JSONObject provinceJson = new JSONObject();
        provinceJson.put("id", id);
        provinceJson.put("province", province);
        provinceJson.put("parent", "1");
        provinceArray.add(provinceJson);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("provinceUrl", provinceUrl);
        map.put("id", id);
        map.put("province", province);
        provinceList.add(map);
      }
    }
    System.out.println("provinceJson " + provinceArray);
    return provinceList;
  }

  // 获取网页的内容
  public static String getContent(String strUrl) throws Exception {
    String s = HttpClientUtil.httpGet(strUrl, new HashMap<String, String>(), "GBK");
    return s;
  }
}
