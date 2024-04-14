<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>${hello}</h1>
    <h2>123</h2>
    <form method="GET" action="/reHello">
        <input type="submit" value="跳轉頁面"/>
    </form>
    <p>姓名：${name}</p>
    <p>性别：${sex}</p>
    <p>年龄:${age}</p>
</body>
</html>
