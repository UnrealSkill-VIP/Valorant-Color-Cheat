package dev.niro.coloraimbot.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinDef.HBITMAP;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinGDI.BITMAPINFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class MakeScreenshot {

	private static final DirectColorModel SCREENSHOT_COLOR_MODEL = new DirectColorModel(24, 0x00FF0000, 0xFF00, 0xFF);
	private static final int[] SCREENSHOT_BAND_MASKS = {
	        SCREENSHOT_COLOR_MODEL.getRedMask(),
	        SCREENSHOT_COLOR_MODEL.getGreenMask(),
	        SCREENSHOT_COLOR_MODEL.getBlueMask()
	    };
	
	public static HDC hdcTarget;
	// device context used for drawing
	public static HDC hdcTargetMem;
	 // handle to the bitmap to be drawn to
	public static HBITMAP hBitmap;
	public static int bitmapW;
	public static int bitmapH;
	
	public static BufferedImage capture(HWND target, Rectangle area) {
        int windowWidth = area.width;
        int windowHeight = area.height;

        // original display surface associated with the device context
        HANDLE hOriginal = null;

        // final java image structure we're returning.
        BufferedImage image = null;

        try {
        	if (hdcTarget == null) {
            	hdcTarget = User32.INSTANCE.GetDC(target);
            	hdcTargetMem = GDI32.INSTANCE.CreateCompatibleDC(hdcTarget);
            }
        	
        	if(hBitmap == null || bitmapW != windowWidth || bitmapH != windowHeight) {
        		hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcTarget, windowWidth, windowHeight);
        		bitmapW = windowWidth;
        		bitmapH = windowHeight;
        	}
        	
            hOriginal = GDI32.INSTANCE.SelectObject(hdcTargetMem, hBitmap);
            if (hOriginal == null) {
                throw new Win32Exception(Native.getLastError());
            }

            // draw to the bitmap
            if (!GDI32.INSTANCE.BitBlt(hdcTargetMem, 0, 0, windowWidth, windowHeight, hdcTarget, (int)area.getX(), (int)area.getY(), GDI32.SRCCOPY)) {
                return null;
            }

            BITMAPINFO bmi = new BITMAPINFO();
            bmi.bmiHeader.biWidth = windowWidth;
            bmi.bmiHeader.biHeight = -windowHeight;
            bmi.bmiHeader.biPlanes = 1;
            bmi.bmiHeader.biBitCount = 32;
            bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

            Memory buffer = new Memory(windowWidth * windowHeight * 4);
            int resultOfDrawing = GDI32.INSTANCE.GetDIBits(hdcTarget, hBitmap, 0, windowHeight, buffer, bmi,
                    WinGDI.DIB_RGB_COLORS);
            if (resultOfDrawing == 0 || resultOfDrawing == WinError.ERROR_INVALID_PARAMETER) {
                throw new Win32Exception(Native.getLastError());
            }

            int bufferSize = windowWidth * windowHeight;
            DataBuffer dataBuffer = new DataBufferInt(buffer.getIntArray(0, bufferSize), bufferSize);
            WritableRaster raster = Raster.createPackedRaster(dataBuffer, windowWidth, windowHeight, windowWidth,
                    SCREENSHOT_BAND_MASKS, null);
            image = new BufferedImage(SCREENSHOT_COLOR_MODEL, raster, false, null);
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        } 
        
        return image;
    }
	
}
