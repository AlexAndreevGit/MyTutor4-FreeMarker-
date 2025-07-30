<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>


<#include "fragments/header.ftl">

<main class="container py-4">

<#--    <#assign errors = request.getAttribute("org.springframework.validation.BindingResult.tutorialAddDTO")>-->
<#--    <#assign errors = request.getAttribute("org.springframework.validation.BindingResult.tutorialAddDTO")!>-->

    <div class="form-container">
        <div class="form-icon">
            <i class="fas fa-chalkboard-teacher"></i>
        </div>

        <div class="form-header">
            <h1 class="form-title">Add Tutoring Offer FM</h1>
            <p class="form-subtitle">Share your expertise with students who need your help</p>
        </div>

        <form action="/tutorials/add" method="POST">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="form-group">
                <label class="form-label" for="offerName">Name</label>
                <input type="text"
                       class="form-control <#if tutorialAddDTO_errors?? && tutorialAddDTO_errors.hasFieldErrors("name")>is-invalid</#if>"
                       id="offerName"
                       placeholder="Name your tutoring offer"
                       name="name"
                       value="${tutorialAddDTO.name!}">

                <#if tutorialAddDTO_errors?? && tutorialAddDTO_errors.hasFieldErrors("name")>
                    <small class="invalid-feedback">
                        The name length must be between 2 and 40 characters!
                    </small>
                </#if>

            </div>

            <div class="form-group">
                <label class="form-label" for="offerDescription">Offer Description</label>
                <textarea class="form-control"
                          id="offerDescription"
                          rows="3"
                          placeholder="Describe what you can help with"
                          name="description">${tutorialAddDTO.description!}</textarea>

                <#if tutorialAddDTO_errors?? && tutorialAddDTO_errors.hasFieldErrors("description")>
                    <small class="invalid-feedback">
                        The description length must be between 2 and 200 characters!
                    </small>
                </#if>

            </div>

            <div class="form-group">
                <label class="form-label" for="offerPrice">Price Per Hour (BGN)</label>
                <div class="input-group">
                    <input type="text"
                           class="form-control"
                           id="offerPrice"
                           placeholder="Enter your hourly rate"
                           name="price"
                           value="${tutorialAddDTO.price!}">
                    <span class="input-group-text">EUR</span>
                </div>


                <#if tutorialAddDTO_errors?? && tutorialAddDTO_errors.hasFieldErrors("price")>
                    <small class="invalid-feedback">
                        The price should be a positive number!
                    </small>
                </#if>

            </div>

            <div class="form-group">
                <label class="form-label" for="offerCategory">Category</label>
                <select class="form-select"
                        id="offerCategory"
                        name="category">

                    <option value="">Select a category</option>

                    <option value="MATHEMATICS" <#if tutorialAddDTO.category!?string == "MATHEMATICS">selected</#if>>MATHEMATICS</option>
                    <option value="INFORMATICS" <#if tutorialAddDTO.category!?string == "INFORMATICS">selected</#if>>INFORMATICS</option>
                    <option value="OTHER" <#if tutorialAddDTO.category!?string == "OTHER">selected</#if>>OTHER</option>

                </select>

                <#if tutorialAddDTO_errors?? && tutorialAddDTO_errors.hasFieldErrors("category")>
                    <small class="invalid-feedback">
                        Please select a category for your offer.
                    </small>
                </#if>

            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">
                    <i class="fas fa-plus-circle me-2"></i> Create Offer
                </button>
            </div>
        </form>

    </div>

</main>

<#include "fragments/footer.ftl">

</body>

</html>