package co.edu.escuelaing.NanoSpring.demo;


import co.edu.escuelaing.NanoSpring.NanoSpringApplication;
public class Application {

	public static void main(String[] args) throws SecurityException, ClassNotFoundException {
		String[] path = {"co.edu.escuelaing.NanoSpring.demo.WebServices","co.edu.escuelaing.NanoSpring.demo.Multiplication" };
		NanoSpringApplication.run(path);

	}

}
