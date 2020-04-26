package dev.niro.valorantcheat.gui.debug;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.niro.valorantcheat.Main;


public class DebugPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public DebugPanel(JFrame frame) {
		setPreferredSize(frame.getSize());
		setBackground(new Color(222, 222, 222));
		setDoubleBuffered(true);
	}

	int cputime = -1;
	long cpuupdate = 0;
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(System.currentTimeMillis() - cpuupdate > 500 || Math.abs(cputime - Main.cpuTime) > 20) {
        	cpuupdate = System.currentTimeMillis();
        	cputime = Main.cpuTime;
        }
        
        g2.setColor(Color.black);
        g2.drawString("CPU Time: " + cputime + "ms", 6, 14);
        g2.drawString("TPS: " + Main.tps, 130, 14);
        
       
        if(Main.debugImage == null) 
        	return;
        
        int imageX = 1;
        int imageY = 20;
        int imageW = getWidth() - imageX - 2;
        int imageH = getHeight() - imageY - 2;
        if((double)imageW / imageH > (double)Main.debugImage.getWidth() / Main.debugImage.getHeight())
        	imageW = (int) ((double)Main.debugImage.getWidth() / Main.debugImage.getHeight() * imageH);
        else 
        	imageH = (int) ((double)Main.debugImage.getHeight() / Main.debugImage.getWidth() * imageW);
        
        g2.drawImage(Main.debugImage, imageX, imageY, imageW, imageH, null);
              
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(3));
        
        if(Main.aimTo != null)
        	g2.fillOval((int) (Main.aimTo.getX() / Main.fov.getWidth() * imageW + imageX - 3), 
        			(int) (Main.aimTo.getY() / Main.fov.getHeight() * imageH + imageY - 3), 6, 6);
               
        int padding = 3;
        if(Main.entityFrame != null) 
        	g2.drawRect((int)(Main.entityFrame.getX() / Main.fov.getWidth() * imageW - padding + imageX), 
        			(int) (Main.entityFrame.getY() / Main.fov.getHeight() * imageH - padding + imageY), 
        			(int) (Main.entityFrame.getWidth() * (imageW / Main.fov.getWidth()) + padding * 2), 
        			(int) (Main.entityFrame.getHeight() * (imageH / Main.fov.getHeight()) + padding * 2));
	}
}