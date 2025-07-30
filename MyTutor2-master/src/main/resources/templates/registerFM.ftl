<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

<main class="container py-4">

    <div class="form-container">

        <div class="form-icon">
            <i class="fas fa-user-plus"></i>
        </div>

        <div class="form-header">
            <h1 class="form-title">Create Account</h1>
            <p class="form-subtitle">Join our tutoring community today</p>
        </div>

        <form action="/users/register" method="POST" >

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="form-group">
                <label class="form-label" for="username">Username</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       placeholder="Choose a username"
                       name="Username"
                       value="${userRegisterDTO.username!}">

                <#if userRegisterDTO_errors?? && userRegisterDTO_errors.hasFieldErrors("username")>
                    <small class="invalid-feedback">
                        Username length must be between 2 and 40 characters!
                    </small>
                </#if>
            </div>

            <div class="form-group">
                <label class="form-label" for="email">Email</label>
                <input type="email"
                       class="form-control"
                       id="email"
                       placeholder="Enter your email address"
                       name="email"
                       value="${userRegisterDTO.email!}">

                <#if userRegisterDTO_errors?? && userRegisterDTO_errors.hasFieldErrors("email")>
                    <small class="invalid-feedback">
                        Email can't be empty!
                    </small>
                </#if>

            </div>

            <div class="form-group">
                <label class="form-label" for="password">Password</label>
                <input type="password"
                       class="form-control"
                       id="password"
                       placeholder="Create a password"
                       name="password"
                       value="${userRegisterDTO.password!}">

                <#if userRegisterDTO_errors?? && userRegisterDTO_errors.hasFieldErrors("password")>
                    <small class="invalid-feedback">
                        Password length must be between 2 and 40 characters!
                    </small>
                </#if>

            </div>

            <div class="form-group">
                <label class="form-label" for="confirmPassword">Confirm Password</label>
                <input type="password"
                       class="form-control"
                       id="confirmPassword"
                       placeholder="Confirm your password"
                       name="confirmPassword"
                       value="${userRegisterDTO.confirmPassword!}">

                <#if userRegisterDTO_errors?? && userRegisterDTO_errors.hasFieldErrors("confirmPassword")>
                    <small class="invalid-feedback">
                        Password length must be between 2 and 40 characters!
                    </small>
                </#if>

            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">
                    <i class="fas fa-user-plus me-2"></i> Create Account
                </button>
            </div>
        </form>

    </div>

</main>

<#include "fragments/footer.ftl">

</body>

</html>