<%@ page import="java.util.*, com.aryan.Product" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String search = request.getParameter("search");
    String category = request.getParameter("category");
%>

<!DOCTYPE html>
<html>
<head>
<title>Products | SmartBuy</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
body {
    background: radial-gradient(circle at top, #1f2933, #0f172a);
    font-family: 'Poppins', sans-serif;
    color: #e5e7eb;
    margin: 0;
    padding: 30px;
}

/* NAVBAR */
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.logo {
    font-size: 22px;
    font-weight: 600;
}

.nav-actions a {
    margin-left: 12px;
    padding: 8px 14px;
    border-radius: 8px;
    background: rgba(255,255,255,0.1);
    color: #e5e7eb;
    text-decoration: none;
    font-size: 13px;
}

/* HEADER FEATURE BUTTONS */
.header-features {
    display: flex;
    gap: 12px;
    margin-bottom: 25px;
}

.header-features a {
    padding: 10px 16px;
    border-radius: 20px;
    font-size: 13px;
    text-decoration: none;
    background: rgba(255,255,255,0.1);
    color: #c7d2fe;
}

/* FILTERS */
.filters {
    display: flex;
    gap: 12px;
    margin-bottom: 25px;
}

.filters input,
.filters select {
    padding: 12px;
    border-radius: 10px;
    border: none;
    background: rgba(255,255,255,0.1);
    color: #e5e7eb;
}

.filters button {
    padding: 12px 18px;
    border-radius: 10px;
    border: none;
    background: #6366f1;
    color: white;
    cursor: pointer;
}

/* PRODUCT GRID */
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

/* ACTION BUTTONS */
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

/* BUTTON STYLES */
.view {
    background: #22c55e;
    color: #022c22;
}

.compare {
    background: rgba(255,255,255,0.15);
    color: #818cf8;
}

.like {
    background: rgba(255,255,255,0.12);
    color: #f472b6;
}

.like.liked {
    background: #22c55e;
    color: #022c22;
}
</style>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">SmartBuy</div>
    <div class="nav-actions">
        <a href="products">Products</a>
        <a href="logout">Logout</a>
    </div>
</div>

<!-- HEADER FEATURE BUTTONS -->
<div class="header-features">
   <a href="popular">🔥 Popular</a>
    <a href="recommendations">⭐ Recommended</a>
    <a href="also-clicked">👥 Users Also Clicked</a>
</div>

<!-- SEARCH + FILTER -->
<form class="filters" method="get" action="products">
    <input type="text" name="search" placeholder="Search products"
           value="<%= search != null ? search : "" %>">

    <select name="category">
        <option value="">All Categories</option>
        <option value="Mobile">Mobiles</option>
        <option value="Laptop">Laptops</option>
        <option value="TV">TV</option>
        <option value="Headphone">Headphones</option>
        <option value="Shoes">Shoes</option>
    </select>

    <button type="submit">Apply</button>
</form>

<!-- PRODUCTS -->
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
            <!-- View Deal -->
            <a class="view" href="<%= p.getLink() %>" target="_blank">
                View Deal
            </a>

            <!-- Compare -->
            <a class="compare" href="compare?productId=<%= p.getProductId() %>">
                Compare
            </a>

            <!-- ❤️ LIKE -->
            <a class="like"
               href="javascript:void(0)"
               onclick="likeProduct(<%= p.getProductId() %>, this)">
                ❤️ Like
            </a>
        </div>
    </div>
<%
        }
    } else {
%>
    <p style="color:#9ca3af;">No products found.</p>
<%
    }
%>
</div>

<!-- ❤️ LIKE SCRIPT -->
<script>
function likeProduct(productId, btn) {
    fetch("click?productId=" + productId)
        .then(res => {
            if (res.ok) {
                btn.innerText = "❤️ Liked";
                btn.classList.add("liked");
            } else {
                alert("Like failed");
            }
        })
        .catch(err => console.error(err));
}
</script>

</body>
</html>
