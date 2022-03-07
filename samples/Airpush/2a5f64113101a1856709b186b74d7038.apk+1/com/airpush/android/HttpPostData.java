package com.airpush.android;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HttpContext;

public final class HttpPostData {
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static Context ctx;
    private static HttpEntity entity;
    private static DefaultHttpClient httpClient;
    private static BasicHttpParams httpParameters;
    private static HttpPost httpPost;
    private static BasicHttpResponse httpResponse;
    private static HttpPost post;
    private static HttpEntity response;
    protected static long timeDiff = 1800000;
    private static int timeoutConnection;
    private static int timeoutSocket;
    private static String url;
    private BasicHttpResponse httpResponse2;

    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1;
        }
    }

    private interface Prefs {
        public static final String IOSCHED_SYNC = "iosched_sync";
        public static final String LOCAL_VERSION = "local_version";
    }

    protected static HttpEntity postData(List<NameValuePair> values, Context context) {
        if (Constants.checkInternetConnection(context)) {
            ctx = context;
            try {
                httpPost = new HttpPost("http://api.airpush.com/v2/api.php");
                httpPost.setEntity(new UrlEncodedFormEntity(values));
                httpParameters = new BasicHttpParams();
                timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                timeoutSocket = 3000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                httpClient = new DefaultHttpClient(httpParameters);
                httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
                    public void process(HttpRequest request, HttpContext context) {
                        if (!request.containsHeader(HttpPostData.HEADER_ACCEPT_ENCODING)) {
                            request.addHeader(HttpPostData.HEADER_ACCEPT_ENCODING, HttpPostData.ENCODING_GZIP);
                        }
                    }
                });
                httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
                    public void process(HttpResponse response, HttpContext context) {
                        Header encoding = response.getEntity().getContentEncoding();
                        if (encoding != null) {
                            for (HeaderElement element : encoding.getElements()) {
                                if (element.getName().equalsIgnoreCase(HttpPostData.ENCODING_GZIP)) {
                                    response.setEntity(new InflatingEntity(response.getEntity()));
                                    return;
                                }
                            }
                        }
                    }
                });
                httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);
                entity = httpResponse.getEntity();
                return entity;
            } catch (SocketTimeoutException e) {
                Log.d("SocketTimeoutException Thrown", e.toString());
                Airpush.reStartSDK(ctx, 1800000);
                return null;
            } catch (ClientProtocolException e2) {
                Log.d("ClientProtocolException Thrown", e2.toString());
                Airpush.reStartSDK(ctx, 1800000);
                return null;
            } catch (MalformedURLException e3) {
                MalformedURLException e4 = e3;
                Airpush.reStartSDK(ctx, 1800000);
                Log.d("MalformedURLException Thrown", e4.toString());
                return null;
            } catch (IOException e5) {
                IOException e6 = e5;
                Airpush.reStartSDK(ctx, 1800000);
                Log.d("IOException Thrown", e6.toString());
                return null;
            } catch (Exception e7) {
                Log.i("AirpushSDK", e7.toString());
                Airpush.reStartSDK(ctx, 1800000);
                return null;
            }
        }
        Airpush.reStartSDK(context, timeDiff);
        return null;
    }

    protected static HttpEntity postData3(List<NameValuePair> values, boolean test, Context context) {
        if (Constants.checkInternetConnection(context)) {
            ctx = context;
            if (test) {
                try {
                    url = "http://api.airpush.com/testmsg2.php";
                } catch (SocketTimeoutException e) {
                    Log.d("SocketTimeoutException Thrown", e.toString());
                    Airpush.reStartSDK(ctx, 1800000);
                    return null;
                } catch (ClientProtocolException e2) {
                    Log.d("ClientProtocolException Thrown", e2.toString());
                    Airpush.reStartSDK(ctx, 1800000);
                    return null;
                } catch (MalformedURLException e3) {
                    MalformedURLException e4 = e3;
                    Airpush.reStartSDK(ctx, 1800000);
                    Log.d("MalformedURLException Thrown", e4.toString());
                    return null;
                } catch (IOException e5) {
                    IOException e6 = e5;
                    Airpush.reStartSDK(ctx, 1800000);
                    Log.d("IOException Thrown", e6.toString());
                    return null;
                } catch (Exception e7) {
                    Exception iex = e7;
                    Airpush.reStartSDK(ctx, 1800000);
                    return null;
                }
            }
            url = "http://api.airpush.com/v2/api.php";
            httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(values));
            httpParameters = new BasicHttpParams();
            timeoutConnection = 10000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            httpClient = new DefaultHttpClient(httpParameters);
            httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
                public void process(HttpRequest request, HttpContext context) {
                    if (!request.containsHeader(HttpPostData.HEADER_ACCEPT_ENCODING)) {
                        request.addHeader(HttpPostData.HEADER_ACCEPT_ENCODING, HttpPostData.ENCODING_GZIP);
                    }
                }
            });
            httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
                public void process(HttpResponse response, HttpContext context) {
                    Header encoding = response.getEntity().getContentEncoding();
                    if (encoding != null) {
                        for (HeaderElement element : encoding.getElements()) {
                            if (element.getName().equalsIgnoreCase(HttpPostData.ENCODING_GZIP)) {
                                response.setEntity(new InflatingEntity(response.getEntity()));
                                return;
                            }
                        }
                    }
                }
            });
            httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);
            entity = httpResponse.getEntity();
            return entity;
        }
        Airpush.reStartSDK(context, timeDiff);
        return null;
    }

    protected static String postData2(String args, String appId, String imei, Context context) {
        if (Constants.checkInternetConnection(context) != null) {
            appId = "";
            ctx = context;
            try {
                if (Constants.checkInternetConnection(context) != null) {
                    HttpURLConnection connection = (HttpURLConnection) new URL(args).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(3000);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        StringBuffer sb = new StringBuffer();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while (true) {
                            args = reader.readLine();
                            if (args == null) {
                                return sb.toString();
                            }
                            sb.append(args);
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                Log.d("SocketTimeoutException Thrown", e.toString());
                Airpush.reStartSDK(ctx, 1800000);
            } catch (ClientProtocolException e2) {
                Log.d("ClientProtocolException Thrown", e2.toString());
                Airpush.reStartSDK(ctx, 1800000);
            } catch (MalformedURLException e3) {
                e3.printStackTrace();
                Airpush.reStartSDK(ctx, 1800000);
                Log.d("MalformedURLException Thrown", e3.toString());
            } catch (IOException e4) {
                e4.printStackTrace();
                Airpush.reStartSDK(ctx, 1800000);
                Log.d("IOException Thrown", e4.toString());
            } catch (Exception e5) {
                Airpush.reStartSDK(ctx, 1800000);
            }
            return "";
        }
        Airpush.reStartSDK(context, timeDiff);
        return "";
    }
}
