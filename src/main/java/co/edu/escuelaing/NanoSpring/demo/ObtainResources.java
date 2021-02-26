package co.edu.escuelaing.NanoSpring.demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ObtainResources {
	
	public static String outResource(String resource, String term) {
		String outText = "";
		try {
			FileReader f = new FileReader(System.getProperty("user.dir") + "/src/main/java/co/edu/escuelaing/resources/" + resource);
			BufferedReader b = new BufferedReader(f);
			String text = "";
			
			while ((text = b.readLine()) != null) {
				outText += text;
			}
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "HTTP/1.1 201 OK\r\n" + "Content-Type: text/" + term + ";" + "charset=\"UTF-8\" \r\n" + "\r\n"
				+ outText;
	}
	
	public static void outResourceImage(String resource, OutputStream entrada) {
		  try {
	            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir")+ "/src/main/java/co/edu/escuelaing/resources/" + resource));
	            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
	            DataOutputStream out = new DataOutputStream(entrada);
	            ImageIO.write(image, "png", ArrBytes);
	   
	            out.writeBytes("HTTP/1.1 200 OK \r\n"
	                    + "Content-Type: image/png \r\n"
	                    + "\r\n");
	            out.write(ArrBytes.toByteArray());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
}
