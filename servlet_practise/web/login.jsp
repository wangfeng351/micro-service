<%--
  Created by IntelliJ IDEA.
  User: wf
  Date: 2020/10/18
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="checkbox" name="hobbys" value="唱歌">唱歌
    <input type="checkbox" name="hobbys" value="跳舞">跳舞
    <input type="checkbox" name="hobbys" value="代码">代码
    <br>
    <input type="submit">
</form>
</body>
</html>
