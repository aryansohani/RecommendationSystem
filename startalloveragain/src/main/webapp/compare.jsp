<%@ page import="java.util.*, com.aryan.Product, java.net.URLEncoder" %>

<%
    List<Product> list = (List<Product>) request.getAttribute("compareList");
%>

<!DOCTYPE html>
<html>
<head>
<title>Compare Prices | SmartBuy</title>

<style>
body {
    background: #0f172a;
    font-family: Poppins, Arial;
    color: #e5e7eb;
    padding: 40px;
}

h2 {
    margin-bottom: 20px;
}

.table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

.table th, .table td {
    padding: 14px;
    border-bottom: 1px solid rgba(255,255,255,0.1);
    text-align: left;
}

.best {
    background: rgba(34,197,94,0.15);
}

.price {
    color: #22c55e;
    font-weight: 600;
}

a {
    color: #818cf8;
    text-decoration: none;
}
</style>
</head>

<body>

<h2>Compare Prices</h2>

<table class="table">
<tr>
    <th>Website</th>
    <th>Price</th>
    <th>Action</th>
</tr>

<%
    if (list != null && !list.isEmpty()) {
        boolean first = true;
        for (Product p : list) {
%>
<tr class="<%= first ? "best" : "" %>">
    <td><%= p.getBrand() %></td>
    <td class="price">₹ <%= p.getPrice() %></td>
    <td>
        <a href="<%= p.getLink() %>" target="_blank">
            Visit Site
        </a>
    </td>
</tr>
<%
            first = false;
        }
    }
%>

</table>

<p style="margin-top:20px;color:#9ca3af;">
Highlighted row shows the <b>best price</b>.
</p>

<a href="products">⬅ Back to Products</a>

</body>
</html>
