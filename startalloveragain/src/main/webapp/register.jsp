<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Account | SmartBuy</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        body {
            height: 100vh;
            background: radial-gradient(circle at top, #1f2933, #0f172a);
            font-family: 'Poppins', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #e5e7eb;
        }

        .card {
            background: rgba(255,255,255,0.06);
            padding: 45px;
            border-radius: 18px;
            width: 420px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.6);
        }

        h2 {
            margin-bottom: 8px;
        }

        p {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 25px;
        }

        input {
            width: 100%;
            padding: 14px;
            margin-bottom: 15px;
            border-radius: 10px;
            border: none;
            background: rgba(255,255,255,0.1);
            color: white;
            font-size: 14px;
        }

        input::placeholder {
            color: #9ca3af;
        }

        button {
            width: 100%;
            padding: 14px;
            border-radius: 10px;
            border: none;
            background: #22c55e;
            color: #022c22;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
        }

        .links {
            text-align: center;
            margin-top: 20px;
            font-size: 13px;
        }

        .links a {
            color: #818cf8;
            text-decoration: none;
        }

        .error {
            background: rgba(239,68,68,0.15);
            color: #fca5a5;
            padding: 10px;
            border-radius: 8px;
            font-size: 13px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="card">
    <h2>Create Account</h2>
    <p>Join SmartBuy and start saving</p>

    <!-- OPTIONAL ERROR MESSAGE -->
    <%
        String error = request.getParameter("error");
        if (error != null) {
    %>
        <div class="error">Registration failed. Try again.</div>
    <%
        }
    %>

    <!-- FORM ONLY -->
    <form action="register" method="post">
        <input type="text" name="name" placeholder="Full name" required>
        <input type="email" name="email" placeholder="Email address" required>
        <input type="password" name="password" placeholder="Password" required>

        <button type="submit">Create Account</button>
    </form>

    <div class="links">
        Already have an account?
        <a href="login.jsp">Login</a>
    </div>
</div>

</body>
</html>
