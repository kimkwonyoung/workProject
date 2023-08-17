package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
/*
 * 1. Controller 클래스의 service 멤버 변수 설정 (DI) 
 * 2. Interface DAO에 대한 구현체 DI 설정
 * 3. mime : (Content-Type: Application/x-www-form-urlencode)
 *    get/post 의 일반적인 전송시 
 *    Controller의 메소드의 인자로 DTO 객체를 전달 할 수 있게 기능 추가
 * 4. mime : (Content-Type: Application/json)
 *    Controller의 메소드의 인자로 DTO 객체를 전달 할 수 있게 기능 추가    
 * 5. 결과를 리턴시 
 * 	  JSP로 리턴
 *    redirect로 리턴
 *    JSON 문자열로 리턴 -> fetch 
 *    
 *    
 */

import Utils.StringUtil;


/**
 * Servlet implementation class MyServlet
 */
@WebServlet(name = "myServlet", urlPatterns = { "*.do" }, 
	initParams = {
		@WebInitParam(name = "VIEW-PATH", value="/WEB-INF/jsp/")
	} 
)
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, Class> classMap = new HashMap<>();
    private Map<String, Object> objectMap = new HashMap<>();
    private Map<String, Method> actions = new HashMap<>();
    private String viewPath;
    private String classPath = "";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<String> childFileInfo(String postfix, String classPath, File[] list) {
    	List<String> result = new ArrayList<>() ;
    	
		for (File fileInfo : list) {
			if (fileInfo.isDirectory()) {
				result.addAll(childFileInfo(postfix, classPath, fileInfo.listFiles()));
			} else {
				String fileName = fileInfo.getAbsolutePath();
				if (fileName.indexOf("$") >= 0) continue;
				int pos = fileName.indexOf(postfix);
				if (pos != -1) {
					result.add(fileName);
				}
			}
		}
		
		return result;
    }
    
    private void classLoadingAndCreateInstance(List<String> list) throws Exception {
    	for(String fileName : list) {
			String controllerName = fileName.substring(classPath.length()+1);
			System.out.println(controllerName);
			
			controllerName = controllerName.replace('\\', '.');
			System.out.println(controllerName);
			
			String className = controllerName.substring(0, controllerName.length() - 6);
			System.out.println(className);
			
			Class cls = classMap.get(className); 
			if (cls == null) {
				cls = Class.forName(className);
				classMap.put(className, cls);
				
				// 기본 생성자 정보를 얻는다 
				Constructor constructor = cls.getConstructor();
				// 객체 생성
				objectMap.put(className, constructor.newInstance());
			}
    	}
    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Class cls = MyServlet.class;
			String contextPath = config.getServletContext().getContextPath();
			URL url = cls.getResource("/");
			File file = new File(url.toURI());
			
			classPath = file.getAbsolutePath();
			System.out.println(classPath);
			
			List<String> controllerList = childFileInfo("Controller.class", classPath, file.listFiles());
			List<String> serviceList = childFileInfo("Service.class", classPath, file.listFiles());
			List<String> daoImplList = childFileInfo("Impl.class", classPath, file.listFiles());
			
			//classMap, objectMap의 변수에 값을 설정 
			classLoadingAndCreateInstance(controllerList);
			classLoadingAndCreateInstance(serviceList);
			classLoadingAndCreateInstance(daoImplList);
			
			for(Entry<String, Class> entry : classMap.entrySet()) {
				cls = entry.getValue();
				if (!cls.getName().endsWith("Controller")) continue;
				
				String simpleName = cls.getSimpleName().substring(0, cls.getSimpleName().length() - 10).toLowerCase();
				System.out.println("클래스명 -> " + simpleName);
				//클래스에 정의된 모든 메소드를 얻는다
				for (Method method : cls.getDeclaredMethods()) {
					boolean isRequest = false;
					boolean isResponse = false;
					for (Class paramClass : method.getParameterTypes()) {
						System.out.println(paramClass.getName());
						isRequest = isRequest || paramClass.equals(HttpServletRequest.class);
						isResponse = isResponse || paramClass.equals(HttpServletResponse.class);
					}
					if (isRequest && isResponse) {
						System.out.println("메소드명 " + method.getName());
						String actionURL = contextPath + "/" + simpleName + "/" + method.getName() + ".do";
						
						actions.put(actionURL, method);
						System.out.println("들어간 액션값 확인 = " + actions.toString());
						objectMap.put(actionURL, objectMap.get(entry.getKey()));
					}
				}
			}

			//DI 직접 구현 
			/*
			 * 1. classMap에 클래스 정보를 읽는다 
			 * 2. 클래스정보 중 필드 목록을 얻는다
			 * 3. 필드 목록중에서 필드의 타입을 확인하여 objectMap에 객체가 
			 *    존재하는지 확인하여 값이 존재하면 설정한다    
			 */
			
			//className = com.kosa.controller.MemberController
			//cls = MemberController 클래스 정보 
			//fields -> [MemberService memberService]
			//typeName -> com.kosa.service.MemberService
			for (Entry<String, Class> entry : classMap.entrySet()) {
				String className = entry.getKey();
				cls = entry.getValue();
				Object instance = objectMap.get(className);
				
				for (Field field : cls.getDeclaredFields()) {
					//클래스의 이름을 얻는다 
					String typeName = field.getType().getName();
					Object fieldObject = objectMap.get(typeName);
					if (instance != null && fieldObject != null) {
						field.setAccessible(true);
						field.set(instance, fieldObject);

						System.out.println(instance + " : " + field.getName() + " : " + field.get(instance));
						break;
					}
					//DAO 구현체를 주입 
					/*
					 * 1. 필드의 타입이 인터페이스인지 확인 한다
					 * 2. objectMap에 있는 객체 중 인터페이스로 형변환이 가능한지 확인한다
					 * 3. 확인이 되면 해당 객체를 field 에 대입한다
					 */
					if (!field.getType().isInterface()) continue;
					for (Entry<String, Object> objectEntry : objectMap.entrySet()) {
						try {
							objectEntry.getValue().getClass().asSubclass(field.getType());
							field.setAccessible(true);
							field.set(instance, objectEntry.getValue());

							System.out.println(instance + " : " + field.getName() + " : " + field.get(instance));
							break;
						} catch(Exception e) {
							
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println(classMap);
//		System.out.println(objectMap);
//		System.out.println(actions);
		
		viewPath = config.getInitParameter("VIEW-PATH");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response); 
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String contextType = request.getHeader("Content-Type");
		
		//경로를 얻는다
		String requestURI = request.getRequestURI();
		System.out.println("requestURI = " + requestURI);
		
		Method method = actions.get(requestURI);
		if (method == null) {
			response.getWriter().append("실행할 경로로 설정되지 않았습니다");
			response.getWriter().append("error");
		} else {
			Object obj = objectMap.get(requestURI);
			System.out.println("obj -> " + obj);
			try {
				String result = "";
				switch (method.getParameterCount()) {
				case 2: 
					if (contextType != null && contextType.equalsIgnoreCase("application/json; charset=UTF-8")) {
						jsonProcess(method, obj, request, response);
					} else {
						result = (String) method.invoke(obj, request, response);
						if (result.endsWith(".jsp")) {
							RequestDispatcher rd = request.getRequestDispatcher(viewPath + result);
							rd.forward(request, response);
						} else {
							response.sendRedirect(request.getContextPath() + "/" + result);
						}
					}
					break;
				case 3:
					if (StringUtil.isEmpty(contextType)) {
						formUrlencoded(method, obj, request, response);
					} else {
						if (contextType.equals("application/x-www-form-urlencoded")) {
							formUrlencoded(method, obj, request, response);
						} else if (contextType.equalsIgnoreCase("application/json; charset=UTF-8")) {
							jsonProcess(method, obj, request, response);
						}
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void formUrlencoded(Method method, Object obj, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Class paramClass = method.getParameterTypes()[0];
		//객체 생성 
		Object paramObj = paramClass.getDeclaredConstructor().newInstance();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			try {
				Field field = paramClass.getDeclaredField(entry.getKey());
				field.setAccessible(true);
				if (field.getType().equals(String.class)) {
					field.set(paramObj, entry.getValue()[0]);
				} else if(field.getType().equals(int.class)) {
					field.set(paramObj, Integer.parseInt(entry.getValue()[0]));
				} else if(field.getType().equals(float.class)) {
					field.set(paramObj, Float.parseFloat(entry.getValue()[0]));
				} else if(field.getType().equals(String[].class)) {
					field.set(paramObj, entry.getValue());
				} else if(field.getType().equals(int[].class)) {
					String [] values = entry.getValue();
					int [] arr = new  int[entry.getValue().length];
					for (int i=0;i<arr.length;i++) {
						arr[i] = Integer.parseInt(values[i]); 
					}
					field.set(paramObj, arr);
				} 
				
			} catch (Exception e) {
				
			}
		}
		
		String result = (String) method.invoke(obj, paramObj, request, response);
		if (result.endsWith(".jsp")) {
			RequestDispatcher rd = request.getRequestDispatcher(viewPath + result);
			rd.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/" + result);
		}
		
	}
	
	private void jsonProcess(Method method, Object obj, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		StringBuilder data = new StringBuilder();
		String line = null;
		
		while(true) {
			line = br.readLine();
			if (line == null) break;
			data.append(line);
		}
		System.out.println("json 문자열 = " + data.toString());
		
		Class paramClass = method.getParameterTypes()[0];
		Object paramObj = null;
		String result = "";
		
		if (paramClass.equals(HttpServletRequest.class)) {
			result = (String) method.invoke(obj, request, response);
		} else {
			//json 객체 생성 
			JSONObject jsonObject = new JSONObject(data.toString());
			
			//객체 생성 
			paramObj = paramClass.getDeclaredConstructor().newInstance();
			for (String key : jsonObject.keySet()) {
				try {
					Field field = paramClass.getDeclaredField(key);
					field.setAccessible(true);
					if (field.getType().equals(String.class)) {
						field.set(paramObj, jsonObject.getString(key));
					} else if(field.getType().equals(int.class)) {
						field.set(paramObj, jsonObject.getInt(key));
					} else if(field.getType().equals(float.class)) {
						field.set(paramObj, jsonObject.getFloat(key));
					} else if(field.getType().equals(String[].class)) {
						field.set(paramObj, jsonObject.getDouble(key));
					} else if(field.getType().equals(int[].class)) {
	//					String [] values = entry.getValue();
	//					int [] arr = new  int[entry.getValue().length];
	//					for (int i=0;i<arr.length;i++) {
	//						arr[i] = Integer.parseInt(values[i]); 
	//					}
	//					field.set(paramObj, arr);
					} 
					
				} catch (Exception e) {
					
				}
			}
			result = (String) method.invoke(obj, paramObj, request, response);
		}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().append(result);
	}	
}
