package dev.niro.valorantcheat.listener;

import java.util.Map.Entry;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import dev.niro.valorantcheat.Main;

public class GlobalMouseListener implements NativeMouseInputListener {
	
	public GlobalMouseListener() {
		new Thread(() -> {
			while(true) {
				if(System.currentTimeMillis() - lastClick > 300 && !holdM1)
					Main.shootingSince = -1;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	long lastClick = 0;
	boolean holdM1 = false;
	
	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			if(e.getButton() == entry.getKey().intValue()) {
				if(entry.getValue()) {
					Main.working = true;
				} else {
					Main.working = !Main.working;
				}
			}
		}
				
		if(e.getButton() == 1) {
			if(System.currentTimeMillis() - lastClick > 500)
				Main.shootingSince = System.currentTimeMillis();
			holdM1 = true;
			lastClick = System.currentTimeMillis();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			if(e.getButton() == entry.getKey().intValue()) {
				if(entry.getValue()) {
					Main.working = false;
				} 
			}
		}
				
		if(e.getButton() == 1) {
			holdM1 = false;
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {}
	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {}
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {}
}
