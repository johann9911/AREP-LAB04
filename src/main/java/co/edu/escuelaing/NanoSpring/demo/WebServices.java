package co.edu.escuelaing.NanoSpring.demo;

import co.edu.escuelaing.NanoSpring.PathVariable;
import co.edu.escuelaing.NanoSpring.RequestMapping;

public class WebServices {
	@RequestMapping("/sum")
	public static String sum(@PathVariable int num1,@PathVariable int num2) {
		String data = ObtainResources.outResource("sum.html", "html");
	
		String dataR1 = data.replaceAll("<input type=\"text\" id=\"num1\" placeholder=\"Numbers\" value=\"valorNum1\">",
				"<input type=\"text\" id=\"num1\" placeholder=\"Numbers\" value=\""+Integer.toString(num1)+"\">");
		
		String dataR2 = dataR1.replaceAll("<input type=\"text\" id=\"num2\" placeholder=\"Numbers\" value=\"valorNum2\">",
				"<input type=\"text\" id=\"num2\" placeholder=\"Numbers\" value=\""+Integer.toString(num2)+"\">");
		
		return dataR2;
	}

}
