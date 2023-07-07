package com.bcyesil.capacior.printer;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@CapacitorPlugin(name = "Printer")

public class PrinterPlugin extends Plugin {

    private Printer implementation = new Printer();
    private WebView mWebView;

    @PluginMethod
    public void print(PluginCall call) {
        new Handler(Looper.getMainLooper()).post(
                () -> {
                    String content = call.getString("content");
                    String jobName = call.getString("name");
                    String orientation = call.getString("orientation");

                    if(!call.getData().has("content")) {
                        call.reject("Must provide any content");
                    }
                    else {
                    WebView webView = new WebView(getActivity());
                    webView.setWebViewClient(new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                            return false;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            createWebPrintJob(view,jobName,orientation );
                            mWebView = null;
                        }
                    });
                    webView.loadDataWithBaseURL(null, content, "text/HTML", "UTF-8", null);
                    mWebView = webView;

                    JSObject ret = new JSObject();
                    ret.put("message","success");
                    ret.put("content", content);
                    ret.put("name", jobName);
                    call.resolve(ret);
                    }
                }
        );
    }

    private void createWebPrintJob(WebView webView, String jobNameValue,String orientationValue) {
        // Get a PrintManager instance

        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName;
        if (jobNameValue == null) {
            jobName = "Document" + System.currentTimeMillis();
        }
        else {
            jobName = jobNameValue;
        }

        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        if(orientationValue == "landscape") {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE).build());
        }
        else {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
        }
    }
}
