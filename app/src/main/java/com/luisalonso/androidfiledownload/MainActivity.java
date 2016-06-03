package com.luisalonso.androidfiledownload;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luisalonso.androidfiledownload.io.ApiConstants;
import com.luisalonso.androidfiledownload.io.FileDownload;

import com.luisalonso.androidfiledownload.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<ResponseBody> {

    private Button mDownloadButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.luisalonso.androidfiledownload.R.layout.activity_main);
        setupView();
        setupListeners();
    }

    private void setupView() {
        mDownloadButton = (Button) findViewById(com.luisalonso.androidfiledownload.R.id.download);
    }

    private void setupListeners() {
        mDownloadButton.setOnClickListener(this);
    }

    private void showMessage(String message, boolean state) {
        if (state) {
            Toast.makeText(this, "Success -> " + message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error -> " + message, Toast.LENGTH_LONG).show();
        }
    }

    private void startLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.show();
    }

    private void stopLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    private void downloadFile() {
        Retrofit api = new Retrofit.Builder().baseUrl(ApiConstants.PATH_FILE).build();
        FileDownload fileDownload = api.create(FileDownload.class);
        Call<ResponseBody> call = fileDownload.downloadFile(ApiConstants.FILE_ID);
        call.enqueue(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mDownloadButton.getId()) {
            startLoading();
            downloadFile();
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        (new DownloadTask(response.body().byteStream())).execute();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        stopLoading();
        if (t != null) {
            t.printStackTrace();
            showMessage(t.getMessage(), false);
        } else {
            showMessage("Error en la descarga", false);
        }
    }

    private class DownloadTask extends AsyncTask<Void, Void, Void> {

        boolean status;
        private InputStream inputStream;

        public DownloadTask(InputStream stream) {
            inputStream = stream;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            status = (FileUtil.saveInExternalStorage(inputStream, Constants.FILE_FROM_DRIVE_NAME));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            stopLoading();
            if (status) {
                showMessage("Descarga completa", true);
            } else {
                showMessage("Ocurrió un error inesperado", false);
            }
        }
    }
}
