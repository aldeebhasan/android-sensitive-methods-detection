package android.hardware.input;

public interface InputDeviceListener
{
    void onInputDeviceAdded(final int p0);
    
    void onInputDeviceRemoved(final int p0);
    
    void onInputDeviceChanged(final int p0);
}
