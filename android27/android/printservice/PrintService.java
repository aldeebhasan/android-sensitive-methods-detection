package android.printservice;

import android.app.*;
import java.util.*;
import android.print.*;
import android.content.*;
import android.os.*;

public abstract class PrintService extends Service
{
    public static final String EXTRA_CAN_SELECT_PRINTER = "android.printservice.extra.CAN_SELECT_PRINTER";
    public static final String EXTRA_PRINTER_INFO = "android.intent.extra.print.EXTRA_PRINTER_INFO";
    public static final String EXTRA_PRINT_DOCUMENT_INFO = "android.printservice.extra.PRINT_DOCUMENT_INFO";
    public static final String EXTRA_PRINT_JOB_INFO = "android.intent.extra.print.PRINT_JOB_INFO";
    public static final String EXTRA_SELECT_PRINTER = "android.printservice.extra.SELECT_PRINTER";
    public static final String SERVICE_INTERFACE = "android.printservice.PrintService";
    public static final String SERVICE_META_DATA = "android.printservice";
    
    public PrintService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected final void attachBaseContext(final Context base) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onConnected() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onDisconnected() {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract PrinterDiscoverySession onCreatePrinterDiscoverySession();
    
    protected abstract void onRequestCancelPrintJob(final PrintJob p0);
    
    protected abstract void onPrintJobQueued(final PrintJob p0);
    
    public final List<PrintJob> getActivePrintJobs() {
        throw new RuntimeException("Stub!");
    }
    
    public final PrinterId generatePrinterId(final String localId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
