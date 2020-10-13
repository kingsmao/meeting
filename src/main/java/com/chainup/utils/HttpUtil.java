package com.chainup.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Paceage:com.kingmao.cat.util
 * Description:
 * Date:2019/1/17
 * Author: KingMao
 **/
public class HttpUtil {

    public static String doHttpsGetJson(String Url)
    {
        String message = "";
        try
        {
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

    public static String sendGetHttp(String url) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String reslut = null;
        try {
            URL myUrl = new URL(url);
            // 打开连接
            HttpURLConnection conn = null;
            /**
             if ("https".equals(WeiXinAccountsManager.getHttpType())) {
             SSLContext sslContext = null;
             sslContext = SSLContext.getInstance("TLS");
             X509TrustManager[] xtmArray = new X509TrustManager[]{xtm};
             sslContext.init(null, xtmArray, new java.security.SecureRandom());
             HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
             HttpsURLConnection.setDefaultHostnameVerifier(hnv);

             conn = (HttpsURLConnection) myUrl.openConnection();
             } else {
             conn = (HttpURLConnection) myUrl.openConnection();
             }
             **/
            conn = (HttpURLConnection) myUrl.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/application/x-java-serialized-object");
            // 设置请求方式为GET
            conn.setRequestMethod("GET");
            conn.connect();
            // 将数据流信息转存在byte数组中
            InputStream in = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            byte[] total = null;
            while ((len = in.read(b)) > 0) {
                total = byteMerger(total, b, len);
            }

            //关闭输入流
            if (null != in) {
                in.close();
            }

            reslut = new String(total, "utf-8");
            //查看结果里面是不是有AccessToken错误信息，如果有的话就直接重置AccessToken。
        } catch (Exception e) {
            throw new RuntimeException("调用api接口发生 错误", e);
        }
        return reslut;
    }

    public static String executeJsonParamHttpPost(String url, String textMsg) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(textMsg, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                httpPost.abort();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return result;
    }

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2, int len) {
        byte[] byte_3 = null;
        if (byte_1 == null) {
            byte_3 = new byte[len];
            System.arraycopy(byte_2, 0, byte_3, 0, len);
        } else {
            byte_3 = new byte[byte_1.length + len];
            System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
            System.arraycopy(byte_2, 0, byte_3, byte_1.length, len);
        }
        return byte_3;
    }
}
