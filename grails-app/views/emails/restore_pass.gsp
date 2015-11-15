<html>
<head>

    <title>Recuperar tu contraseña</title>
</head>

<body>
    Solicitaste restablecer tu contraseña para recuperar tu contraseña haz click
    <a href="${ createLink(action: "change_pass", controller: "user", params: [token:token], base:"http://localhost:8080") }">aqui</a>
</body>
</html>