<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Invoice</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
        }

        .page-header {
            margin-top: 20px;
            margin-bottom: 20px;
            padding-bottom: 9px;
            border-bottom: 1px solid #eee;
        }

        .invoice-info {
            margin-top: 20px;
        }

        .table-responsive {
            margin-top: 20px;
        }

        .table th, .table td {
            text-align: center;
            vertical-align: middle;
            font-size: 12px; /* Adjust font size */
            padding: 8px; /* Adjust padding */

        }

        .table th {
            width: 25%; /* Ensure all columns are evenly spaced */
        }

        .page-header {
            margin-left: 300px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-12">
            <h2 class="page-header">
                <i class="fa fa-globe"></i> E-Invoice
                <small class="float-right">Date: 28/05/2024</small>
            </h2>
        </div>
    </div>

    <div class="row invoice-info">
        <div class="col-sm-6">
            <strong>From</strong>
            <address>
                Shop: <strong>Car For You</strong><br/>
                Address: <strong>Nam Dinh</strong><br/>
                Phone: <strong>0794 070 559</strong><br/>
                Email: <strong>dhtcntt03@gmail.com</strong><br/>
            </address>
        </div>

        <div class="col-sm-6" style="margin-top: 5%">
            <strong>To</strong>
            <address>
                Customer: <strong>${order.user.userName}</strong><br/>
                Customer address: <strong>${order.addressShip}</strong><br/>
                Customer phone: <strong>${order.phone}</strong><br/>
                Email: <strong>${order.user.email}</strong><br/>
            </address>
        </div>
    </div>

    <div class="row" style="margin-top: 5%">
        <div class="col-12 table-responsive">
            <table class="table table-striped" style="margin-left: -80px;width: 80%">
                <thead>
                <tr>
                    <th>Quantity</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Subtotal</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDetails as item>
                    <tr>
                        <td>${item.quantity}</td>
                        <td>${item.product.productName}</td>
                        <td>${item.price} $</td>
                        <td>${item.price * item.quantity} $</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
