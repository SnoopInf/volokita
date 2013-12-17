package com.volokita.gae;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class HelloGAEServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Here is gonna be twitter demo app for Volokita!");
	}
}
