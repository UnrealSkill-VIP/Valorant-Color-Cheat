package dev.niro.valorantcheat.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.RECT;

import dev.niro.valorantcheat.Main;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	Panel panel;
	String taskName;
	
	public Frame(String title) {		
		super(title + " Overlay");
		
		taskName = title;
		
		init();
		
		startThreads();
	}
	
	public void init() {
		setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
        setSize(100, 100);
        setFocusableWindowState(false);
        setResizable(false);
        setFocusable(false);
        setFocusableWindowState(false);
        getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

        panel = new Panel(this);
        add(panel);
        pack();
	}
	
	public void setFrameSize(int x, int y)  {
		setSize(x, y);
		panel.setPreferredSize(new Dimension(x, y));
	}
	
	public void startThreads() {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					int width = 0;
					int height = 0;
					int x = 0;
					int y = 0;
					while(true) {
						Main.hwnd = User32.INSTANCE.FindWindow(null, taskName);

						while(Main.hwnd != null) {
							RECT rect = new RECT();
							boolean result = User32.INSTANCE.GetWindowRect(Main.hwnd, rect);

							if (!result) {
								setVisible(false);
								Main.hwnd = null;
								break;
							}
							
							if(rect.right - rect.left != width 
									|| rect.bottom - rect.top != height
									|| x != rect.left
									|| y != rect.top) {
								setVisible(true);
								
								setFrameSize(rect.right - rect.left, rect.bottom - rect.top);
								
								setLocation(rect.left, rect.top);
								
								Main.updateSettings();
								
								width = rect.right - rect.left;
								height = rect.bottom - rect.top;
								x = rect.left;
								y = rect.top;
							}

							repaint();

							Thread.sleep(Main.tps < 10 ? 100 : 1000 / Main.tps);
						}
						setVisible(false);
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					System.err.println("Frame reposition thread crashed!");
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
