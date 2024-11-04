package com.bcyesil.capacitor.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Base64;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

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

                    if (content == null || content.isEmpty()) {
                        call.reject("Must provide any content");
                        return;
                    }

                    if (content.startsWith("base64:")) {
                        handleBase64Content(call, content, jobName, orientation);
                    } else {
                        handleHtmlContent(call, content, jobName, orientation);
                    }
                }
        );
    }

    private void handleBase64Content(PluginCall call, String content, String jobName, String orientation) {
        try {
            String base64Data;
            if (content.contains("data:")) {
                base64Data = content.substring(content.indexOf(",") + 1);
            } else {
                base64Data = content.replace("base64:", "");
            }

            byte[] fileData = Base64.decode(base64Data, Base64.DEFAULT);

            if (fileData == null || fileData.length == 0) {
                call.reject("Invalid Base64 data - unable to decode");
                return;
            }

            String fileName;
            String filePath = getContext().getFilesDir() + "/";

            if (isImageData(fileData)) {
                fileName = "document_" + System.currentTimeMillis() + ".pdf";
                filePath += fileName;
                createPdfFromImage(fileData, filePath);
            } else {
                fileName = "document_" + System.currentTimeMillis() + ".pdf";
                filePath += fileName;
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    fos.write(fileData);
                    fos.flush();
                }

                if (!isValidPdf(filePath)) {
                    call.reject("Malformed PDF file");
                    return;
                }
            }

            printPdf(filePath, jobName, orientation);

            JSObject ret = new JSObject();
            ret.put("message", "success");
            ret.put("content", content);
            ret.put("name", jobName);
            call.resolve(ret);
        } catch (IOException | IllegalArgumentException e) {
            call.reject("Invalid Base64 data");
        }
    }

    private boolean isValidPdf(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] header = new byte[4];
            if (fis.read(header) == 4) {
                return header[0] == '%' && header[1] == 'P' && header[2] == 'D' && header[3] == 'F';
            }
        } catch (IOException e) {
        }
        return false;
    }

    private boolean isImageData(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap != null;
    }

    private void createPdfFromImage(byte[] imageData, String filePath) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        if (bitmap == null) throw new IOException("Failed to decode image");

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        page.getCanvas().drawBitmap(bitmap, 0, 0, null);
        pdfDocument.finishPage(page);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            pdfDocument.writeTo(fos);
        } finally {
            pdfDocument.close();
        }
    }

    private void handleHtmlContent(PluginCall call, String content, String jobName, String orientation) {
        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view, jobName, orientation);
                mWebView = null;
            }
        });
        webView.loadDataWithBaseURL(null, content, "text/HTML", "UTF-8", null);
        mWebView = webView;

        JSObject ret = new JSObject();
        ret.put("message", "success");
        ret.put("content", content);
        ret.put("name", jobName);
        call.resolve(ret);
    }

    private void printPdf(String filePath, String jobNameValue, String orientationValue) {
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName = jobNameValue == null ? "Document" + System.currentTimeMillis() : jobNameValue;

        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        if ("landscape".equals(orientationValue)) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        }

        PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(getContext(), filePath);
        printManager.print(jobName, printAdapter, builder.build());
    }

    private void createWebPrintJob(WebView webView, String jobNameValue, String orientationValue) {
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName = jobNameValue == null ? "Document" + System.currentTimeMillis() : jobNameValue;

        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        if ("landscape".equals(orientationValue)) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        }

        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        printManager.print(jobName, printAdapter, builder.build());
    }
}