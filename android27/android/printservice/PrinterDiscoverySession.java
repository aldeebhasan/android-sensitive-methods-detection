package android.printservice;

import java.util.*;
import android.print.*;
import android.os.*;

public abstract class PrinterDiscoverySession
{
    public PrinterDiscoverySession() {
        throw new RuntimeException("Stub!");
    }
    
    public final List<PrinterInfo> getPrinters() {
        throw new RuntimeException("Stub!");
    }
    
    public final void addPrinters(final List<PrinterInfo> printers) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removePrinters(final List<PrinterId> printerIds) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onStartPrinterDiscovery(final List<PrinterId> p0);
    
    public abstract void onStopPrinterDiscovery();
    
    public abstract void onValidatePrinters(final List<PrinterId> p0);
    
    public abstract void onStartPrinterStateTracking(final PrinterId p0);
    
    public void onRequestCustomPrinterIcon(final PrinterId printerId, final CancellationSignal cancellationSignal, final CustomPrinterIconCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onStopPrinterStateTracking(final PrinterId p0);
    
    public final List<PrinterId> getTrackedPrinters() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onDestroy();
    
    public final boolean isDestroyed() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isPrinterDiscoveryStarted() {
        throw new RuntimeException("Stub!");
    }
}
