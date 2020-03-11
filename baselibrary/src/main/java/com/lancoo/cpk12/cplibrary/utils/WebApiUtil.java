package com.lancoo.cpk12.cplibrary.utils;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lancoo.cpk12.cplibrary.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络相关的util
 */
public class WebApiUtil {

    /**
     * 字符集
     */
    private static final String CHARSET = "UTF-8";
    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 15000;
    /**
     * 读取超时时间
     */
    private static final int READ_TIMEOUT = 15000;

    private static OkHttpClient mHttpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        mHttpClient = builder.build();
    }

    /**
     * 通过get请求获取到服务端的json数据，并把该数据转化成对象集合
     *
     * @param baseUrl  不带参数的url，如：http://192.168.2.131:8088/api/User/GetList
     * @param params   参数，可为null
     * @param classOfT 与返回的json数据相对应的java类
     * @return 若为null，则获取失败
     */
    public static <T> ArrayList<T> doGet(String baseUrl,
                                         HashMap<String, String> params, Class<T> classOfT) {
        String json = doGet(baseUrl, params);
        Log.i("minisky", "doGet: url = " + baseUrl);
        Log.i("minisky", "doGet: json = " + json);
        return getArrayList(json, classOfT);
    }


    /**
     * 通过get请求获取到服务端的json数据，并把该数据转化成对象集合
     *
     * @param baseUrl  不带参数的url，如：http://192.168.2.131:8088/api/User/GetList
     * @param params   参数，可为null
     * @param classOfT 与返回的json数据相对应的java类
     * @return 若为null，则获取失败
     */
    public static <T> ArrayList<T> doGet(String baseUrl,
                                         HashMap<String, String> params, Class<T> classOfT, boolean needDecode) {
        // String
        // old="http://192.168.126.158:5354/Community/Forum/Interface/ForumForMobile.ashx";
        // String
        // base="http://192.168.2.19:8099/Community/Forum/Interface/ForumForMobile.ashx";
        // if(baseUrl.equals(old)) baseUrl=base;
        String json = doGet(baseUrl, params);

        // Log.e("ttt", json);
        // Log.i("System.out.alexaaa", "doGet方法被调用返回ArrayList的泛型数据json="+json);
        /*try {//
            if(  classOfT.getName().contains("Msg"))//classOfT.getName().contains("Qtn")|| classOfT.getName().contains("Survey")||
			return getArrayList(json, classOfT,1);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        if (needDecode) {
            return getArrayList(Uri.decode(json), classOfT);
        } else {
            return getArrayList(json, classOfT);
        }
    }


    public static String doGetJsonString(String baseUrl,
                                         HashMap<String, String> params) {
        String json = doGet(baseUrl, params);
        return json;
    }

    public static <T> ArrayList<T> doGet(String baseUrl,
                                         HashMap<String, String> params, Class<T> classOfT, int type) {
        String json = doGet(baseUrl, params);
        if (type == 1) return getArrayList(json, classOfT, type);
        return getArrayList(json, classOfT);
    }


    /**
     * 通过post请求（提交json）获取到服务端的json数据，并把该数据转化成对象集合
     *
     * @param baseUrl  不带参数的url，如：http://192.168.2.131:8088/api/User/GetList
     * @param param    参数，将会被转化成json数据发送给服务端
     * @param classOfT 与返回的json数据相对应的java类
     * @return 若为null，则获取失败
     */
    public static <T> ArrayList<T> doPostByJson(String baseUrl, Object param,
                                                Class<T> classOfT) {
        String json = doPostByJson(baseUrl, param);
        return getArrayList(json, classOfT);
    }

    /**
     * 通过post请求（提交表单）获取到服务端的json数据，并把该数据转化成对象集合
     *
     * @param baseUrl  不带参数的url，如：http://192.168.2.131:8088/api/User/GetList
     * @param param    参数，将会被转化成json数据发送给服务端
     * @param classOfT 与返回的json数据相对应的java类
     * @return 若为null，则获取失败
     */
    public static <T> ArrayList<T> doPostByForm(String baseUrl, Object param,
                                                Class<T> classOfT) {
        HashMap<String, String> paramMap = FormUtil.convertToForm(param);
        String json = postWithForm(baseUrl, paramMap);//doPostByForm(baseUrl, param);
        Log.i("minisky", "dopost: json = " + json);
        Log.i("minisky", "dopost: baseUrl = " + baseUrl);
        return getArrayList(json, classOfT);
    }

    public static String doPostByForm(String baseUrl, HashMap<String, String> paramMap) {
        String json = postWithForm(baseUrl, paramMap);
        return json;
    }

    public static <T> ArrayList<T> doPostByForm(String baseUrl, HashMap<String, String> paramMap,
                                                Class<T> classOfT) {
        //HashMap<String, String> paramMap = FormUtil.convertToForm(param);
        String json = postWithForm(baseUrl, paramMap);//doPostByForm(baseUrl, param);
        Log.i("minisky", "dopost: json = " + json);
        //Log.i("ttt", "@@:"+json);
        return getArrayList(Uri.decode(json), classOfT);
    }

    /**
     * 测试用doGet重载方法
     */

    public static <T> ArrayList<T> doGet(String baseUrl, Class<T> classOfT) {
        String json = doGet(baseUrl);
        return getArrayList(json, classOfT);
    }

    public static String doGet(String baseUrl) {
        String url = baseUrl;
        URL uurl;
        try {
            uurl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) uurl.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.setDoInput(true);// 指示应用程序要从 URL 连接读取数据。
            con.setRequestMethod("GET");// 设置请求方式
            if (con.getResponseCode() == 200) {
                InputStream is = con.getInputStream();
                String json = formatIsToString(is);
                return json;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doGet(String baseUrl, HashMap<String, String> params) {
        String url = baseUrl;
        if (params != null && !params.isEmpty()) {
            url = url + "?";
            for (Entry<String, String> entry : params.entrySet())
                url = url + entry.getKey() + "=" + encode(entry.getValue())
                        + "&";
            url = url.substring(0, url.length() - 1);
        }
        // Log.i("System.out.alexaaa", "url="+url);
        // url="http://192.168.2.19:8002/Community/ThemeTalk/InnerInterface/TTFM.ashx";
        // url="http://192.168.2.19:8002/Community/ThemeTalk/InnerInterface/ThemeTalkForMobile.ashx?ThemeID=18506B7E54A24FFCA5578D5AEE792386&PageIndex=1&Method=getspeechlist&PageSize=20&SysID=610";
        URL uurl;
        // Log.i("System.out.alex", "doGetJson方法url="+url);
        // LogUtil.writeLogtoFile("System.out.alex", "doGetJson被调用URL="+url);
        try {
            Log.i("laodaye", "doGet: url = " + url);
            uurl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) uurl.openConnection();
            con.setConnectTimeout(15000);// xwt 3000太短了，网络差可能会获取不到数据
            con.setReadTimeout(15000);
            con.setDoInput(true);// 指示应用程序要从 URL 连接读取数据。
            con.setRequestMethod("GET");// 设置请求方式
            if (con.getResponseCode() == 200) {
                InputStream is = con.getInputStream();
                String json = formatIsToString(is);
                // Log.i("System.out.alexa", "json="+json);
                // LogUtil.writeLogtoFile("System.out.alex",
                // "doGetJson方法得到的结果:"+json);
                return json;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Log.i("System.out.alex",
            // "MalformedURLException异常 原因"+e.getMessage());
            // LogUtil.writeLogtoFile("System.out.alex",
            // "doGetJson方法发生MalformedURLException异常,原因:"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            // Log.i("System.out.alex", "IOException e 原因:"+e.getMessage());
            // LogUtil.writeLogtoFile("System.out.alex",
            // "doGetJson方法发生IOException异常,原因:"+e.getMessage());
        }

        return null;

    }

    // tangyuwen
    // 将流转化为字符串
    private static String formatIsToString(InputStream is) {
        if (is == null) {
            System.out.println("is == null");
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        try {
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            baos.close();
            is.close();
            return new String(baos.toByteArray(), "utf-8");
        } catch (IOException e) {
            System.out.println("formatIsToString发生异常" + e.getMessage());
            // LogUtil.writeLogtoFile("System.out.alex",
            // "formatIsToString发生异常"+e.getMessage());
            // Log.i("System.out.alex", "IOException异常"+e.getMessage());
            return null;
        }
    }

    /**
     * 通过post请求（提交json）获取到服务端的json数据
     *
     * @param baseUrl 不带参数的url，如：http://192.168.2.131:8088/api/User/GetList
     * @param param   参数，将会被转化成json数据发送给服务端
     * @return 若为null，则获取失败
     */
    public static String doPostByJson(String baseUrl, Object param) {
        RequestBody jsonBody = null;
        if (param != null) {
            String json = GsonUtil.objectToJson(param);
            jsonBody = getRequestBody(json);
        }
        /*if (YunApplication.IsDubeg)
            Log.d("ttt", " @ " + baseUrl);*/
        return executeRequest(getRequestByJson(baseUrl, jsonBody));
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(String url,
                                           HashMap<String, String> map) {
        if (map == null) {
            return "";
        }
        boolean isSort = true;
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        if (isSort) {
            Collections.sort(keys);
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key).toString();
            sb.append(key + "=" + value);
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.lastIndexOf("&"));
        }
        /*
         * for (Map.Entry<String, Object> entry : map.entrySet()) {
         * sb.append(entry.getKey() + "=" + entry.getValue()); sb.append("&"); }
         * String s = sb.toString(); if (s.endsWith("&")) { //s =
         * StringUtils.substringBeforeLast(s, "&"); s = s.substring(0,
         * s.lastIndexOf("&")); }
         */
        if (BuildConfig.DEBUG)
            Log.d("ttt", " @@ " + url + "?" + s);
        return s;
    }

    public static <T> ArrayList<T> getArrayList(String json, Class<T> classOfT) {
        ArrayList<T> list = convertJsonToList(json, classOfT);
//        ClassURLDecoderUtil.decode(list, CHARSET);
        Gson gson = new Gson();
        String a = gson.toJson(list);
        if (BuildConfig.DEBUG)
            Log.d("ttt", a);
        return list;
    }

    public static <T> ArrayList<T> getArrayList(String json, Class<T> classOfT, int type) {
        ArrayList<T> list = convertJsonToList(json, classOfT);
//        if (type == 1) ClassURLDecoderUtil.decode(list, "");
//        else ClassURLDecoderUtil.decode(list, CHARSET);

        Gson gson = new Gson();
        String a = gson.toJson(list);
        if (BuildConfig.DEBUG)
            Log.d("ttt", a);
        return list;
    }

    /**
     * 将json数据转化成对象集合
     *
     * @param json
     * @param classOfT 与json数据相对应的java类
     * @return 若为null，则转化失败
     */
    public static <T> ArrayList<T> convertJsonToList(String json,
                                                     Class<T> classOfT) {
        if (json != null && "".equals(json) == false) {
            if (json.startsWith("[")) {
                ArrayList<T> listOfT = GsonUtil.jsonToList(json, classOfT);
                return listOfT;
            } else {
                ArrayList<T> listOfT = null;
                try {
                    T t = GsonUtil.jsonToObject(json, classOfT);
                    listOfT = new ArrayList<T>(1);
                    listOfT.add(t);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                return listOfT;
            }
        }
        return null;
    }

    // ******************************************************************************************
    // 私有方法

    private static Builder getBuilder(String url) {
        return new Builder().url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip");
    }

    private static Builder getBuilder(String baseUrl, RequestBody body,
                                      boolean isJsonBody) {
        if (body != null) {
            if (isJsonBody)
                return getBuilder(baseUrl).post(body).addHeader("Content-Type",
                        "application/json");
            else
                return getBuilder(baseUrl).post(body);
        } else
            return getBuilder(baseUrl);
    }

    private static RequestBody getRequestBody(String json) {
        if (json != null && "".equals(json) == false) {
            MediaType jsonType = MediaType.parse("application/json; charset="
                    + CHARSET);
            return RequestBody.create(jsonType, json);
        }
        return null;
    }

    private static RequestBody getRequestBody(HashMap<String, String> paramMap) {
        if (paramMap != null && !paramMap.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Entry<String, String> entry : paramMap.entrySet())
                builder.add(entry.getKey(), entry.getValue());
            return builder.build();
        }
        return null;
    }

    private static Request getRequest(String url) {
        return getBuilder(url).build();
    }

    private static Request getRequestByForm(String baseUrl, RequestBody formBody) {
        return getBuilder(baseUrl, formBody, false).build();
    }

    private static Request getRequestByJson(String baseUrl, RequestBody jsonBody) {
        return getBuilder(baseUrl, jsonBody, true).build();
    }

    private static String executeRequest(Request request) {
        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encode(String content) {
        if (content == null || "".equals(content))
            return "";
        else {
            try {
                return Uri.encode(content);
//				return URLEncoder.encode(content, CHARSET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    public static String getRequestPm(String method, String[] params,
                                      String token) {
        StringBuffer sb = new StringBuffer();
        sb.append("?method=").append(method).append("&token=").append(token)
                .append("&params=").append(createParams(params));
        return sb.toString();
    }

    public static String getRequestPmNoToken(String method, String[] params) {
        StringBuffer sb = new StringBuffer();
        sb.append("?method=").append(method).append("&params=")
                .append(createParams(params));
        return sb.toString();
    }

    /**
     * 将参数转化为434|45|2|32的样子
     *
     * @param strs
     * @return
     */
    private static String createParams(String[] strs) {
        String params = "";
        StringBuilder sb = new StringBuilder();
        if (strs != null) {
            for (String str : strs) {
                sb.append(encodeParams(str));
                sb.append("|");
            }
            params = sb.substring(0, sb.length() - 1);
        }
        return params;
    }

    /**
     * 对参数进行编码
     *
     * @param params
     * @return
     */
    private static String encodeParams(String params) {
//		try {
        return Uri.encode(params);
//			return URLEncoder.encode(params, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//		}
//		return params;
    }

    /**
     * 获取Json对象(已解除编码)
     *
     * @param str
     * @return
     */
    public static JsonElement getResult(String str) {
        str = decode(str);
        JsonParser parser = new JsonParser();
        return parser.parse(str == null ? "" : str.trim());
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    private static String decode(String str) {
        try {
            str = Uri.decode(str);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 未解除编码
     *
     * @param str
     * @return
     */
    public static JsonElement getResultNoDecode(String str) {
        JsonParser parser = new JsonParser();
        return parser.parse(str == null ? "" : str.trim());
    }


    //private static OkHttpClient mClient=new OkHttpClient();


    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public static String postWithForm(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            if (BuildConfig.DEBUG)
                Log.d("ttt", " post@ " + getUrlParamsByMap(actionUrl, paramsMap));

            if (mHttpClient == null) mHttpClient = new OkHttpClient();
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
           /* for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }*/


            for (Map.Entry<String, String> map : paramsMap.entrySet()) {
                String key = map.getKey().toString();
                String value = null;
                /**
                 * 判断值是否是空的
                 */
                if (map.getValue() == null) {
                    value = "";
                } else {
                    value = map.getValue();
                }
                /**
                 * 把key和value添加到formbody中
                 */
                builder.add(key, decodeUnicode(value));
                //Log.e("qqq","#:"+decodeUnicode(value));
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //创建一个请求
            final Request request = new Builder().url(actionUrl).post(formBody).build();
            //创建一个Call
            final Call call = mHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {
                //   Log.e("xxx", "response ----->" + response.body().string());
                return response.body().string();
            }
        } catch (Exception e) {
            // Log.e(TAG, e.toString());
            return "";
        }
        return "";
    }

    // Unicode转UTF-8
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx  
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
