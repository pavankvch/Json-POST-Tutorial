package com.icore.jsonpost;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sundar on 31/3/16.
 */
public class JsonParser {
    HttpURLConnection urlConnection;
    String charset = "UTF-8";
    String responseData;

    public String makeHttpRequest(URL url, String requestBody) {

        try{
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestProperty("Accept-Charset", charset);
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);


        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(requestBody.getBytes());
            out.flush();
//        writeStream(out);

                /* Get Response and execute WebService request*/
        int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
        if (statusCode == HttpsURLConnection.HTTP_OK) {

           InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            responseData= readStream(inputStream);

        } else {
            responseData = null;
        }

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally{
        urlConnection.disconnect();
    }
        return responseData;
    }

    private String readStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

//
//    private void writeStream(OutputStream out) throws IOException {
//
//        out.write(requestBody.getBytes());
//        out.flush();
//    }
}
