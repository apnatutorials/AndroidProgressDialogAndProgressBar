package com.apnatutorials.androidprogressdialog;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private static final String DIALOG_TITLE = "Please wait ..." ;
    private static final String DIALOG_MESSAGE = "Pulling data from server" ;
    ProgressBar progressBar ;
    final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        progressBar.setVisibility(View.INVISIBLE);

    }

    /**
     * This method called when user click on a button
     * @param view
     */
    public void progressBarDemo(View view){
            switch (view.getId()){
                case R.id.btnIndeterminateCircular:
                    indeterminateCircularProgress();
                    break;
                case R.id.btnIndeterminateHorizontal:
                    indeterminateHorizontalProgress() ;
                    break;
                case R.id.btnDeterminateHorizontalProgressDialog:
                    horizontalProgressBar();
                    break;
                case R.id.btnProgressBarWidget:
                    progressBarWidgetDemo() ;
                    break;
            }
    }

    /**
     * indeterminate circular progress bar demo
     */
    public void indeterminateCircularProgress(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(DIALOG_TITLE);
        dialog.setMessage(DIALOG_MESSAGE);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000); // Here we can place our time consuming task
                    dismissDialog(dialog);
                }catch(Exception e){
                    dismissDialog(dialog);
                }

            }
        }).start();
    }

    /**
     * indeterminate horizontal progress bar demo
     */
    public void indeterminateHorizontalProgress(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(DIALOG_TITLE);
        dialog.setMessage(DIALOG_MESSAGE);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000); // Here we can place our time consuming task
                    dismissDialog(dialog);
                }catch(Exception e){
                    dismissDialog(dialog);
                }

            }
        }).start();
    }

    /**
     * Method used to dismiss a dialog
     * @param pd
     */
    public void dismissDialog(final ProgressDialog pd){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    /**
     * Determinate horizontal progress dialog demo
     */
    public void horizontalProgressBar(){
        final ProgressDialog dialog = new ProgressDialog(this);
        final Handler handler = new Handler();
        dialog.setTitle(DIALOG_TITLE);
        dialog.setMessage(DIALOG_MESSAGE);

        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0 ;
                    Log.v("myTag", " : Inside thread " ) ;
                    while (dialog.getProgress() < dialog.getMax()) {
                        Log.v("myTag", " : " + i ) ;
                        Thread.sleep(2*1000); // Here place time cunsuming task
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.setProgress((dialog.getProgress()+1));
                                    if(dialog.getProgress()>= dialog.getMax())
                                        dialog.dismiss();
                                }
                            });

                    }

                }catch(Exception e){
                    e.printStackTrace();
                    dismissDialog(dialog);
                    
                }

            }
        }).start();
    }

    /**
     * Determinate progress bar widget demo
     */
    public void progressBarWidgetDemo(){

        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        while(progressBar.getProgress() <progressBar.getMax()){
                            Thread.sleep(200);
                            mHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress( (progressBar.getProgress()+1));
                                    if(progressBar.getProgress()>=progressBar.getMax())
                                        progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        }

                }
                catch(Exception e){
                }
            }
        }).start();
    }
}
