package com.luisalonso.androidfiledownload.io;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * @author Luis Alonso Paulino Flores <alonso.paulino@mediabyte.com.pe>
 */
public interface FileDownload {

    @GET(ApiConstants.URL_FILE_TO_DOWNLOAD)
    @Streaming
    Call<ResponseBody> downloadFile(@Query("id") String fileId);
}
