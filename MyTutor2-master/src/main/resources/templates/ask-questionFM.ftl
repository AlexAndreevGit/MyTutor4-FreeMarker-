<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

<main class="container py-4">

   <div class="general-header">
      <h1 class="main-title">
         Ask TutorBot
      </h1>
      <p class="general-subtitle">Ask the TutorBot any question!</p>
   </div>

 <div class="row justify-content-center">

    <div class="col-md-8">

       <div class="form-group-ask-question" >

          <input type="text" class="form-control" style="margin-right: 5px" id="searchInput"
                 placeholder="Enter Your Question">
          <button id="searchButton" class="btn-submit">Ask</button>

       </div>


       <div id="resultsOuterContainer" class="answer-chatbot-container" >

          <div class="offers-list">
             <div id="searchResults"></div>
          </div>

       </div>

    </div>

 </div>


</main>

<script type="text/javascript" src="/scripts/ask-question.js"></script>


<#include "fragments/footer.ftl">

</body>

</html>