package cn.com.dhc.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.dhc.service.CoreService;
import cn.com.dhc.util.SHAUtil;

@WebServlet(name="token",urlPatterns="/token")
public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String TOKEN = "123456";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		String[] str = {TOKEN,timestamp,nonce};
		Arrays.sort(str);
		String bigStr = str[0]+str[1]+str[2];
		String digest = SHAUtil.encoder("SHA1", bigStr);
		if(signature.equals(digest)){
			resp.getWriter().print(echostr);
		}else{
			resp.getWriter().println("test get");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		System.out.println("post-------------"+req.getRequestURI());
		String respMessage = CoreService.processRequest(req);
		System.out.println(respMessage);
		resp.getWriter().print(respMessage);
		
	}

}
