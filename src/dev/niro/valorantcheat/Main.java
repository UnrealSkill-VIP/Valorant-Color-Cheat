package dev.niro.valorantcheat;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;

import com.sun.jna.platform.win32.WinDef.HWND;

import dev.niro.valorantcheat.gui.GuiFrame;
import dev.niro.valorantcheat.listener.GlobalMouseListener;
import dev.niro.valorantcheat.overlay.OverlayFrame;
import dev.niro.valorantcheat.utils.Logger;
import dev.niro.valorantcheat.utils.MakeScreenshot;
import javafx.application.Application;

public class Main {
		
	public static float fovWidth = 0.3f;
	public static float fovHeight = 0.3f;	
	public static float moveSpeed = 0.12f;
	public static int maxTps = 100;
	public static boolean aimbot = false;
	public static boolean triggerbot = false;
	public static boolean sniper = false;
	public static float visirTime = 300;
	public static float reviseTime = 500;
	public static float recoilSpeed = 0.033f;
	public static float maxRecoil = 50;
	public static boolean debug = false;
		
	public static Robot robot;
	public static OverlayFrame overlay;	
	public static int tps = 0;
	public static int cpuTime = 0;
	public static boolean working = false;
	public static Rectangle fov;
	public static long lastShot = 0;
	public static boolean inFire = false;
	public static long inVisirSince = -1;
	public static Point aimTo;
	public static Rectangle entityFrame;
	public static HWND hwnd;
	public static boolean focusHead = false;
	
	public static void main(String[] args) {		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				int tpsTemp = 0;
				long tpsTime = System.currentTimeMillis();
				long sleepOffest = 0;
				long sleepTime = System.nanoTime();
				while(true) {			
					long time = System.currentTimeMillis();
					try {
						tick();
					} catch (Exception e) {
						e.printStackTrace();
					}
					cpuTime = (int) (System.currentTimeMillis() - time);
								
					try {
						int toSleep = (int) (1_000.0 / maxTps - sleepOffest / 1_000_000);
						if(toSleep > 0)
							Thread.sleep(toSleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					sleepOffest -= 1_000_000_000.0 / maxTps;
					sleepOffest += System.nanoTime() - sleepTime;
					sleepTime = System.nanoTime();
					if(sleepOffest > 10_000_000)
						sleepOffest = 10_000_000;
					
					tpsTemp++;
					if(System.currentTimeMillis() - tpsTime > 1000) {
						tps = tpsTemp;
						tpsTemp = 0;
						tpsTime = System.currentTimeMillis();
					}
				}
			}
		}).start();
		
		try {
			init(args);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void init(String[] args) throws Exception {
		overlay = new OverlayFrame("VALORANT  ");
		
		LogManager.getLogManager().reset();
		GlobalScreen.registerNativeHook();			
		GlobalScreen.addNativeMouseListener(new GlobalMouseListener());
				
		updateSettings();
		robot = new Robot();
		
		Application.launch(GuiFrame.class, args);
	}
	
	public static void updateSettings() {
		int fovWidthPx = (int) (overlay.getWidth() * fovWidth);
		int fovHeightPx = (int) (overlay.getHeight() * fovHeight);
		fov = new Rectangle((int)(overlay.getWidth() / 2 - fovWidthPx / 2), 
				(int) (overlay.getHeight() / 2 - fovHeightPx / 2), fovWidthPx, fovHeightPx);
	}

	public static BufferedImage debugImage;
	public static void tick() throws Exception {
		if(hwnd == null)
			return;
				
		int top = -1;
		int left = -1;
		int right = -1;
		int bottom = -1;

		// Taking Screenshot
		//Normal:
		BufferedImage image = MakeScreenshot.capture(hwnd, fov);
		//Other:
		//BufferedImage image = GDI32Util.getScreenshot(hwnd).getSubimage((int)fov.getX(), (int)fov.getY(), (int)fov.getWidth(), (int)fov.getHeight());
		//Default Screenshot (Too slow):
		//BufferedImage image = robot.createScreenCapture(fov);
		
		if(image == null)
			return;
			
		// Pixel search algorithm
		boolean canSnipe = false;
		int inColorPixelCount = 0;
		for (int i = 0; i < image.getWidth(); i += 1) {
			int x = image.getWidth() / 2 + (i % 2 == 0 ? -1 : 1) * (int)Math.floor(i / 2);
			
			if(x - 30 > right && right != -1)
				continue;
			if(x + 30 < left && left != -1)
				continue;
			if(right - left > (bottom - top) * 2
					&& bottom - top > 10 && right - left > 10)
				continue;
						
			for (int y = 0; y < image.getHeight(); y += 1) {	
				int clr   = image.getRGB(x, y); 

				short red   = (short) ((clr & 0x00ff0000) >> 16);
				short green = (short) ((clr & 0x0000ff00) >> 8);
				short blue  =  (short) (clr & 0x000000ff);
				float colorBrightness = Color.RGBtoHSB(red, green, blue, null)[1];			
				
				// Scanning for the red dot in the sniper scope
				if(i <= 6 && red > 196 && green < 45 && blue < 36
						&& Math.abs(image.getHeight() / 2 - y) <= 6) {
					canSnipe = true;
				}
						
				if(y - 30 > bottom && bottom != -1 && i > 3)
					break;
				if(y + 30 < top && top != -1 && i > 3)
					continue;
				
				double colorScale = 1;
				if(red >= green && red >= blue)
					colorScale = 255.0 / red;
				else if(green >= red && green >= blue)
					colorScale = 255.0 / green;
				else if(blue >= green && blue >= red)
					colorScale = 255.0 / blue;
				
				red = (short) Math.round(red * colorScale);
				green = (short) Math.round(green * colorScale);
				blue = (short) Math.round(blue * colorScale);
				float colorHue = Color.RGBtoHSB(red, green, blue, null)[0];
				
				if(colorScale <= 4
						&& red >= 245 && red <= 255
						&& green >= 50 && green <= 100 + (colorBrightness - 0.5) * 240
						&& blue >= 240 && blue <= 255
						&& colorHue >= 0.82 && colorHue <= 0.88
						&& colorBrightness > 0.4 && colorBrightness < 0.8) {
					if(y < top || top == -1)
						top = y;
					if(y > bottom || bottom == -1)
						bottom = y;
					if(left > x || left == -1)
						left = x;
					if(right < x || right == -1)
						right = x;		
					inColorPixelCount++;
										
					if(debug)
						image.setRGB(x, y, red * 16 * 16 * 16 * 16 + green * 16 * 16 + blue);
						//image.setRGB(x, y, clr);
				} else if(debug)
					image.setRGB(x, y, 0);				
			}
		}
		
		if(debug)
			debugImage = image;
		else 
			debugImage = null;
				
		int width = right - left;
		int height = bottom - top;
		if(width == 0 || height == 0) {
			entityFrame = null; 
			aimTo = null;
			return;
		}
		
		// Is it really a player?
		float pixelQuote = (float)inColorPixelCount / (width * height);		
		if(height < 10 || width < 10 
				|| (int)width / height > 2 
				|| pixelQuote < 0.004
				|| (pixelQuote > 0.1 && inColorPixelCount < 100)
				|| inColorPixelCount <= 8) {
			entityFrame = null; 
			aimTo = null;			
			return;
		}
						
		boolean head = true;
		if(System.currentTimeMillis() - lastShot > 500 && inFire)
			head = false;
		if((double)width / height < 0.31)
			head = false;
		
		// How high should the aimbot aim? 
		float aimHeight = 0.4f * height;		
		if(head)
			aimHeight = 0.22f * width;		
		if(aimHeight > height / 2)
			aimHeight = height / 2;
		focusHead = head; 
		
		// Recoil calculation
		float recoil = (int) ((System.currentTimeMillis() - lastShot) * recoilSpeed);
		if(!inFire)
			recoil = 0;
		if(recoil > maxRecoil)
			recoil = maxRecoil;
						
		// Calculate mouse move distance
		float aimToX = left + width / 2;
		float aimToY = top + aimHeight + recoil;	
		float moveX = Math.round((aimToX - fov.getWidth() / 2) * moveSpeed);
		float moveY = Math.round((aimToY - fov.getHeight() / 2) * moveSpeed);
				
		entityFrame = new Rectangle(left, top, width, height);
		aimTo = new Point((int)aimToX, (int)aimToY);
		
		if(!working) 
			return;
		
		if(aimbot && (Math.abs(moveX) >= 2 || Math.abs(moveY) >= 2)) {
			robot.mouseMove((int) (MouseInfo.getPointerInfo().getLocation().getX() + moveX),
					(int) (MouseInfo.getPointerInfo().getLocation().getY() + moveY));
		} 
					
		boolean inVisir = System.currentTimeMillis() - inVisirSince > visirTime;
		boolean overPlayer = true;
		if(Math.abs(aimToX - fov.getWidth() / 2) > (head ? 15 : 30))
			overPlayer = false;
		if(aimToY - fov.getHeight() / 2 < -40)
			overPlayer = false;
		if(aimToY - fov.getHeight() / 2 > (head ? 10 : 30))
			overPlayer = false;
		
		if(triggerbot && overPlayer 
				&& ((inVisir && canSnipe) || !sniper)) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(10);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			lastShot = System.currentTimeMillis();
			inVisirSince = (long) (System.currentTimeMillis() + reviseTime);
		}
	}
}
