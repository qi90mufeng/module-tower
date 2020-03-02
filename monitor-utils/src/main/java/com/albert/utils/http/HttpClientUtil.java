package com.albert.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLInitializationException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description 支持POST和GET请求, 支持SSL
 * @description HttpClient 4.5.2
 * @description fastjson 1.2.44
 * <p>
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.5.2</version>
 * </dependency>
 * <p>
 * <dependency>
 * <groupId>com.alibaba</groupId>
 * <artifactId>fastjson</artifactId>
 * <version>1.2.44</version>
 * </dependency>
 * @since 1.0
 */
public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connManager;

    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 出错返回结果
     */
    private static final String RESULT = "-1";

    private static final String HTTP_HEADER_REQUEST_ID = "X-RequestId";

    /**
     * 初始化连接池管理器,配置SSL
     */
    static {
        if (connManager == null) {

            try {
                // 创建ssl安全访问连接
                // 获取创建ssl上下文对象
                /**
                 * 使用带证书的定制SSL访问
                 */
//                File authFile = new File("C:/Users/lynch/Desktop/my.keystore");
//                SSLContext sslContext = getSSLContext(false, authFile, "mypassword");
                SSLContext sslContext = getSSLContext(true, null, "");

                // 注册
                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslContext))
                        .build();

                // ssl注册到连接池
                connManager = new PoolingHttpClientConnectionManager(registry);
                connManager.setMaxTotal(1000);  // 连接池最大连接数
                connManager.setDefaultMaxPerRoute(20);  // 每个路由最大连接数

            } catch (SSLInitializationException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取客户端连接对象
     *
     * @param useProxy     是否使用代理.
     * @param proxyIp      代理ip.
     * @param proxyPort    代理ip的端口.
     * @param authUserName 代理认证用户名.
     * @param authPasswd   代理认证密码.
     * @param timeOut      超时时间.
     * @return
     */
    public static CloseableHttpClient getHttpClient(boolean useProxy, String proxyIp, Integer proxyPort, String authUserName, String authPasswd, Integer timeOut) {
        if (useProxy && StringUtils.isEmpty(proxyIp)) {
            useProxy = false;
        }
        RequestConfig requestConfig = null;
        CloseableHttpClient httpClient = null;
        // 配置请求参数
        if (useProxy) {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .setSocketTimeout(timeOut)
                    .setProxy(new HttpHost(proxyIp, proxyPort))
                    .build();
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .setSocketTimeout(timeOut)
                    .build();
        }
        // 配置超时回调机制
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        if (useProxy) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(proxyIp, proxyPort),
                    new UsernamePasswordCredentials(authUserName, authPasswd));
            httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(retryHandler)
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();
        } else {
            httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(retryHandler)
                    .build();
        }
        return httpClient;
    }

    /**
     * 获取客户端连接对象.不使用代理.
     *
     * @param timeOut 超时时间
     * @return
     */
    public static CloseableHttpClient getHttpClient(Integer timeOut) {
        return getHttpClient(false, "", null, "", "", timeOut);
    }

    /**
     * 获取SSL上下文对象,用来构建SSL Socket连接
     *
     * @param isDeceive 是否绕过SSL
     * @param creFile   整数文件,isDeceive为true 可传null
     * @param crePwd    整数密码,isDeceive为true 可传null, 空字符为没有密码
     * @return SSL上下文对象
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CertificateException
     */
    private static SSLContext getSSLContext(boolean isDeceive, File creFile, String crePwd) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException {

        SSLContext sslContext = null;

        if (isDeceive) {
            sslContext = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        } else {
            if (null != creFile && creFile.length() > 0) {
                if (null != crePwd) {
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(new FileInputStream(creFile), crePwd.toCharArray());
                    sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
                } else {
                    throw new SSLHandshakeException("整数密码为空");
                }
            }
        }

        return sslContext;

    }

    /**
     * post请求,支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, Map<String, Object> headers, Map<String, Object> params, Integer timeOut, boolean isStream) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }

        return getResult(httpPost, timeOut, isStream);

    }

    /**
     * post请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, Map<String, Object> params, Integer timeOut) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }

        return getResult(httpPost, timeOut, false);

    }
    /**
     * post请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPostJson(String url, Map<String, Object> params, Integer timeOut) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(JSON.toJSONString(params),"utf-8");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        return getResult(httpPost, timeOut, false);
    }


    /**
     * post请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPostString(String url, Map<String, String> params, Integer timeOut) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPSString(params), ENCODING));
        }

        return getResult(httpPost, timeOut, false);

    }

    /**
     * post请求,支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }

        return getResult(httpPost, timeOut, isStream);

    }

    /**
     * post请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, JSONObject params, Integer timeOut) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }

        return getResult(httpPost, timeOut, false);

    }


    /**
     * post请求,支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, JSONObject headers, String params, Integer timeOut, boolean isStream) throws UnsupportedEncodingException {

        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new StringEntity(params, ENCODING));
        }

        return getResult(httpPost, timeOut, isStream);

    }


    /**
     * get请求,支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, Map<String, Object> headers, Map<String, Object> params, Integer timeOut, boolean isStream) throws URISyntaxException {

        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }

        // 创建post请求
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        return getResult(httpGet, timeOut, isStream);

    }

    /**
     * get请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, Map<String, Object> params, Integer timeOut) throws URISyntaxException {

        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        // 创建get请求
        HttpGet httpGet = new HttpGet(uriBuilder.toString());

        return getResult(httpGet, timeOut, false);

    }

    /**
     * get请求,支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream) throws URISyntaxException {

        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }

        // 创建post请求
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        return getResult(httpGet, timeOut, isStream);

    }
    public static String sendPostJson(String url, String jsonStr, String charset) {
        return sendPostJson(url, jsonStr, charset, new HashMap<String, String>());
    }

    public static String sendPostJson(String url, String jsonStr, String charset, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        String uuid = UUID.randomUUID().toString();
        post.setHeader(HTTP_HEADER_REQUEST_ID, uuid);
        try {
            StringEntity entity = new StringEntity(jsonStr, charset);
            entity.setContentEncoding(charset);
            entity.setContentType("application/json;charset=" + charset);
            post.setEntity(entity);

            long start = System.currentTimeMillis();
            HttpResponse response = client.execute(post);
            log.debug("请求id:{}, url:{}，耗时:{}", uuid, url, (System.currentTimeMillis() - start));
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }

            if ((jsonStr.contains("<body")) || jsonStr.contains("<div")
                    || jsonStr.contains("<table")
                    || (jsonStr.length() > 1000)) {
                log.info("===请求url:{}, jsonStr过长或包含敏感信息，屏蔽掉, response:{}", url, sb.toString());
            } else {
                log.info("===请求url:{}, jsonStr :{}, response:{}", url, jsonStr, sb.toString());
            }

            return sb.toString();
        } catch (Exception ex) {
            log.error("execute  sendPostJson exception", ex);
            return null;
        }
    }


    /**
     * get请求,支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, JSONObject params, Integer timeOut) throws URISyntaxException {

        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }

        // 创建post请求
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet, timeOut, false);

    }

    private static String getResult(HttpRequestBase httpRequest, Integer timeOut, boolean isStream) {
        // 响应结果
        StringBuilder sb = null;
        CloseableHttpResponse response = null;
        try {
            // 获取连接客户端
            CloseableHttpClient httpClient = getHttpClient(timeOut);
            // 发起请求
            response = httpClient.execute(httpRequest);
            int respCode = response.getStatusLine().getStatusCode();
            // 如果是重定向
            if (302 == respCode) {
                String locationUrl = response.getLastHeader("Location").getValue();
                return getResult(new HttpPost(locationUrl), timeOut, isStream);
            }
            // 正确响应
            if (200 == respCode) {
                // 获得响应实体
                HttpEntity entity = response.getEntity();
                sb = new StringBuilder();

                // 如果是以流的形式获取
                if (isStream) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), ENCODING));
                    String len = "";
                    while ((len = br.readLine()) != null) {
                        sb.append(len);
                    }
                } else {
                    sb.append(EntityUtils.toString(entity, ENCODING));
                    if (sb.length() < 1) {
                        sb.append("-1");
                    }
                }
            }
        } catch (ConnectionPoolTimeoutException e) {
            log.error("从连接池获取连接超时!!!-->{}", e.getMessage());
        } catch (SocketTimeoutException e) {
            log.error("响应超时-->{}", e.getMessage());
            sb = new StringBuilder("-2");
        } catch (ConnectTimeoutException e) {
            log.error("请求超时-->{}", e.getMessage());
            sb = new StringBuilder("-2");
        } catch (ClientProtocolException e) {
            log.error("http协议错误-->{}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的字符编码-->{}", e.getMessage());
        } catch (UnsupportedOperationException e) {
            log.error("不支持的请求操作-->{}", e.getMessage());
        } catch (ParseException e) {
            log.error("解析错误-->{}", e.getMessage());
        } catch (IOException e) {
            log.error("IO错误-->{}", e.getMessage());
            sb = new StringBuilder("-2");
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭响应连接出错-->{}", e.getMessage());
                }
            }

        }

        return sb == null ? RESULT : ("".equals(sb.toString().trim()) ? "-1" : sb.toString());

    }


    /**
     * Map转换成NameValuePair List集合
     *
     * @param params map
     * @return NameValuePair List集合
     */
    public static List<NameValuePair> covertParams2NVPS(Map<String, Object> params) {

        List<NameValuePair> paramList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        return paramList;

    }
    public static List<NameValuePair> covertParams2NVPSString(Map<String, String> params) {

        List<NameValuePair> paramList = new LinkedList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        return paramList;

    }

    public static void main(String[] args) throws Exception {

        String url = "http://order.api.komovie.cn/movie/service?action=order_Delete&enc=0bdadeb64165d318ba9400102de67d74&time_stamp" +
                "=1516876680474&order_id=a1516698609803911971";
        Map<String, Object> headers = new HashMap<>();
        headers.put("channel_id", "280");
        log.info(httpGet(url, headers, null, 6000, false));

    }

}

