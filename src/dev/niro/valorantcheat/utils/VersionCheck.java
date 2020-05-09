package dev.niro.valorantcheat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JOptionPane;

import dev.niro.valorantcheat.Main;

public class VersionCheck {

	public static void check() {
		BufferedReader br = null;
		try {
			URL url = new URL("https://raw.githubusercontent.com/NiroDeveloper/Valorant-Color-Cheat/master/LASTEST_VERSION.txt");
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line = br.readLine();
			
			if(!line.equalsIgnoreCase(Main.VERSION))
				JOptionPane.showMessageDialog(null, "New Version is avaible: https://github.com/NiroDeveloper/Valorant-Color-Cheat");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    if (br != null) {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}
	}	
}
