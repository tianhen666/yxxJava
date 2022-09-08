package com.lechi.yxx.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class HttpUtils {

    private static Certificate cert = null;

    private static SSLConnectionSocketFactory socketFactory;//私密连接工厂

    private static TrustManager manager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    /**
     * 是否忽略证书.https网站一般情况下使用了安全系数较低的SHA-1签名，因此首先我们在调用SSL之前需要重写验证方法，取消检测SSL。
     */
    private static void enableSSl(boolean isIgnore) {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            if (isIgnore) {
                context.init(null, new TrustManager[]{manager}, null);
            } else {
                context.init(null, new TrustManager[]{httpsManager}, null);
            }
            socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier
                    .INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 证书获取
     * @param certificateUrl 证书地址.
     */
    private static void initHttpsCertificate(String certificateUrl) {
        try {
            FileInputStream fis = new FileInputStream(certificateUrl);
            BufferedInputStream bis = new BufferedInputStream(fis);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            while (bis.available() > 0) {
                cert = cf.generateCertificate(bis);
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    private static TrustManager httpsManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] { (X509Certificate) cert };
        }
    };


    /**
     * get
     *
     * @throws Exception
     */
    public static  String Method(Map<String,String> map, String token,String creat_url) throws IOException {
        URL restURL = new URL(creat_url);
        JSONObject jsonObject = new JSONObject();
        // jsonObject.put("Type","ChatRoom");
        jsonObject.put("no",map.get("no"));

        String s1 = jsonObject.toJSONString();
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(s1);
        ps.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String str = null;
        while((line = br.readLine()) != null ){
            str = line;
        }
        br.close();
//        String str =JSONObject.parseObject(str).getString("GroupId");
        return str;



    }


    /**
     * 发送post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject DO_POST(String url, JSONObject json){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }



    public static R getExpress(String no) throws  Exception{
        String host = "https://wuliu.market.alicloudapi.com";       //【1】请求地址  支持http 和 https 及 WEBSOCKET
        String path = "/kdi";                                     //【2】后缀
        String appcode = "70b8fac5a1e84b478b3115f0f3e6e7de";                             //【3】AppCode  你自己的AppCode 在买家中心查看
        //String no = "75315515586562";                                     //【4】参数，具体参照api接口参数
        //String type = "zto";                                            //【5】参数，具体参照api接口参数
        String urlSend = host + path + "?no=" + no ;   //【6】拼接请求链接
        /*【1】 ~ 【6】 需要修改为对应的 可以参考产品详情 */
        URL url = new URL(urlSend);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "APPCODE " + appcode);//格式Authorization:APPCODE (中间是英文空格)
        int httpCode = httpURLConnection.getResponseCode();
        if (httpCode==200){
            String json = read(httpURLConnection.getInputStream());
            System.out.println("/* 获取服务器响应状态码 200 正常；400 权限错误 ； 403 次数用完； */ ");
            System.out.println(httpCode);
            System.out.println("/* 获取返回的json   */ ");
            System.out.print(json);
            JSONObject jo = JSONObject.parseObject(json);
            int status = jo.getInteger("status");
            if (status==0){
                String str = jo.getString("result");
                JSONObject jo1 = JSONObject.parseObject(str);
                String strList = jo1.getString("list");
                JSONArray jsonArray = JSONArray.parseArray(strList);
                return R.ok(jsonArray);
            }
            return R.failed("单号输入有误或暂无物流信息");

        }
        return R.failed("");




    }
    /*
        读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }




    /**
     * httpClient get http 请求.
     * @param url     请求路径.
     * @param values  请求参数.
     * @return response 请求结果状态.
     */
    public static String doGet(String url, List<NameValuePair> values) throws IOException {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        //最新版的httpClient使用实现类的是closeableHTTPClient,以前的default作废了.设置可关闭的httpclient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        StringBuilder urlBuffer = new StringBuilder(url);
        if (!url.contains("?")) {
            urlBuffer.append("?");
        }

        if (values != null) {
            for (NameValuePair nameValuePair : values) {
                urlBuffer.append("&").append(nameValuePair.getName()).append("=")
                        .append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
            }
        }

        HttpGet get = new HttpGet(urlBuffer.toString());
        CloseableHttpResponse response = httpClient.execute(get);
        return getResponseContent(response);
    }
    /**
     * get请求,设置headers
     * get
     * @param path
     * @param values
     * @param headers
     * @return
     * @throws Exception
     */
    public static String doGet(String path, List<NameValuePair> values,
                               Map<String, String> headers)
            throws Exception {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        //最新版的httpClient使用实现类的是closeableHTTPClient,以前的default作废了.设置可关闭的httpclient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        StringBuilder urlBuffer = new StringBuilder(path);
        if (!path.contains("?")) {
            urlBuffer.append("?");
        }
        if (values != null) {
            for (NameValuePair nameValuePair : values) {
                urlBuffer.append("&").append(nameValuePair.getName()).append("=")
                        .append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
            }
        }
        HttpGet request = new HttpGet(urlBuffer.toString());
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(request);
        return getResponseContent(response);
    }

    /**
     * httpClient post http 请求.
     * @param url     请求路径.
     * @param values  请求参数.
     * @return response 请求结果状态.
     */
    public static String doPost(String url, List<NameValuePair> values) throws IOException {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        HttpPost post = new HttpPost(url);

        if (values != null) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List<? extends NameValuePair>) values,"UTF-8");
            //StringEntity entity = new StringEntity(values);
            post.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(post);
        return getResponseContent(response);

    }


    /**
     * http请求 数据流传参
     * @param url  请求地址
     * @param json json参数
     * @return
     * @throws IOException
     */
    public static String doPostForFlow(String url,String json) throws IOException{
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        if(StringUtils.isNotBlank(json)){
            StringEntity entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return getResponseContent(response);
    }


    /**
     * httpClient post http 请求.
     * @param url
     * @param values
     * @param headers
     * @return
     * @throws IOException
     */
    public static String doPost(String url, List<NameValuePair> values, Map<String, String> headers) throws IOException {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        HttpPost post = new HttpPost(url);
        String charset = "UTF-8";
        if (values != null) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values,charset);
            post.setEntity(entity);
        }
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), e.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(post);
        return getResponseContent(response);

    }

    /**
     * httpClient get https 请求.
     * @param url     请求路径.
     * @param values  请求参数.
     * @return response 请求结果状态.
     */
    public static String ignoreGetForHttps(String url, List<NameValuePair> values, boolean isIgnoreSSL) throws
            IOException {
        enableSSl(isIgnoreSSL);
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
                        AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                .build();
        //创建可用Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        //创建ConnectionManager，添加Connection配置信息
        PoolingHttpClientConnectionManager connectionManager = new
                PoolingHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();

        StringBuilder urlBuffer = new StringBuilder(url);
        if (!url.contains("?")) {
            urlBuffer.append("?");
        }

        if (values != null) {
            for (NameValuePair nameValuePair : values) {
                urlBuffer.append("&").append(nameValuePair.getName()).append("=")
                        .append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
            }
        }

        HttpGet get = new HttpGet(urlBuffer.toString());

        CloseableHttpResponse response = httpClient.execute(get);

        return getResponseContent(response);
    }

    /**
     * httpClient get https 请求.
     * @param url     请求路径.
     * @return response 请求结果状态.
     */
    public static String ignoreGetForHttps(String url, boolean isIgnoreSSL) throws
            IOException {
        enableSSl(isIgnoreSSL);
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
                        AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                .build();
        //创建可用Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        //创建ConnectionManager，添加Connection配置信息
        PoolingHttpClientConnectionManager connectionManager = new
                PoolingHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();

        HttpGet get = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(get);

        return getResponseContent(response);
    }


    /**
     * httpClient post https 请求.
     * @param url     请求路径.
     * @param values  请求参数.
     * @return response 请求结果状态.
     */
    public static String ignorePostForHttps(String url, List<NameValuePair> values, boolean isIgnoreSSL)
            throws IOException {
        enableSSl(isIgnoreSSL);
        //首先设置全局的标准cookie策略
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
                        AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                .build();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new
                PoolingHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();

        HttpPost post = new HttpPost(url);

        if (values != null) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values,Charset.forName("UTF-8"));
            post.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(post);
        return getResponseContent(response);
    }

    /**
     * 获取请求返回值.
     */
    private static String getResponseContent(CloseableHttpResponse response)
            throws IOException {
        try {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = response.getEntity();
                httpEntity = new BufferedHttpEntity(httpEntity);
                return EntityUtils.toString(httpEntity);
            }
        } finally {
            response.close();
        }
        return null;
    }
    public static String post(String URL,JSONObject json) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);

        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                System.out.println("请求服务器成功，做相应处理");

            } else {

                System.out.println("请求服务端失败");

            }


        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }


    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException{
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        //发送请求
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity !=null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        httpGet.releaseConnection();//释放连接
        return jsonObject;

    }







    public static String doHttpsGetJson(String Url)
    {
        String message = "";
        try
        {
            System.out.println("doHttpsGetJson");//TODO:dd
            URL urlGet = new URL(Url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");      //必须是get方式请求    24
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒28
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒29 30
            http.connect();
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            message=new String(jsonBytes,"UTF-8");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return message;
    }


}
