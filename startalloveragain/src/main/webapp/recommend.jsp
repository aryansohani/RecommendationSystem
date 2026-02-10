<%@ page import="java.util.*, com.aryan.Product" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html>
<head>
<title>Recommended For You | SmartBuy</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

<style>
body {
    background: radial-gradient(circle at top, #1f2933, #0f172a);
    font-family: 'Poppins', sans-serif;
    color: #e5e7eb;
    padding: 30px;
}

h2 {
    margin-bottom: 25px;
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

.brand {
    color: #9ca3af;
    font-size: 14px;
}

.price {
    color: #22c55e;
    font-size: 18px;
    font-weight: 600;
    margin: 10px 0;
}

.actions {
    display: flex;
    gap: 10px;
    margin-top: 14px;
}

.actions a {
    padding: 8px 14px;
    border-radius: 10px;
    font-size: 13px;
    text-decoration: none;
}

.view {
    background: #22c55e;
    color: #022c22;
}

.back {
    background: rgba(255,255,255,0.15);
    color: #c7d2fe;
}
</style>
</head>

<body>

<h2>⭐ Recommended for You</h2>

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
            <a class="back" href="products">Back</a>
        </div>
    </div>
<%
        }
    } else {
%>
    <p>No recommendations yet. Like some products ❤️</p>
<%
    }
%>
</div>

</body>
</html>
