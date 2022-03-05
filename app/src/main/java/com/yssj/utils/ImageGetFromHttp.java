package com.yssj.utils;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageGetFromHttp {
    private static final String LOG_TAG = "ImageGetFromHttp";
             
    public static Bitmap downloadBitmap(String url){
    	URL imgUrl = null;
		Bitmap b = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) imgUrl
					.openConnection();
			urlConn.setDoInput(true);
			urlConn.connect();
			// 将得到的数据转化成InputStream
			InputStream is = urlConn.getInputStream();
			// 将InputStream转换成Bitmap
			b = BitmapFactory.decodeStream(is);
			return b;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
    }
    
    public static Bitmap downloadBitmap1(String url) {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
                                                               
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LogYiFu.w(LOG_TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }
                                                                   
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
//                    FileInputStream fit = new FileInputStream(inputStream);
                    return BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            LogYiFu.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            LogYiFu.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            LogYiFu.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return null;
    }
                                                       
    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
                                                       
        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}