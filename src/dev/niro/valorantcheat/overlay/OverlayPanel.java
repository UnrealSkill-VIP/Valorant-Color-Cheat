package dev.niro.valorantcheat.overlay;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.niro.valorantcheat.Main;


public class OverlayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public OverlayPanel(JFrame frame) {
		setPreferredSize(frame.getSize());
		setBackground(new Color(0, 0, 0, 0));
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
        
        g2.setColor(Color.white);
        g2.drawString("CPU Time: " + cputime + "ms", 6, 16);
        g2.drawString("TPS: " + Main.tps, 6, 30);
        
        if(Main.debugImage != null)
        	g2.drawImage(Main.debugImage, 6, 36, Main.debugImage.getWidth(), Main.debugImage.getHeight(), null);
        
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 10}, 0));
        
        if(Main.working) 
        	g2.setColor(new Color(200, 0, 0));
        else
        	g2.setColor(Color.black);
        g2.drawRect((int)Main.fov.getX(), (int)Main.fov.getY(), (int)Main.fov.getWidth(), (int)Main.fov.getHeight());
        
        g2.setColor(Color.red);
        
        if(Main.aimTo != null)
        	g2.fillOval((int)(Main.aimTo.getX() + Main.fov.getX() - 3), (int) (Main.aimTo.getY() + Main.fov.getY() - 3), 6, 6);
            
        g2.setStroke(new BasicStroke(3));
        int padding = 3;
        if(Main.entityFrame != null && Main.fov != null) {
        	String text = "Focus: " + (Main.focusHead ? "Head" : "Body");
        	g2.drawString(text, (int)(Main.entityFrame.getX() + Main.fov.getX() - padding), 
        			(int) (Main.entityFrame.getY() + Main.fov.getY() - padding - 2));
        	g2.drawRect((int)(Main.entityFrame.getX() + Main.fov.getX() - padding), (int) (Main.entityFrame.getY() + Main.fov.getY() - padding), 
        			(int)Main.entityFrame.getWidth() + padding * 2, (int)Main.entityFrame.getHeight() + padding * 2);
        }
	}
}