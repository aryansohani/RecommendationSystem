<%@ page import="java.util.*, com.aryan.Product" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html>
<head>
<title>🔥 Popular Products</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
body {
    background: radial-gradient(circle at top, #1f2933, #0f172a);
    font-family: 'Poppins', sans-serif;
    color: #e5e7eb;
    padding: 30px;
}

.grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
    gap: 22px;
}

.card {
    background: rgba(255,255,255,0.06);
    padding: 20px;
    border-radius: 16px;
}

.brand { color: #9ca3af; }
.price { color: #22c55e; font-size: 18px; font-weight: 600; }

.actions {
    margin-top: 14px;
    display: flex;
    gap: 10px;
}

.actions a {
    padding: 8px 14px;
    border-radius: 10px;
    font-size: 13px;
    text-decoration: none;
}

.view { background: #22c55e; color: #022c22; }
.compare { background: rgba(255,255,255,0.15); color: #818cf8; }
</style>
</head>

<body>

<h2>🔥 Popular Products</h2>

<div class="grid">
<%
if (products != null && !products.isEmpty()) {
    for (Product p : products) {
%>
    <div class="card">
        <h3><%= p.getProductName() %></h3>
        <div class="brand"><%= p.getBrand() %></div>
        <div class="price">₹ <%= p.getPrice() %></div>

        <div class="actions">
            <a class="view" href="<%= p.getLink() %>" target="_blank">View Deal</a>
            <a class="compare" href="compare?productId=<%= p.getProductId() %>">Compare</a>
        </div>
    </div>
<%
    }
} else {
%>
    <p>No popular products yet.</p>
<%
}
%>
</div>

</body>
</html>
