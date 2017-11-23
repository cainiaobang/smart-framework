
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>customer</title>
</head>
<body>

<h1>create customer page</h1>

<form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
    <table>
        <tr>
            <td>customer name:</td>
            <td>
                <input type="text" name="name" >
            </td>
        </tr>
        <tr>
            <input type="file"  name="photo" >
        </tr>
    </table>
    <button type="submit">save</button>
</form>
</body>
</html>
