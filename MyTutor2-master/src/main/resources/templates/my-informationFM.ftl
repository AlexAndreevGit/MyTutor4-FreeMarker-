<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>


<#include "fragments/header.ftl">

<main class="container py-4">

    <div style="max-width: 1000px;">

        <div class="profile-header">

            <div class="profile-avatar">
                <i class="fas fa-user-circle"></i>
            </div>

            <h1 class="profile-title" >${userName}</h1>
            <p class="profile-subtitle" >${userEmail}</p>
        </div>

        <div class="profile-stats">

            <div class="stat-card">

                <div class="stat-icon">
                    <i class="fas fa-book"></i>
                </div>

                <div class="stat-content">
                    <h3 class="stat-value" >${numberOfClasses}</h3>
                    <p class="stat-label">Classes</p>
                </div>

            </div>

            <div class="stat-card">

                <div class="stat-icon">
                    <i class="fas fa-euro-sign"></i>
                </div>

                <div class="stat-content">
                    <h3 class="stat-value" >${averagePriceEUR} EUR</h3>
                    <p class="stat-label">Avg. Price (EUR)</p>
                </div>

            </div>

            <div class="stat-card">

                <div class="stat-icon">
                    <i class="fas fa-money-bill-wave"></i>
                </div>

                <div class="stat-content">
                    <h3 class="stat-value" >${averagePriceBGN} Leva</h3>
                    <p class="stat-label">Avg. Price (BGN)</p>
                </div>

            </div>

        </div>

    </div>

    <div class="offers-list-container">

        <div class="section-header">
            <h2 class="section-title">
                <i class="fas fa-clipboard-list me-2"></i>
                My Tutoring Offers
            </h2>

            <a href="/tutorials/add" class="btn-action secondary">
                <i class="fas fa-plus-circle"></i> Add New Offer
            </a>

        </div>

        <div class="offers-list">
            <#list submittedByMeTutorialsAsView as w>
                <div class="offer-card">

                    <div class="offer-card-content">
                        <div class="offer-header">
                            <h4 class="offer-title">${w.name}</h4>
                            <div class="offer-price">
                                <span>${w.price}</span>
                                <span> EUR </span>
                                <span class="price-per">per hour</span>
                            </div>
                        </div>

                        <div class="offer-body">
                            <p class="offer-description">${w.description}</p>
                        </div>

                        <div class="offer-footer">
                            <div class="offer-tutor">
                                <i class="fas fa-user-graduate me-2"></i>
                                <a href="#" class="tutor-email" >${w.emailOfTheTutor}</a>
                            </div>

                        </div>

                        <div class="offer-actions">

                            <a class="btn-delete" href="/tutorials/edit/${w.id}">
                                <i class="fas fa-wrench" style="margin-right: 8px;"></i> Edit
                            </a>

                            <a class="btn-delete" href="/tutorials/remove/${w.id}">
                                <i class="fas fa-trash-alt" style="margin-right: 8px;"></i> Remove
                            </a>

                        </div>

                    </div>

                    <#if !submittedByMeTutorialsAsView?has_content>
                        <div class="no-off">
                            <i class="fas fa-search fa-3x mb-3"></i>
                            <h3>No mathematics offers available</h3>
                            <p>Check back later or explore other subject areas.</p>
                        </div>
                    </#if>


                </div>

            </#list>

        </div>

    </div>

    <div class="div-home-padding-1rem" style="max-width: 1000px;">

        <div style="display: flex ">
            <p class="subject-description" style="padding-top: 1.2rem">Account termination is permanent and cannot be
                undone.</p>

            <div style="text-align: right;">
                <form action="/users/delete-account" method="post">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <button type="submit" class="button-delete-account"
                            onclick="return confirm('Are you sure you want to delete your account? This action cannot be undone.')">
                            <i class="fas fa-trash-alt" style="margin-right: 8px;"></i> Delete account
                    </button>
                </form>
            </div>
        </div>

    </div>

</main>

<#include "fragments/footer.ftl">

</body>

</html>