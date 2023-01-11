package com.intersem.sdib.ui.services.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfDocumentAdapter extends PrintDocumentAdapter {
    Context context;
    String path;
    ServiceRequest servicio_selecionado;

    public PdfDocumentAdapter(Context context, String path, ServiceRequest servicio) {
        this.context = context;
        this.path = path;
        this.servicio_selecionado = servicio;
    }

    @Override
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
        if (cancellationSignal.isCanceled()){
            layoutResultCallback.onLayoutCancelled();
        }else{
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("file name");
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build();
            layoutResultCallback.onLayoutFinished(builder.build(), !printAttributes1.equals(printAttributes));
        }
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

            byte [] buff = new byte[16384];
            int size;
            while ((size = inputStream.read(buff)) >= 0 && !cancellationSignal.isCanceled()){
                outputStream.write(buff, 0, size);;
            }

            if(cancellationSignal.isCanceled()){
                writeResultCallback.onWriteCancelled();
            }else{
                writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        DataBaseService dataBaseService = new DataBaseService(context);
        servicio_selecionado.setPrinted(1);
        dataBaseService.ModificarServicio(servicio_selecionado);
    }
}
