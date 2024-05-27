<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recibo de Cobro</title>
</head>

<body style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">
    <main style="width: 300px;
    background-color: #f3fbfb;
    border-radius: 12px;
    padding: 16px;
    border: 1px solid #9ae0d9;
    position: relative;
    ">
        <img src="https://portal-de-la-vina-bucket.s3.us-east-2.amazonaws.com/logo-removebg.png" alt="logo" width="240" style="display: block; margin: 0 auto; margin-bottom: 8px;
    " />
        <h1 style="text-align: center;
    text-transform: uppercase;
    color: #084a42;
    font-size: 48px;
    margin-bottom: 4px;
    ">Portal de la Viña</h1>
        <h2 style="text-align: center;
    text-transform: capitalize;
    color: #084a42;
    font-size: 36px;
        margin: 4px
    ">
            Conjunto Residencial
        </h2>
        <div style="border: 4px solid #084a42;">


        <table cellpadding="0" cellspacing="0" border="0" width="100%" style="border-collapse: collapse; font-size: 20px" >
            
            <tr >
                <td colspan="3" style="text-align: center;">
                    <h3 style="margin: 0; color: black; font-size: 28px">Recibo de Cobro</h3>
                </td>
            </tr>

            <tr>
                <td colspan="1" style="width: 33.3%;">
                     
                </td>
                <td colspan="1" style="width: 33.3%; text-align: center;">
                    
                </td>
                <td colspan="1" style="width: 33.3%; padding-right: 20px; text-align: end;">
                    <p style="margin: 0 0 8px 0; padding: 8px; font-weight: bold; color: crimson;"><span>${code}</span></p>
            </td>
            </tr>

            <tr>
                <td colspan="1" style="width: 33.3%;text-align: start;padding-left: 20px">
                    <p style="margin: 0 0 8px 0; padding: 8px; border: 1px solid black; width: fit-content; border-radius: 8px;">Casa: <span style="color: #0b655b">${number}</span></p>
                </td>
                <td colspan="1" style="width: 33.3%; ">

                </td>
                <td colspan="1" style="width: 33.3%; padding-right: 20px; text-align: end;">
                    <p style="margin: 0 0 8px 0; margin-left: auto; padding: 8px; border: 1px solid black; width: fit-content; border-radius: 8px;">Monto: <span style="color: #0b655b">${amount} $</span></p>
            </td>
            </tr>
            
            <tr >
                <td colspan="3" style="width: 100%; padding: 0 20px;">
                    <p style="margin: 0 0 8px 0; ">Residente: <span style="color: #0b655b">${fullname}</span></p>
                </td>
            </tr>
            <tr>
                <td colspan="3" style="width: 100%; padding: 0 20px;">
                    <p style="margin: 0 0 8px 0; ">Cantidad de: <span style="color: #0b655b">${quantity_letters}</span></p>
                </td>
            </tr>
            <tr>
                <td colspan="3" style="width: 100%; padding: 0 20px;">
                    <p style="margin: 0 0 8px 0; ">Por concepto de: <span style="color: #0b655b">${concept}</span></p>
                </td>
            </tr>
            <tr >
                <td colspan="3"  style="width: 100%; padding: 0 20px;">
                    <table cellspacing="0" cellpadding="8px" style="width: fit-content;" >
                        <thead>
                            <tr>
                                <th></th>
                                <th>Día</th>
                                <th>Mes</th>
                                <th>Año</th>
                            </tr>
                        </thead>
                        <tbody >
                            <tr>
                                <td>Ambato</td>
                                <td style="border: 1px solid black; border-right: 0; border-top-left-radius: 8px; border-bottom-left-radius: 8px;">${day}</td>
                                <td style="border: 1px solid black; border-right: 0;">${month}</td>
                                <td style="border: 1px solid black; border-top-right-radius: 8px; border-bottom-right-radius: 8px;">${year}</td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <!-- <tr>
                <td colspan="3" style="text-align: right; padding-right: 60px;">
                    <p>Firma y sello</p>
                </td>
            </tr> -->
        </table>
        </div>
        <p style="text-align: center;
        font-size: 16px;
        color: red;">Nota: Este recibo es un respaldo</p>
    </main>
</body>

</html>