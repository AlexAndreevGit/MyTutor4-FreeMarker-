<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

<main class="container py-4">

    <div class="form-container">
        <div class="form-icon">
            <i class="fas fa-user-circle"></i>
        </div>

        <div class="form-header">
            <h1 class="form-title">Welcome Back FM</h1>
            <p class="form-subtitle">Sign in to access your tutoring account</p>
        </div>

        <form action="/users/login"
              method="POST">

            <div class="form-group">
                <label class="form-label" for="username">Username</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder="Enter your username. Example: user1"
                       value="${userLogInDTO.username}"/>
            </div>

            <div class="form-group">
                <label class="form-label" for="password">Password</label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder="Enter your password. Example: 12345"
                       value="${userLogInDTO.password}"/>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">
                    <i class="fas fa-sign-in-alt me-2"></i> Login
                </button>
            </div>
        </form>

    </div>


</main>

<#include "fragments/footer.ftl">

</body>

</html>