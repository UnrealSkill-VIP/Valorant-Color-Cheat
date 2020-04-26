package dev.niro.valorantcheat.gui.debug;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.utils.Logger;

public class DebugFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	DebugPanel panel;
	
	public DebugFrame() {		
		super("Debug");
			
		init();
		
		startThreads();
	}
	
	public void init() {
        setBackground(new Color(222, 222, 222));
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        
        panel = new DebugPanel(this);
        add(panel);
        pack();
	}
		
	public void startThreads() {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				while(true) {
					try {
						if(Main.debug) {
							if(!isVisible())
								setVisible(true);
							repaint();
							Thread.sleep(50);
						} else {
							if(isVisible())
								setVisible(false);
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						Logger.err("Frame reposition thread crashed!");
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
}
