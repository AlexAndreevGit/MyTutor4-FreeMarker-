<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>


<#include "fragments/header.ftl">

<main class="container py-4">

    <div class="offers-container">

        <div class="general-header">
            <h1 class="main-title">
                Other Offers
            </h1>
            <p class="general-subtitle">Find tutoring services for other topics</p>
        </div>

        <div class="div-search-bar">

            <form action="/tutorials/otherFind" method="get">
                <div class="input-group">
                    <input type="text"
                           class="form-control"
                           name="searchTerm"
                           placeholder="Search for an offers..."
                           value="${searchTerm!}">

                    <button class="btn-action primary custom-button-spacing" type="submit" id="button-addon2">Search</button>
                </div>
            </form>

        </div>

        <div class="offers-list-container">
            <div class="offers-list">
                <#list otherTutorialsAsView as w>
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

                        </div>

                        <#if !otherTutorialsAsView?has_content>
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

    </div>

</main>

<#include "fragments/footer.ftl">

</body>

</html>