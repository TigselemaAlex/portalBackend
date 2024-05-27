<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Recuperación de Contraseña</title>
</head>
<body style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">
<main style="width: 600px;
        background-color: #f3fbfb;
        border-radius: 12px;
        padding: 16px;
        border: 1px solid #9ae0d9;">
    <img src="https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/logo-removebg.png" alt="logo" width="240" style="display: block; margin: 0 auto; margin-bottom: 8px;
    " />
    <h1 style="text-align: center;
        text-transform: uppercase;
        color: #084a42;
        font-size: 48px;">Portal de la Viña</h1>
    <div style="border: 4px solid #084a42;">
        <h2 style="text-align: center;
        color: #0b655b;
        font-size: 32px;">Recuperación de la contraseña</h2>
        <p style="font-size: 20px;
        text-align: center;">
            Estimado residente <span style="text-transform: uppercase;
        color: #0e8174;">${name}</span> su contraseña ha
            sido reestablecida.
        </p>
        <p style="font-size: 20px;
        text-align: center;">Para acceder al sistema por favor introduzca la nueva contraseña:</p>
        <p style="background-color: #084a42;
        margin: 0 auto;
        font-size: 52px;
        color: white;
        height: 80px;
        width: 380px;
        font-weight: bold;
        letter-spacing: 8px;
        border-radius: 4px;
        text-align: center;">${password}</p>
        <p style="text-align: center;
        font-size: 16px;
        color: red;">
            *Recuerde cambiar su contraseña una vez iniciada sesión para más
                seguridad.
        </p>
    </div>
</main>
</body>
</html>
