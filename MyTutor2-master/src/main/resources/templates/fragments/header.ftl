
<header>
    <nav class="navbar navbar-expand-lg navbar-light modern-navbar">
        <a class="navbar-brand" style="margin-left: 10px;" href="/home">
            <i class="fas fa-chalkboard-teacher brand-icon"></i>
            <span class="brand-text">MyTutor4</span>
        </a>

        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item" >
                <a class="nav-link-button" href="/home">
                    <i class="fas fa-home nav-icon"></i> Home
                </a>
            </li>

            <#if isAuthenticated?? && isAuthenticated>
<#--            <#if security.isAuthenticated()>-->

                <li class="nav-item">
                    <a class="nav-link-button" href="/tutorials/add">
                        <i class="fas fa-plus-circle nav-icon"></i> Add Offer
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link-button" href="/tutorials/infoFM">
                        <i class="fas fa-laptop-code nav-icon"></i> Informatics
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link-button" href="/tutorials/mathFM">
                        <i class="fas fa-square-root-alt nav-icon"></i> Mathematics
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link-button" href="/tutorials/otherFM">
                        <i class="fas fa-chart-line nav-icon"></i> Other subjects
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link-button" href="/tutorials/ask-questionFM">
                        <i class="fas fa-robot nav-icon"></i> TutorBot
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link-button" href="/users/my-informationFM">
                        <i class="fas fa-user nav-icon"></i> My Information
                    </a>
                </li>
            </#if>

            <#if hasRole_Admin?? && hasRole_Admin>
                <li class="nav-item">
                    <a class="nav-link-button" href="/admin/statisticsFM">
                        <i class="fas fa-chart-bar nav-icon"></i> Statistics
                    </a>
                </li>
            </#if>

            <li class="nav-item">
                <a class="nav-link-button" href="/about-us">
                    <i class="fas fa-info-circle nav-icon"></i> About Us
                </a>
            </li>


        </ul>

        <div class="d-flex">
            <ul class="navbar-nav">
                <#if !isAuthenticated?? || !isAuthenticated>


                    <li class="nav-item">
                        <a class="btn-login" href="/users/loginFM">
                            <i class="fas fa-sign-in-alt nav-icon"></i> Login
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="btn-register" href="/users/registerFM" style="margin-right: 5px;">
                            <i class="fas fa-user-plus nav-icon"></i> Register
                        </a>
                    </li>

                </#if>

                <#if isAuthenticated?? && isAuthenticated>

                    <div class="weather-information">
                            <h3 style="margin-right: 10px">City: <span id="weather-city"></span></h3>
                            <h3>Temp: <span id="weather-temp"></span></h3>
                    </div>

                    <li class="nav-item">
                        <form method="post" action="/users/logout">

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                            <button class="btn-logout" type="submit">
                                <i class="fas fa-sign-out-alt nav-icon" style="margin-right: 8px;"></i> Logout
                            </button>
                        </form>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            fetch('/weather/berlin')
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    return response.json();
                })
                .then(data => {
                    // Populate the HTML elements with the weather data
                    document.getElementById("weather-city").textContent = data.city;
                    document.getElementById("weather-temp").textContent = data.temperature + " " + data.units;
                })
                .catch(error => {
                    console.error("There was a problem with the fetch operation:", error);
                });
        });
    </script>
</header>
