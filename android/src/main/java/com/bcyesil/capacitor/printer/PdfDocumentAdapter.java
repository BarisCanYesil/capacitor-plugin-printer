package com.bcyesil.capacitor.printer;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfDocumentAdapter extends PrintDocumentAdapter {

    private Context context;
    private String filePath;

    public PdfDocumentAdapter(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    @Override
    public void onLayout(
        PrintAttributes oldAttributes,
        PrintAttributes newAttributes,
        android.os.CancellationSignal cancellationSignal,
        LayoutResultCallback callback,
        Bundle extras
    ) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo info = new PrintDocumentInfo.Builder("file_name")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
            .build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(
        PageRange[] pageRanges,
        ParcelFileDescriptor destination,
        android.os.CancellationSignal cancellationSignal,
        WriteResultCallback callback
    ) {
        try (
            FileInputStream input = new FileInputStream(filePath);
            FileOutputStream output = new FileOutputStream(destination.getFileDescriptor())
        ) {
            byte[] buffer = new byte[1024];
            int size;
            while ((size = input.read(buffer)) >= 0 && !cancellationSignal.isCanceled()) {
                output.write(buffer, 0, size);
            }

            if (cancellationSignal.isCanceled()) {
                callback.onWriteCancelled();
            } else {
                callback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
            }
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
        }
    }
}
