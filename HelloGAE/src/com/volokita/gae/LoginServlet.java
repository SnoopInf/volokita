package com.volokita.gae;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = -3335883539878192010L;

	private static final Logger log = Logger.getLogger(LoginServlet.class.getName());

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

	try {
		Twitter twitter = new TwitterFactory().getInstance();
	twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
	RequestToken requestToken = twitter.getOAuthRequestToken();
	 
	String token = requestToken.getToken();
	String tokenSecret = requestToken.getTokenSecret();
	
	HttpSession session = request.getSession();
	session.setAttribute("token", token);
	session.setAttribute("tokenSecret", tokenSecret);
	 
	response.sendRedirect(requestToken.getAuthorizationURL());

	} catch (TwitterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
