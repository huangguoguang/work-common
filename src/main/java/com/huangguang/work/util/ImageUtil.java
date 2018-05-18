package com.huangguang.work.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * Description: 图片工具类
 * User : huangguang
 * DATE : 2018-04-02 9:48
 */
public class ImageUtil {
    /**
     * 将图片转换成Base64编码
     * @param imgFile
     * @return
     */
    public static String getImageStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    public static void getIdCardInfo(String imgFile) {
        String imgStr = getImageStr(imgFile);
        String host = "http://idcardocr.market.alicloudapi.com";
        String path = "/id_card_ocr";
        String method = "POST";
        String appcode = "82e39313cab94057bfd1f1737a30eca3";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("imgData", imgStr);
        bodys.put("type", "1");
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyCardInfo(String imgFile) {
        String imgStr = getImageStr(imgFile);
        String host = "http://face.market.alicloudapi.com";
        String path = "/efficient/idfaceIdentity";
        String method = "POST";
        String appcode = "82e39313cab94057bfd1f1737a30eca3";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("base64Str", imgStr);
        bodys.put("name", "黄光");
        bodys.put("number", "429001198706201699");
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String imgFile = "F:\\aaa.JPG";
        System.out.println(getImageStr(imgFile));
        getIdCardInfo(imgFile);
//        verifyCardInfo("F:\\bbb.jpg");
    }
}
