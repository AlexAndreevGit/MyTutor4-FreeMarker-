<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

<main class="container py-4">

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

            <div class="stat-content-statistics">
                <h3 class="stat-value" >${countAllUsers}</h3>
                <p class="stat-label">Registered Tutors</p>
            </div>
            <div class="stat-chart tutors-chart"></div>
        </div>

    </div>

</main>

<#include "fragments/footer.ftl">

</body>

</html>