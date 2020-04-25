package dev.niro.valorantcheat.listener;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import dev.niro.valorantcheat.Main;

public class GlobalMouseListener implements NativeMouseInputListener {
	
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		if(e.getButton() == 2 || e.getButton() == 5)
			Main.working = true;
		
		if(e.getButton() == 2)
			Main.inVisirSince = System.currentTimeMillis();
		
		if(e.getButton() == 1) {
			Main.lastShot = System.currentTimeMillis();
			Main.inFire = true;
			Main.inVisirSince = (long) (System.currentTimeMillis() + Main.reviseTime);
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		if(e.getButton() == 2 || e.getButton() == 5)
			Main.working = false;
		
		if(e.getButton() == 2)
			Main.inVisirSince = -1;
		
		if(e.getButton() == 1) {
			Main.lastShot = System.currentTimeMillis();
			Main.inFire = false;
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
