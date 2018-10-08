<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>edit meal</title>
</head>
<body>
    <h3><a href="/topjava">Home</a></h3>
    <h2>Meals</h2>
    <table align="center" border="0" cellpadding="0" cellspacing="0" style="width:500px">
        <tbody>
        <tr>
            <td>
                <form  action="update" method="POST">

                    <p>ID: <input name="id" type="text" readonly="readonly" value="<c:out value="${meal.id}" />" /></p>
                    <p>Date: <input name="date" type="date" value="<c:out value="${meal.formatDate}" />" /></p>
                    <p>Time: <input name="time" type="time" value="<c:out value="${meal.formatTime}" />" /></p>
                    <p>Description: <input name="description" type="text" value="<c:out value="${meal.description}" />" /></p>
                    <p>Calories: <input name="calories" type="number" value="<c:out value="${meal.calories}" />"  /></p>
                    <p><input type="submit" value="edit meal"/></p>

                </form>
            </td>
        </tr>
        </tbody>
    </table>
</body>
</html>
