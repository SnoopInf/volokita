package com.volokita.gae;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gae.tests.SignGuestBookServlet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = -4653810414349132792L;
	private static final Logger log = Logger.getLogger(HomeServlet.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String verifier = request.getParameter("oauth_verifier");
			if (verifier != null) {
				Twitter twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(Constants.CONSUMER_KEY,
						Constants.CONSUMER_SECRET);

				AccessToken aToken = twitter.getOAuthAccessToken(
						new RequestToken(
								(String) session.getAttribute("token"),
								(String) session.getAttribute("tokenSecret")),
						verifier);
				twitter.setOAuthAccessToken(aToken);
				session.setAttribute("twitter", twitter);
				User user = twitter.verifyCredentials();
				request.setAttribute("name", user.getName());
				// Status status =
				// twitter.updateStatus("Posted status via Twitter API");
			} else {
				Twitter twitter = (Twitter) session.getAttribute("twitter");
				if (twitter != null) {
					User user = twitter.verifyCredentials();
					request.setAttribute("name", user.getName());
				} else {
					response.sendRedirect("/login");
				}
			}
			getServletContext().getRequestDispatcher("/home.jsp").forward(
					request, response);
			super.doGet(request, response);
		} catch (TwitterException e) {
			log.severe(e.getErrorMessage());
			log.severe(e.getMessage());
			log.severe(e.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			Twitter twitter = (Twitter) session.getAttribute("twitter");
			Query query = new Query("DataArt since:2010-12-27");
			QueryResult result = twitter.search(query);
			String str = "";
			for (Status status : result.getTweets()) {
				str += "@" + status.getUser().getScreenName() + ":"
						+ status.getText() + "<br/>";
			}

			request.setAttribute("test", str);

			getServletContext().getRequestDispatcher("/home.jsp").forward(
					request, response);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.doPost(request, response);
	}
}
