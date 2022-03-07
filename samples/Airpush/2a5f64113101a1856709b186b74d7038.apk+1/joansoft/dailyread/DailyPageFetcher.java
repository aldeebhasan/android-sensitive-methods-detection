package joansoft.dailyread;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class DailyPageFetcher {
    volatile boolean downloading = true;
    String responseBody;

    DailyPageFetcher() {
    }

    String fetchPage(String url) {
        if (this.responseBody == null) {
            try {
                this.responseBody = (String) new DefaultHttpClient().execute(new HttpGet(url), new BasicResponseHandler());
            } catch (Exception e) {
                e.printStackTrace();
                this.responseBody = "";
            }
            this.downloading = false;
        }
        return this.responseBody;
    }

    void clear() {
        this.responseBody = null;
    }
}
