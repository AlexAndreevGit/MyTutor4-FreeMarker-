<!DOCTYPE html>
<html lang="en">

<#include "fragments/head.ftl">

<body>

<#include "fragments/header.ftl">

    <main class="container py-4">

        <div class="about-hero">
            <div class="row align-items-center">
                <div class="col-lg-6 mb-4 mb-lg-0">
                    <div class="about-image-container">
                        <img class="about-image"
                             src="https://www.mystipendium.de/sites/default/files/styles/width_1003/public/images/bewerbung/nachhilfe_geben.jpg?itok=DntH9A84"
                             alt="Students tutoring">
                    </div>
                </div>

                <div class="col-lg-6">
                    <div class="about-content">
                        <h1 class="main-title">MyTutor FM</h1>
                        <p class="about-description">MyTutor is a specialized platform designed to facilitate connections
                            between students seeking academic support and qualified peer tutors eager to share their
                            expertise.</p>
                        <a class="btn-action primary" href="/home">
                            <i class="fas fa-search"></i> Find your tutor now!
                        </a>
                    </div>
                </div>

            </div>
        </div>

        <!-- Mission Statement Section -->
        <div class="mission-section">
            <div class="mission-content">
                <i class="fas fa-graduation-cap mission-icon"></i>
                <p class="mission-text">We specialize in facilitating academic connections across mathematics, informatics,
                    and other disciplines.</p>
            </div>
        </div>

        <!-- Subject Cards Section -->
        <div class="subjects-section" >
            <h2 class="section-title" >Our Tutoring Areas</h2>

            <div class="row" >     <!-- VB row not found in the css-->


                <div class="col-md-6 mb-6" >
                    <div class="subject-card" >
                        <div class="subject-icon" >
                            <i class="fas fa-square-root-alt"></i>
                        </div>

                        <h3 class="subject-title">Mathematics</h3>
                        <p class="subject-description">Mathematics is the foundational discipline that explores number,
                            quantity, and space through rigorous analytical frameworks. It provides powerful tools for
                            identifying patterns, analyzing structures, and modeling changes in the physical world through
                            systematic logical reasoning and precise quantitative methods.</p>
                        <a href="https://en.wikipedia.org/wiki/Mathematics" class="subject-link" target="_blank">
                            Learn more &rarr;
                        </a>

                    </div>
                </div>


                <div class="col-md-6 mb-6">
                    <div class="subject-card">
                        <div class="subject-icon">
                            <i class="fas fa-laptop-code"></i>
                        </div>

                        <h3 class="subject-title">Informatics</h3>
                        <p class="subject-description">Informatics is the interdisciplinary study of information processing,
                            management, and dissemination, encompassing the design, implementation, and evaluation of
                            computational systems and their impact on society.</p>
                        <a href="https://en.wikipedia.org/wiki/Informatics" class="subject-link" target="_blank">
                            Learn more &rarr;
                        </a>
                    </div>
                </div>

            </div>

        </div>


    </main>

<#include "fragments/footer.ftl">

</body>

</html>