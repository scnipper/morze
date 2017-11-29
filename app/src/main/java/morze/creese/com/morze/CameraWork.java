package morze.creese.com.morze;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by yoba2 on 27.11.2017.
 */

public class CameraWork {
    private final Camera mCamera;
    private final Activity context;
    private Camera.Parameters mParams;

    public CameraWork(Activity context) {
        this.context = context;
        checkCameraHardware();

        mCamera = getCameraInstance();
        checkFlash();
    }
    /**
     * Check if this device has a camera
     */
    private void checkCameraHardware() {
        boolean result = false;
        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                && pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // this device has a camera and flash
            result = true;
        } else {
            context.finish();
            cameraRelease();
        }

    }
    public boolean isFlashOn() {
        return !mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF);
    }
    public void turnOnFlash() {

        mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(mParams);

    }
    public void turnOffFlash() {
        mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(mParams);
    }

    public void cameraRelease() {
        if (mCamera != null)
            mCamera.release();
    }
    /**
     * A safe way to get an instance of the Camera object.
     */
    private Camera getCameraInstance() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {

            }
        }*/

        return c; // returns null if camera is unavailable
    }

    private void checkFlash() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
        } else
            cameraRelease();
    }
}
