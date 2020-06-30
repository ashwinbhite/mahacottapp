package com.cotton.mahacott.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class DownloadFile extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	String download_file_url;
	String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
    float per = 0;
	Context context;
	public DownloadFile(String url,Context context){
		download_file_url = url;
		this.context = context;
	}
	
	public void downloadAndOpenPDF() {
        new Thread(new Runnable() {
            public void run() {
                Uri path = Uri.fromFile(downloadFile(download_file_url));
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    
                	Toast.makeText(context, "PDF Reader application is not installed in your device", Toast.LENGTH_LONG).show();        
                }
            }
        }).start();
   
    }
	
	File downloadFile(String dwnload_file_path) {
        File file = null;
        try {
 
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
 
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
 
            // connect
            urlConnection.connect();
 
            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, to save the downloaded file
            file = new File(SDCardRoot, dest_file_path);
 
            FileOutputStream fileOutput = new FileOutputStream(file);
 
            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
 
            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();
 
            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];  
            int bufferLength = 0;
 
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
//                setText("Total PDF File size  : "
//                        + (totalsize / 1024)
//                        + " KB\n\nDownloading PDF " + (int) per
//                        + "% complete");
            }
            // close the output stream when complete //
            fileOutput.close();
 
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
        	e.printStackTrace(); 
        } catch (final Exception e) {
        	e.printStackTrace();
        }
        return file;
    }
}
