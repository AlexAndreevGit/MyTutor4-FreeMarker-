<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

<main class="container py-4">

    <div class="offers-container">

        <div class="stats-container" >
            <div class="stats-header">

                <div class="stats-icon">
                    <i class="fas fa-chart-bar"></i>
                </div>
                <h1 class="stats-title">Platform Statistics</h1>
                <p class="stats-subtitle">An overview of our tutoring community</p>

            </div>

        </div>

        <div class="stats-dashboard">

            <div class="stat-card-statistics" >
                <div class="stat-icon">
                    <i class="fas fa-users"></i>
                </div>

                    <p class="stat-label-statistics">Registered Tutors: </p>
                    <h3 class="stat-value" >${countAllUsers}</h3>

            </div>

        </div>

        <div class="offers-list-container">
            <div class="offers-list">

                <#list allUsers as w>
                    <div class="offer-card">

                        <div class="offer-card-content">
                            <div class="offer-statistics">
                                <div class="offer-star-row">
                                    <i class="fas fa-user" style="color: mediumslateblue"></i>
                                    <h4 class="offer-title" style="color: mediumslateblue">Username: </h4>
                                    <h4 class="offer-title">${w.username}</h4>
                                </div>

                                <div class="offer-star-row">
                                    <i class="fas fa-envelope" style="color: mediumslateblue"></i>
                                    <h4 class="offer-title" style="color: mediumslateblue">Email: </h4>
                                    <h4 class="offer-title">${w.email}</h4>
                                </div>


                                <a class="btn-delete-user" href="/users/removeByAdmin/${w.id}" onclick="return confirm('Are you sure you want to delete this user?')">
                                    <i class="fas fa-trash-alt" style="margin-right: 8px;"></i> Delete User
                                </a>
                            </div>

                        </div>

                    </div>
                </#list>

            </div>
        </div>
    </div>
</main>

<#include "fragments/footer.ftl">

</body>

</html>