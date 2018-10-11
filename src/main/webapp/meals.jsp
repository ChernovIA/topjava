<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <meta charset="utf-8" />
    <title>Title</title>
</head>
<body>

<style>
    .normal {color : green}
    .exceed {color : red}
</style>
<table border="0" cellpadding="1" cellspacing="1" style="width:500px">

    <table align="center" border="1" cellpadding="1" cellspacing="1" style="width:500px">
        <caption><p><a href="/topjava">Home</a></p><p><h2>Meals</h2></p></caption>
        <tbody>
        <tr>
            <td style="text-align:center"><b>Time</b></td>
            <td style="text-align:center"><b>Description</b></td>
            <td style="text-align:center"><b>Calories</b></td>
            <th colspan=2><b>Action</b></th>
        </tr>

        <c:forEach items="${mealsList}" var="meal">

            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed" scope="page"/>

            <tr class = "${meal.exceed? 'exceed': 'normal'}">

                <td style="text-align:center"><javatime:format value="${meal.dateTime}" pattern="${dataPattern}"/></td>
                <td style="text-align:center">${meal.description}</td>
                <td style="text-align:center">${meal.calories}</td>
                <td style="text-align:center"><a href="meals/update?id=<c:out value="${meal.id}"/>">Update</a></td>
                <td style="text-align:center"><a href="meals/delete?id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form align="center" method="post" action="meals/add">
        <p>Data time: <input type="date" name ="date"/> <input type="time" name="time"/></p>
        <p>Description <input type="text" name="description"/></p>
        <p>calories:<input type="number" name="calories"/></p>
        <input type="submit" value="add meal">
    </form>
</table>
</body>
</html>
