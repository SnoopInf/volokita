<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Search</title>
</head>
<body>
<form action="/search" method="post">
    <div><textarea name="content" rows="3" cols="60">
      <c:if test="${requestScope['query'] != null}">
  	<c:out value="${requestScope['query']}"/>
  </c:if>
    </textarea></div>
    <div><input type="submit" value="Search" /></div>
  </form>
  <c:forEach items="${list}" var="item">
  <p><c:out value="${item}"/></p>
  </c:forEach>
</body>
</html>