package com.volokita.gae;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SearchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6896355646687161736L;
	private static final Logger log = Logger.getLogger(SearchServlet.class.getName());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
    	try {

        String content = request.getParameter("content");
        if (content == null) {
            content = "(No search)";
        }
        
        HttpSession session = request.getSession();
		Twitter twitter = (Twitter) session.getAttribute("twitter");
		Query query = new Query(content); //"DataArt since:2010-12-27"
		
		query.setCount(100);
		QueryResult result = twitter.search(query);
		
		List<String> list = new ArrayList<String>();
		for (Status status : result.getTweets()) {
			list.add("@" + status.getUser().getScreenName() + ":"
					+ status.getText());
		}
		request.setAttribute("query", content);
		request.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/search.jsp").forward(
				request, response);
    	} catch (TwitterException | ServletException e) {
    		log.severe(e.getMessage());
    	}
    }
}
