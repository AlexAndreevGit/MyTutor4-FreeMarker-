<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>


<#include "fragments/header.ftl">

<main class="container py-4">

    <div class="offers-container">

        <div class="general-header" >
            <h1 class="main-title">
                Informatics Offers
            </h1>
            <p class="general-subtitle">Find tutoring services for informatics topics</p>
        </div>

        <div class="div-search-bar" style="display: flex; gap: 10px; flex-wrap: wrap;">

            <form action="/tutorials/informaticsFind" method="get" style="flex-grow: 1;">
                <div class="input-group">
                    <input type="text"
                           class="form-control"
                           name="searchTerm"
                           placeholder="Search for informatics offers..."
                           value="${searchTerm!}">

                    <button class="btn-action primary" type="submit" id="button-addon2" style="margin-left: 10px;">Search</button>
                </div>
            </form>

            <div class="dropdown">
                <button class="btn-action primary dropdown-toggle" type="button" id="sortingDropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-sort"></i> Sorting method
                </button>

                <div class="dropdown-menu" aria-labelledby="sortingDropdown">
                    <a class="dropdown-item" href="/tutorials/sortAlphabetically?categoryId=2&typeForSort=alphabetical">
                        Sort alphabetically (ascending)
                    </a>
                    <a class="dropdown-item" href="/tutorials/sortAlphabetically?categoryId=2&typeForSort=date">
                        Sort by date (descending)
                    </a>
                </div>
            </div>
        </div>



        <div class="offers-list-container">
            <div class="offers-list">
                <#list informaticsTutorialsAsView as w>
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

<#--                                    <i class="fas fa-user-graduate me-2" style="margin-right: 8px"></i>-->
<#--                                    <p style="margin-right: 40px">${w.emailOfTheTutor}</p>-->

                                    <button type="button" class="btn-addToFavorite" data-bs-toggle="modal" data-bs-target="#emailModal-${w.id}">
                                        <i class="fas fa-user-graduate me-2" style="margin-right: 8px"></i> Send an Email to ${w.emailOfTheTutor}
                                    </button>

                                    <i class="fas fa-calendar-alt" style="margin-left: 0.7rem"></i>
                                    <p style="margin-left: 0.5rem">${w.getCreatedOn()}</p>
                                </div>

                                <div class="btn-addToFavorite">
                                    <i class="fas fa-star" style="color: #007bff;"></i>
                                    <a href="/tutorials/addToFavorite/${w.id}" style="text-decoration: none" > add to favorite</a>
                                </div>

                            </div>

                        </div>

                        <#if !informaticsTutorialsAsView?has_content>
                            <div class="no-off">
                                <i class="fas fa-search fa-3x mb-3"></i>
                                <h3>No mathematics offers available</h3>
                                <p>Check back later or explore other subject areas.</p>
                            </div>
                        </#if>

                    </div>


                    <div class="modal fade" id="emailModal-${w.id}" tabindex="-1" aria-hidden="true">

                        <div class="modal-dialog">

                            <form action="/email/sendEmail" method="post">

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                                <div class="modal-content">

                                    <div class="modal-header">
                                        <h5 class="modal-title">Send Email to ${w.emailOfTheTutor}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>

                                    <div class="modal-body">
                                        <input type="hidden" name="recipientEmail" value="${w.emailOfTheTutor}">
                                        <textarea class="form-control" name="message" placeholder="Type your message..." required></textarea>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-success">Send</button>
                                    </div>

                                </div>

                            </form>

                        </div>

                    </div>


                </#list>

            </div>

        </div>

    </div>

</main>

<#include "fragments/footer.ftl">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>