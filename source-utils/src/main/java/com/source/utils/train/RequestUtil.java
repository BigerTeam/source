package com.source.utils.train;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class RequestUtil {

    public static final String GET = "get";

    public static final String POST = "post";

    public static final String UTF8 = "UTF-8";

    public static final String GB2312 = "GB2312";

    //12306域名，勿修改
    private static final String Net12306 = "kyfw.12306.cn";

    /**
     * Content-Type>>application/json
     */
    public static final String APPLICATION_JSON = "application/json";

    /**
     * Content-Type>>multipart/form-data
     */
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * Content-Type>>application/x-www-form-urlencoded; charset=UTF-8
     */
    public static final String X_WWW_FORM_URLENCODED_UTF8 = "application/x-www-form-urlencoded; charset=UTF-8";

    /**
     * 非请求12306的GET方法
     */
    public static String getNot12306(String url, String charset) {
        return IS12306(url) ? "" : get(url, charset, new HashMap<String, String>(), 0, new JSONObject());
    }

    /**
     * 非请求12306的POST方法
     */
    public static String postNot12306(String url, String param, String charset) {
        return IS12306(url) ? "" : post(url, param, charset, new HashMap<String, String>(), 0, new JSONObject());
    }

    /**
     * @param url 请求链接，以http或https开头
     * @param param 请求数据
     * @param charset 字符集，不填视为"utf-8"
     * @param header 请求头
     * @param timeout 超时时间，大于0时有效
     * @param accountInfo 账号登录时12306服务器IP等（如：淘宝Cookie方式订单），非空时指定请求该12306服务器
     */
    public static String post(String url, String param, String charset, Map<String, String> header, int timeout,
            JSONObject accountInfo) {
        return submit(url, param, "post", charset, header, timeout, accountInfo);
    }

    /**
     * @param url 请求链接，以http或https开头
     * @param charset 字符集，不填视为"utf-8"
     * @param header 请求头
     * @param timeout 超时时间，大于0时有效
     * @param accountInfo 账号登录时12306服务器IP等（如：淘宝Cookie方式订单），非空时指定请求该12306服务器
     */
    public static String get(String url, String charset, Map<String, String> header, int timeout, JSONObject accountInfo) {
        return submit(url, "", "get", charset, header, timeout, accountInfo);
    }

    /**
     * 空JSONObject
     */
    public static JSONObject NullJson() {
        return new JSONObject();
    }

    /**
     * 空请求头
     */
    public static Map<String, String> NullHeader() {
        return new HashMap<String, String>();
    }

    /**
     * 只有Cookie的请求头
     */
    public static Map<String, String> CookieHeader(String Cookie) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Cookie", Cookie);
        return map;
    }

    /**
     * 网页公共请求头
     * @param isAjaxRequest 网页异步请求 --> X-Requested-With:XMLHttpRequest
     * @param completeAccept 完整的Accept --> Accept:text/html,application/xhtml+xml,application/xml;q=0.9...
     * @param IfModifiedSince true时 --> If-Modified-Since：0
     * @return HeaderMap，key：请求头名称，value：请求头值
     */
    public static Map<String, String> getRequestHeader(boolean isAjaxRequest, boolean completeAccept,
            boolean IfModifiedSince) {
        return getRequestHeader("", "", "", "", isAjaxRequest, completeAccept, IfModifiedSince);
    }

    /**
     * 网页公共请求头
     * @param Cookie
     * @param Referer
     * @param ContentType，如application/x-www-form-urlencoded; charset=UTF-8
     * @param ContentLength 请求参数长度
     * @param isAjaxRequest 网页异步请求 --> X-Requested-With:XMLHttpRequest
     * @param completeAccept 完整的Accept --> Accept:text/html,application/xhtml+xml,application/xml;q=0.9...
     * @param IfModifiedSince true时 --> If-Modified-Since：0
     * @return HeaderMap，key：请求头名称，value：请求头值
     */
    public static Map<String, String> getRequestHeader(String Cookie, String Referer, String ContentType,
            String ContentLength, boolean isAjaxRequest, boolean completeAccept, boolean IfModifiedSince) {
        Map<String, String> map = new HashMap<String, String>();
        //PUT
        if (!StringIsNull(Cookie)) {
            map.put("Cookie", Cookie);
        }
        if (!StringIsNull(Referer)) {
            map.put("Referer", Referer);
        }
        if (IfModifiedSince) {
            map.put("If-Modified-Since", "0");
        }
        if (!StringIsNull(ContentType)) {
            map.put("Content-Type", ContentType);
        }
        if (!StringIsNull(ContentLength)) {
            map.put("Content-Length", ContentLength);
        }
        if (isAjaxRequest) {
            map.put("X-Requested-With", "XMLHttpRequest");
        }
        map.put("DNT", "1");
        map.put("Pragma", "no-cache");
        map.put("Host", "kyfw.12306.cn");
        map.put("Accept-Language", "zh-CN");
        map.put("Connection", "keep-alive");
        map.put("Cache-Control", "no-cache");
        map.put("Accept-Encoding", "gzip, deflate");
        map.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        map.put("Accept", completeAccept ? "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
                : "*/*");
        //返回
        return map;
    }

    //获取账号信息，如账号名称、密码、对应登录IP等
    public static String getLoginName(JSONObject accountInfo) {
        return getJsonString(accountInfo, "loginName");
    }

    public static String getLoginPassWord(JSONObject accountInfo) {
        return getJsonString(accountInfo, "loginPassWord");
    }

    public static String getLogin12306Ip(JSONObject accountInfo) {
        return getJsonString(accountInfo, "login12306Ip");
    }

    /**获取当前日期，格式为yyyy-MM-dd*/
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**计算两个yyyy-MM-dd日期间的天数*/
    public static int getSubDays(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date s = sdf.parse(start);
            Date e = sdf.parse(end);
            long days = (e.getTime() - s.getTime()) / 1000 / 60 / 60 / 24;
            return Integer.parseInt(String.valueOf(days));
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static boolean StringIsNull(String param) {
        return param == null || "".equals(param.trim()) ? true : false;
    }

    public static String getJsonString(JSONObject obj, String key) {
        if (obj == null || StringIsNull(key) || !obj.containsKey(key)) {
            return "";
        }
        else {
            String value = obj.getString(key);
            return StringIsNull(value) ? "" : value;
        }
    }

    /**float加法*/
    public static float floatAdd(float a, float b) {
        BigDecimal x = new BigDecimal(a + "");
        BigDecimal y = new BigDecimal(b + "");
        return x.add(y).floatValue();
    }

    /**float减法*/
    public static float floatSubtract(float a, float b) {
        BigDecimal x = new BigDecimal(a + "");
        BigDecimal y = new BigDecimal(b + "");
        return x.subtract(y).floatValue();
    }

    /**float乘法 */
    public static float floatMultiply(float a, float b) {
        BigDecimal x = new BigDecimal(a + "");
        BigDecimal y = new BigDecimal(b + "");
        return x.multiply(y).floatValue();
    }

    //{"validateMessagesShowId":"_validatorMessage","url":"login/init","status":true,"httpstatus":200,"messages":["用户未登录"],"validateMessages":{}}
    public static String getErrorMsg(JSONObject obj12306) {
        StringBuffer buf = new StringBuffer();
        //错误信息
        JSONArray messages = obj12306.containsKey("messages") ? obj12306.getJSONArray("messages") : new JSONArray();
        int size = messages.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                buf.append(messages.get(i).toString());
            }
            else {
                buf.append(messages.get(i).toString() + "、");
            }
        }
        String ret = buf.toString();
        if (ret.endsWith("。") || ret.endsWith(".")) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    /**
     * Cookie数组拼接为字符串，银联支付拼Cookie可能存在问题，后续如果上线，要先测试
     */
    private static String JoinCookie(JSONArray cookies, boolean is12306) {
        StringBuffer buf = new StringBuffer();
        //Set-Cookie:BIGipServerotn=953614602.24610.0000; path=/
        //Set-Cookie:JSESSIONID=0A01D738603F6E8BD646DD5F9C2C1B0CCCB6E392FD; Path=/otn
        String JSESSIONID = "";
        //循环
        for (int i = 0; i < cookies.size(); i++) {
            //可能多个
            String[] cookieArray = cookies.getString(i).split(";");
            //循环拼接
            for (String cookie : cookieArray) {
                if (is12306) {
                    //临时
                    String temp = cookie.toLowerCase().trim();
                    //跳过
                    if (temp.startsWith("path")) {
                        continue;
                    }
                    //ID
                    if (StringIsNull(JSESSIONID) && temp.toUpperCase().startsWith("JSESSIONID")) {
                        JSESSIONID = cookie.trim() + "; ";
                    }
                    else {
                        buf.append(cookie.trim() + "; ");
                    }
                }
                else {
                    buf.append(cookie.trim() + "; ");
                }
            }
        }
        String result = (JSESSIONID + buf.toString()).trim();
        //返回
        return result.endsWith(";") ? result.substring(0, result.length() - 1) : result;
    }

    /**
     * 请求的12306
     */
    private static boolean IS12306(String url) {
        return !StringIsNull(url) && url.trim().toLowerCase().startsWith("https://" + Net12306 + "/");
    }

    /**
     * @param url 请求链接，以http或https开头
     * @param data 请求数据，post请求有效
     * @param reqType 请求类型，get或post，不填视为post
     * @param charset 字符集，不填视为"utf-8"
     * @param header 请求头
     * @param timeout 超时时间，大于0时有效
     * @param accountInfo 账号登录时12306服务器IP等（如：淘宝Cookie方式订单），非空时指定请求该12306服务器
     */
    private static String submit(String url, String data, String reqType, String charset, Map<String, String> header,
            int timeout, JSONObject accountInfo) {
        //获取响应码
        int statusCode = -1;
        //响应的跳转地址
        String Location = "-1";
        //为空
        if (StringIsNull(url)) {
            return "";
        }
        //一分钟
        if (timeout <= 0) {
            timeout = 60 * 1000;
        }
        if (header == null) {
            header = NullHeader();
        }
        //12306
        boolean is12306 = IS12306(url);
        //记日志>>12306
        boolean writeLog = url.contains(".12306.cn");
        //取Cookie
        boolean getResponseCookie = "true".equalsIgnoreCase(header.get("GetResponseCookie"));
        //移除KEY
        if (getResponseCookie) {
            header.remove("GetResponseCookie");
        }
        //日志
        String logName = "RequestUtil_Submit_Info";
        //时间
        long startTime = System.currentTimeMillis();
        //标示
        String remark = startTime + ">>" + (new Random().nextInt(9000000) + 1000000);
        //日志
        if (writeLog) {
            WriteLog.write(logName, remark + ">>" + url + ">>" + reqType + ">>ip12306>>" + accountInfo + ">>param>>"
                    + data + ">>header>>" + header);
        }
        //编码
        url = url.trim();
        charset = StringIsNull(charset) ? "UTF-8" : charset.trim();
        //HTTPS
        if (url.startsWith("https")) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL"); //或SSL
                X509TrustManager[] xtmArray = new X509TrustManager[] { new myX509TrustManager() };
                sslContext.init(null, xtmArray, new java.security.SecureRandom());
            }
            catch (GeneralSecurityException e) {
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new myHostnameVerifier());
        }
        InputStream in = null;
        URLConnection con = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        JSONArray cookies = new JSONArray();
        StringBuffer res = new StringBuffer();
        try {
            //切换DNS
            changeDnsCachePolicy(url, accountInfo);
            //代理信息
            String proxyHost = header.get("proxyHost");
            String proxyPort = header.get("proxyPort");
            //使用代理
            if (!StringIsNull(proxyHost) && !StringIsNull(proxyPort)) {
                con = (new URL(url)).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer
                        .parseInt(proxyPort))));
            }
            else {
                con = (new URL(url)).openConnection();
            }
            con.setDoOutput(true);
            con.setUseCaches(false);
            if (timeout > 0) {
                con.setReadTimeout(timeout);
                con.setConnectTimeout(timeout);
            }
            for (String key : header.keySet()) {
                con.setRequestProperty(key, header.get(key));
            }
            if ("get".equalsIgnoreCase(reqType)) {
                con.connect();
            }
            else {
                out = new DataOutputStream(con.getOutputStream());
                out.write(data.getBytes(charset));
            }
            in = con.getInputStream();
            //状态码及Location
            try {
                HttpURLConnection conn = (HttpURLConnection) con;
                statusCode = conn.getResponseCode();
                if (301 == statusCode || 302 == statusCode) {
                    Location = conn.getHeaderField("Location");
                }
            }
            catch (Exception e) {
                if (writeLog) {
                }
            }
            //判断是否压缩
            if ("gzip".equalsIgnoreCase(con.getHeaderField("Content-Encoding"))) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(in), charset));
            }
            else {
                reader = new BufferedReader(new InputStreamReader(in, charset));
            }
            String lineTxt = null;
            while ((lineTxt = reader.readLine()) != null) {
                res.append(lineTxt);
            }
            //取Response的Cookie
            if (getResponseCookie) {
                List<String> SetCookies = con.getHeaderFields().get("Set-Cookie");
                //存在Cookie
                if (SetCookies != null && SetCookies.size() > 0) {
                    for (String SetCookie : SetCookies) {
                        cookies.add(SetCookie);
                    }
                }
            }
        }
        catch (Exception e) {
            //重置
            res = new StringBuffer();
            //错误日志
            if (writeLog) {
            }
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e) {
            }
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            }
            catch (Exception e) {
            }
            //清理DNS缓存
            cleanDnsCachePolicy(url, accountInfo);
        }
        String result = res.toString();
        //要Cookie
        if (getResponseCookie) {
            JSONObject obj = new JSONObject();
            obj.put("result", result);
            obj.put("cookies", cookies);
            obj.put("cookie", JoinCookie(cookies, is12306));
            result = obj.toString();
        }
        //结束
        long endTime = System.currentTimeMillis();
        //日志
        if (writeLog) {
            WriteLog.write(logName, remark + ">>" + url + ">>time>>" + (endTime - startTime) + ">>statusCode>>"
                    + statusCode + ((Location != null && !"-1".equals(Location)) ? ">>Location>>" + Location : "")
                    + ">>response>>" + result);
        }
        //返回
        return result;
    }

    /**
     * 淘宝托管
     */
    private static boolean taobao(JSONObject accountInfo) {
        //OS
        String osname = System.getProperty("os.name");
        osname = StringIsNull(osname) ? "" : osname;
        //服务器
        int repType = accountInfo.getIntValue("repType");
        String serverIp = accountInfo.getString("serverIp");
        int serverPort = accountInfo.getIntValue("serverPort");
        String serverPassword = accountInfo.getString("serverPassword");
        //逻辑判断
        return repType == 1 && !StringIsNull(serverIp) && serverPort > 0 && !StringIsNull(serverPassword)
                && osname.contains("Linux");
    }

    /**
     * 切换DNS
     * @param url 请求地址
     * @param ip12306 12306服务器IP
     */
    @SuppressWarnings({ "rawtypes" })
    private static void changeDnsCachePolicy(String url, JSONObject accountInfo) throws Exception {
        //12306
        boolean is12306 = IS12306(url);
        //IP12306
        String ip12306 = getLogin12306Ip(accountInfo);
        //不符合条件
        if (!is12306 || StringIsNull(ip12306) || ip12306.split("\\.").length != 4) {
            return;
        }
        //确保缓存必须存在
        String oldIp = InetAddress.getByName(Net12306).getHostAddress();
        //淘宝托管订单服务器
        if (taobao(accountInfo)) {
            //IP不一致，更改HOST
            if (!ip12306.equals(oldIp)) {
                //更改逻辑
                modifyHosts(accountInfo);
                //清理DNS缓存
                cleanDnsCachePolicy();
            }
            return;//中断后续操作
        }
        //Class
        Class inetAddressClass = java.net.InetAddress.class;
        Field cacheField = inetAddressClass.getDeclaredField("addressCache");
        cacheField.setAccessible(true);
        //Object
        Object obj = cacheField.get(inetAddressClass);
        Class cacheClazz = obj.getClass();
        Field cacheMapField = cacheClazz.getDeclaredField("cache");
        cacheMapField.setAccessible(true);
        Map cacheMap = (Map) cacheMapField.get(obj);
        Object mapItem = cacheMap.get(Net12306);
        Class cacheEntityClazz = mapItem.getClass();
        //集合
        Field[] addressFields = cacheEntityClazz.getDeclaredFields();
        //循环
        for (Field addressField : addressFields) {
            //名称
            String FieldName = addressField.getName();
            //符合条件
            if ("address".equals(FieldName) || "addresses".equals(FieldName)) {
                addressField.setAccessible(true);
                InetAddress[] targetHosts = (InetAddress[]) addressField.get(mapItem);
                //以"."拆分字符串
                String[] ips = ip12306.split("\\.");
                byte[] ipByte = new byte[ips.length];
                for (int i = 0; i < ips.length; i++) {
                    ipByte[i] = (byte) (Integer.parseInt(ips[i]) & 0xFF);
                }
                targetHosts[0] = InetAddress.getByAddress(Net12306, ipByte);
                //中断
                break;
            }
        }
    }

    /**
     * 修改HOST
     */
    private static void modifyHosts(JSONObject accountInfo) throws Exception {
        String path = "/etc/hosts";
        String cmd = "cat /dev/null > " + path;
        cmd += "\n echo '127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4' >> " + path;
        cmd += "\n echo '::1         localhost localhost.localdomain localhost6 localhost6.localdomain6' >> " + path;
        cmd += "\n echo '" + getLogin12306Ip(accountInfo) + " kyfw.12306.cn' >> " + path;
        //执行CMD
        Session session = null;
        ChannelExec openChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession("root", accountInfo.getString("serverIp"), accountInfo.getIntValue("serverPort"));
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(accountInfo.getString("serverPassword"));
            session.connect();
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(cmd);
            openChannel.getExitStatus();
            openChannel.connect();
        }
        catch (Exception e) {
        }
        finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 清理DNS
     */
    @SuppressWarnings("rawtypes")
    private static void cleanDnsCachePolicy() {
        try {
            //Class
            Class inetAddressClass = java.net.InetAddress.class;
            Field cacheField = inetAddressClass.getDeclaredField("addressCache");
            cacheField.setAccessible(true);
            //Object
            Object obj = cacheField.get(inetAddressClass);
            Class cacheClazz = obj.getClass();
            Field cacheMapField = cacheClazz.getDeclaredField("cache");
            Field cachePolicyField = cacheClazz.getDeclaredField("type");
            cacheMapField.setAccessible(true);
            cachePolicyField.setAccessible(true);
            Map cacheMap = (Map) cacheMapField.get(obj);
            //Remove
            cacheMap.remove(Net12306);
        }
        catch (Exception e) {
        }
    }

    /**
     * 清理DNS
     */
    public static void cleanDnsCachePolicy(String url, JSONObject accountInfo) {
        try {
            //12306
            boolean is12306 = IS12306(url);
            //IP12306
            String ip12306 = getLogin12306Ip(accountInfo);
            //不符合条件，REP类型>>1为淘宝客人账号专用
            if (!is12306 || StringIsNull(ip12306) || ip12306.split("\\.").length != 4 || taobao(accountInfo)) {
                return;
            }
            //清理DNS
            cleanDnsCachePolicy();
        }
        catch (Exception e) {
        }
    }

    /**
     * 使用代理
     */
    public static String submitProxy(String url, String data, String reqType, String charset,
            Map<String, String> header, int timeout, JSONObject accountInfo, String proxyHost, int proxyPort) {
        //为空
        if (header == null) {
            header = new HashMap<String, String>();
        }
        //赋值代理IP及端口
        header.put("proxyHost", proxyHost);
        header.put("proxyPort", String.valueOf(proxyPort));
        //调用submit()方法
        return submit(url, data, reqType, charset, header, timeout, accountInfo);
    }

    /**
     * 获取请求结果和Cookie
     */
    public static JSONObject responseCookie(String url, String data, String reqType, String charset,
            Map<String, String> header, int timeout, JSONObject accountInfo) {
        JSONObject object = new JSONObject();
        //为空
        if (header == null) {
            header = new HashMap<String, String>();
        }
        //赋值要cookie
        header.put("GetResponseCookie", "true");
        //调用submit()方法
        String result = submit(url, data, reqType, charset, header, timeout, accountInfo);
        //结果非空
        if (!StringIsNull(result)) {
            object = JSONObject.parseObject(result);
        }
        return object == null ? new JSONObject() : object;
    }

    /**
     * 结果+特殊字符串+Cookie
     * @param splitString 特殊字符串
     * @remark 之前登录等地方的Cookie拼法，建议后续走responseCookie()方法
     */
    public static String resultAndCookie(String url, String data, String reqType, String charset,
            Map<String, String> header, int timeout, JSONObject accountInfo, String splitString) {
        //Cookie
        JSONObject json = responseCookie(url, data, reqType, charset, header, timeout, accountInfo);
        //返回结果
        return json.getString("result") + splitString + json.getString("cookie");
    }

    /**
     * GET请求>>打码等地保存图片调用，其他业务勿使，注意后续关闭流
     */
    public static URLConnection getURLConnection(String url, Map<String, String> header, int timeout,
            JSONObject accountInfo) {
        url = url.trim();
        if (url.startsWith("https")) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL"); //或SSL
                X509TrustManager[] xtmArray = new X509TrustManager[] { new myX509TrustManager() };
                sslContext.init(null, xtmArray, new java.security.SecureRandom());
            }
            catch (GeneralSecurityException e) {
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new myHostnameVerifier());
        }
        URLConnection con = null;
        //HEADER
        header = header == null ? NullHeader() : header;
        try {
            //切换DNS
            changeDnsCachePolicy(url, accountInfo);
            //代理信息
            String proxyHost = header.get("proxyHost");
            String proxyPort = header.get("proxyPort");
            //使用代理
            if (!StringIsNull(proxyHost) && !StringIsNull(proxyPort)) {
                con = (new URL(url)).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer
                        .parseInt(proxyPort))));
            }
            else {
                con = (new URL(url)).openConnection();
            }
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setReadTimeout(timeout <= 0 ? 60 * 1000 : timeout);
            con.setConnectTimeout(timeout <= 0 ? 60 * 1000 : timeout);
            for (String key : header.keySet()) {
                con.setRequestProperty(key, header.get(key));
            }
            con.connect();
        }
        catch (Exception e) {

        }
        return con;
    }

    //        private static class myX509TrustManager implements X509TrustManager {
    //            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
    //    
    //            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
    //    
    //            public X509Certificate[] getAcceptedIssuers() {
    //                return new X509Certificate[] {};
    //            }
    //        }

    //    private static class myHostnameVerifier implements HostnameVerifier {
    //        public boolean verify(String hostname, SSLSession session) {
    //            return true;
    //        }
    //    }

}