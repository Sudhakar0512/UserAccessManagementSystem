<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Software Listing</title>
    <link rel="stylesheet" href="../css/softwareListing.css">
</head>
<body>
    <div class="container-listing">
        <!-- Title Section -->
        <div class="header-section">
            <h2 class="title-listing">Software Listing</h2>
            <div class="btn-create-container">
                <a href="createSoftware.jsp" class="btn-create-software">Create Software</a>
            </div>
        </div>

        <!-- Message Display -->
        <c:if test="${not empty message}">
            <div class="alert alert-warning message-listing">${message}</div>
        </c:if>

        <!-- Software Listing Information -->
        <c:if test="${not empty softwareList}">
            <div class="software-info">Software List has data: ${softwareList.size()} entries.</div>
        </c:if>

        <!-- Search Bar -->
        <div class="search-container">
            <input id="searchBar" class="search-bar" placeholder="Search software by name...">
        </div>

        <!-- Software Cards Display -->
        <div class="software-cards-container">
            <c:forEach var="software" items="${softwareList}">
                <div class="software-card">
                    <h3 class="software-name">${software.name}</h3>
                    <p class="software-description">${software.description}</p>
                    <p class="access-levels">Access Levels: ${software.accessLevels}</p>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
        // Search functionality
        document.getElementById('searchBar').addEventListener('keyup', function () {
            const filter = this.value.toLowerCase();
            const cards = document.querySelectorAll('.software-card');

            cards.forEach(card => {
                const name = card.querySelector('.software-name').textContent.toLowerCase();
                card.style.display = name.includes(filter) ? '' : 'none';
            });
        });
    </script>
</body>
</html>
