<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

    <main class="container py-4">

        <div class="div-home" >

            <div class="stats-header-home">
                <h1 class="main-title">Welcome to MyTutor 4</h1>
            </div>

            <div class="div-home-two-boxes" >
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-solid fa-book-open"></i>
                    </div>
                    <h3 class="feature-title">Find the perfect tutor for you</h3>
                    <p class="feature-text">Connect with knowledgeable tutors who specialize in Informatics, Mathematics,
                        and other disciplines to help you master challenging concepts.</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-chalkboard-teacher"></i>
                    </div>
                    <h3 class="feature-title">Submit your oun tutoring offer</h3>
                    <p class="feature-text">If you already have advanced knowledge in the areas of Informatics, Mathematics,
                        or any other discipline, you can submit your offer and be contacted by other students. </p>
                </div>

            </div>

        </div>

        <div class="div-home" >
            <div class="stats-header">
                <h1 class="main-title">An overview of our tutoring offers</h1>
            </div>

            <div class="stats-dashboard">
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-laptop-code"></i>
                    </div>
                    <div class="stat-content">
                        <h3 class="stat-value" >${countInformaticsTutorials}</h3>
                        <p class="stat-label">Informatics Offers</p>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-square-root-alt"></i>
                    </div>
                    <div class="stat-content">
                        <h3 class="stat-value" >${countMathematicsTutorials}</h3>
                        <p class="stat-label">Mathematics Offers</p>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <div class="stat-content">
                        <h3 class="stat-value" >${countOtherTutorials}</h3>
                        <p class="stat-label">Other Offers</p>
                    </div>
                </div>

            </div>

            <!-- Optional: Total Stats Summary -->
            <div class="stats-summary">
                <#assign total = countInformaticsTutorials + countMathematicsTutorials + countOtherTutorials>

                <#--The <script> element is used to include Java Script-->
                <script>
                    const total = ${total};
                    console.log("Total tutoring offers:", total);
                </script>

                <#assign infoWidth = ((countInformaticsTutorials * 100) / total)?round>
                <#assign mathWidth = ((countMathematicsTutorials * 100) / total)?round>
                <#assign otherWith = ((countOtherTutorials * 100)/total)?round>

                <div class="summary-content">
                    <h3 class="summary-title">
                        <i class="fas fa-graduation-cap me-2"></i>
                        Total Tutoring Offers:
                        <span class="sum-tutoring-offers">
                                ${total}
                            </span>
                    </h3>

                </div>

                <div class="summary-chart">
                    <div class="chart-segment informatics"
                         style="width: ${(infoWidth)}%"></div>
                    <div class="chart-segment mathematics"
                         style="width: ${(mathWidth)}%"></div>
                    <div class="chart-segment datascience"
                         style="width: ${(otherWith)}%"></div>
                </div>

                <div class="chart-legend">
                    <div class="legend-item">
                        <span class="legend-color informatics"></span>
                        <span class="legend-label">Informatics</span>
                    </div>
                    <div class="legend-item">
                        <span class="legend-color mathematics"></span>
                        <span class="legend-label">Mathematics</span>
                    </div>
                    <div class="legend-item">
                        <span class="legend-color datascience"></span>
                        <span class="legend-label">Other</span>
                    </div>
                </div>

            </div>
        </div>

        <div class="div-home" >

            <h1 class="main-title" style="text-align: center">Get Started Today</h1>

            <p class="feature-text" style="text-align: center">Join our tutoring platform to find expert help or offer your teaching
                services.</p>

            <div class="picture-section-actions" >

                <a href="/users/loginFM" class="btn-login">
                    <i class="fas fa-sign-in-alt nav-icon"></i> Login
                </a>

                <a href="/users/registerFM" class="btn-register">
                    <i class="fas fa-user-plus nav-icon"></i> Register
                </a>

            </div>
        </div>

    </main>

<#include "fragments/footer.ftl">

</body>

</html>