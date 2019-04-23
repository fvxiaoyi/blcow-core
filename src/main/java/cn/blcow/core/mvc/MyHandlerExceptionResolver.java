package cn.blcow.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MyHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

	private final MappingJackson2JsonView jsonView;

	public MyHandlerExceptionResolver() {
		jsonView = new MappingJackson2JsonView();
		jsonView.setExtractValueFromSingleKeyModel(true);
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		ResultMsg cmdMsg = new ResultMsg(false, ex.getMessage());
		mv.addObject("exception", cmdMsg);
		mv.setView(jsonView);
		return mv;
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		super.logException(ex, request);
		ex.printStackTrace();
	}

}
