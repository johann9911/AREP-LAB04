package co.edu.escuelaing.NanoSpring;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import co.edu.escuelaing.httpserver.HttpServer;

public class NanoSpringApplication{

	private static NanoSpringApplication _instance = new NanoSpringApplication();
	private boolean componentesLoaded = false;
	private Map<String, Method> componentsRoutes = new HashMap();

	private NanoSpringApplication() {

	}
	/**
	 * this method is used to run and load all components
	 * @param args list of class
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public static void run(String[] args) throws SecurityException, ClassNotFoundException {
		if (!_instance.componentesLoaded) {
			_instance.loadComponents(args);
			_instance.componentesLoaded = true;
			_instance.startServer();
		}

	}

	/**
	 * this method begin to server
	 */
	private void startServer() {
		HttpServer hserver = new HttpServer();
		hserver.registerHandler(this, "/calculator");
		try {
			hserver.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to load components and save with your key 
	 * @param components list of components
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	private void loadComponents(String[] components) throws SecurityException, ClassNotFoundException {

		for (String classPath : components) {
			for (Method m : Class.forName(classPath).getMethods()) {
				if (m.isAnnotationPresent(RequestMapping.class)) {
					System.out.println("clase: "+m+" anotacion: "+m.getAnnotation(RequestMapping.class).value());
					componentsRoutes.put(m.getAnnotation(RequestMapping.class).value(), m);
				}
			}
		}

	}
	
	/**
	 * this method is used to separate path and parameters, and invoke it
	 * @param path
	 * @return
	 */
	public String handle(String path) {
		String retornar = "no component";
		String params = "";
		String pathO;
		int indexOfValues = path.indexOf("?");
		if (indexOfValues > 0) {
			 	params = path.substring(indexOfValues + 1);
	            pathO = path.substring(0, indexOfValues);
	    }else {
	    	pathO=path;
	    }
		
		if(componentsRoutes.containsKey(pathO)) {
			Method component = componentsRoutes.get(pathO);
			
			if(component.getParameters().length>0) {
				retornar = invokeWithParams(component, params);
			}else {
				retornar = invoke(component);
			}
			
		}
		return retornar;
	}
	
	/**
	 * this method is used to invoke a method
	 * @param staticMethod method of class
	 * @return
	 */
	public static String invoke(Method staticMethod) {
		String res="";
		try {
			
			res = staticMethod.invoke(null).toString();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 *  this method is used to invoke a method
	 * @param staticMethod method of class
	 * @param params parameters of method
	 * @return
	 */
	public static String invokeWithParams(Method staticMethod, String params) {
		String[] val = params.split("&");
		
		
		String res ="";
		try {
			
			res = staticMethod.invoke(null, Integer.parseInt(val[0].split("=")[1]),Integer.parseInt(val[1].split("=")[1]) ).toString();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	

}
