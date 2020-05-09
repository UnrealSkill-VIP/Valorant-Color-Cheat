package dev.niro.valorantcheat.listener;

import java.util.Map.Entry;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import dev.niro.valorantcheat.Main;

public class GlobalKeyboardListener implements NativeKeyListener {

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			if(e.getKeyCode() == entry.getKey().intValue()) {
				if(entry.getValue()) {
					Main.working = true;
				} else {
					Main.working = !Main.working;
				}
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			if(e.getKeyCode() == entry.getKey().intValue()) {
				if(entry.getValue()) {
					Main.working = false;
				} 
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}
	
}
