package com.springbook.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller(value = "homeControllerInAdmin")
public class HomeController {
	
	@Autowired
	WebApplicationContext applicationContext;

	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView homepage() {
		ModelAndView mav = new ModelAndView("admin/home.jsp");
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);

		// Get the corresponding information of url and class and method
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		List<Map<String, String>> list = new ArrayList<>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			Map<String, String> map1 = new HashMap<>();
			RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();

//            map1.put("className", method.getMethod().getDeclaringClass().getName()); //class name
            
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			for (String url : p.getPatterns()) {
			for (RequestMethod requestMethod : methodsCondition.getMethods()) {
				if (requestMethod.toString().equals("GET") && url.startsWith("/admin")) {
					map1.put("method", method.getMethod().getName()); //method name
						map1.put("type", requestMethod.toString());
						
						map1.put("url", url);
						list.add(map1);
					}
				}
			}
			
		}

//		list.forEach(t -> System.out.println(t));
//		permissionService.addUrl(list);
		
//		Map<String, List<PermissionDTO>> listf = permissionService.getUrl();
		
//		listf.forEach((t, u) -> u.forEach(k -> System.out.println(k) ) );
		return mav;
	}
	
	

}
