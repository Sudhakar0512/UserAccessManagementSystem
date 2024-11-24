
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dev.model.Software" %>
<%@ page import="com.dev.model.Request" %>
<%@ page import="com.dev.dao.SoftwareDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Access</title>
    <link rel="stylesheet" href="../css/requestAccess.css">
    <style>
        /* Modal Styles */
        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 9999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.6);
            padding-top: 50px;
            transition: opacity 0.4s ease;
        }

        .modal.show {
            display: block;
            opacity: 1;
        }

        .modal-content {
            background-color: #ffffff;
            margin: 10% auto;
            padding: 30px;
            border: 1px solid #ccc;
            width: 85%;
            max-width: 600px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            border-radius: 12px;
            transition: transform 0.3s ease-out;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .modal-header h2 {
            margin: 0;
            font-size: 20px;
            font-weight: 600;
            color: #333;
            letter-spacing: 0.5px;
        }

        .close-btn {
            font-size: 32px;
            color: #aaa;
            cursor: pointer;
            background: none;
            border: none;
            transition: color 0.3s ease;
        }

        .close-btn:hover {
            color: #333;
        }

        .modal-body {
            font-size: 16px;
            color: #555;
            line-height: 1.6;
        }

        .statusRA {
            font-size: 16px;
            font-weight: bold;
        }

        .status-approved {
            color: #28a745;
            font-weight: bold;
        }

        .status-rejected {
            color: #dc3545;
            font-weight: bold;
        }

        .status-icon {
            margin-left: 10px;
            font-size: 18px;
        }

        /* Edit Form Styles */
        .edit-form {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
        }

        .edit-form h4 {
            font-size: 18px;
            margin-bottom: 10px;
            color: #333;
            font-weight: 600;
        }

        .edit-form textarea {
            width: 100%;
            height: 150px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            line-height: 1.6;
            resize: vertical;
            box-sizing: border-box;
        }

        .edit-form button {
            display: inline-block;
            padding: 12px 20px;
            margin-top: 10px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .edit-form button:hover {
            background-color: #0056b3;
        }

        .edit-form button:active {
            background-color: #004085;
        }

    </style>
</head>
<body>
    <div class="containerRA">
        <!-- Form Card -->
        <div class="form-card">
            <h2 class="titleRA">Request Access to Software</h2>
            <form action="requestAccess" method="POST" class="formRA">
                <div class="form-groupRA">
                    <label for="softwareId">Software:</label>
                    <select name="softwareId" required class="inputRA">
                        <option value="" disabled selected>Select Software</option>
                        <%
                            List<Software> softwareList = (List<Software>) request.getAttribute("softwareDropDown");
                            if (softwareList != null && !softwareList.isEmpty()) {
                                for (Software software : softwareList) {
                        %>
                                    <option value="<%= software.getId() %>"><%= software.getName() %></option>
                        <%
                                }
                            } else {
                        %>
                                    <option value="" disabled>No software available</option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-groupRA">
                    <label for="accessType">Access Type:</label>
                    <select name="accessType" required class="inputRA">
                        <option value="Read">Read</option>
                        <option value="Write">Write</option>
                        <option value="Admin">Admin</option>
                    </select>
                </div>
                <div class="form-groupRA">
                    <label for="reason">Reason for Request:</label>
                    <textarea name="reason" required class="inputRA"></textarea>
                </div>
                <button type="submit" class="buttonRA primaryRA">Submit Request</button>
            </form>
        </div>

        <!-- Requests Display -->
        <div class="request-cardsRA">
            <h2 class="titleRA">Your Requests</h2>
            <div class="card-gridRA">
                <%
                    List<Request> userRequests = (List<Request>) request.getAttribute("userRequests");
                    if (userRequests != null && !userRequests.isEmpty()) {
                        for (Request req : userRequests) {
                            String cardColor = "whiteRA";
                            String message = "";
                            String statusIcon = "";
                            String statusClass = "";

                            if ("Approved".equalsIgnoreCase(req.getStatus())) {
                                cardColor = "approvedRA";
                                message = "You have access to " + req.getAccessType() + " this software.";
                                statusIcon = "✔";  // Checkmark for approved
                                statusClass = "status-approved";  // Green color
                            } else if ("Rejected".equalsIgnoreCase(req.getStatus())) {
                                cardColor = "rejectedRA";
                                message = "You are not allowed to access this software.";
                                statusIcon = "✘";  // Cross for rejected
                                statusClass = "status-rejected";  // Red color
                            }
                %>
                            <div class="cardRA <%= cardColor %>" onclick="handleCardClick(<%= req.getSoftwareId() %>, '<%= req.getStatus() %>', '<%= req.getAccessType() %>')">
                                <h3 class="software-nameRA"><%= req.getSoftwareName() %></h3>
                                <h2 class="software-nameRA"><%= req.getSoftwareId() %></h2>
                                <p class="access-typeRA">Access Type: <%= req.getAccessType() %></p>
                                <p class="statusRA <%= statusClass %>">Status: <%= req.getStatus() %>
                                    <span class="status-icon"><%= statusIcon %></span>
                                </p>
                            </div>
                <%
                        }
                    } else {
                %>
                        <p>No requests found.</p>
                <%
                    }
                %>
            </div>
        </div>
    </div>

    <!-- Modal for Showing Messages -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Request Status</h2>
                <span class="close-btn" onclick="closeModal()">&times;</span>
            </div>
            <div class="modal-body" id="modalMessage"></div>
        </div>
    </div>

    <!-- Modal for Software Details -->
    <div id="softwareModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Software Details</h2>
                <span class="close-btn" onclick="closeSoftwareModal()">&times;</span>
            </div>
            <div class="modal-body" id="softwareDetails"></div>
        </div>
    </div>

    <script>
        function handleCardClick(softwareId, status, accessType) {
            if (status === "Approved") {
                if (softwareId) {
                    fetchSoftwareDetails(softwareId, accessType);
                } else {
                    showModal("Invalid software ID.");
                }
            } else {
                showModal("You are not allowed to access this software.");
            }
        }

        function fetchSoftwareDetails(softwareId, accessType) {
                // Map access types to numeric values
                const accessTypeMap = {
                    'Read': 0,
                    'Write': 1,
                    'Admin': 2
                };

                // Get the numeric value for the access type, defaulting to 0 if unknown
                const accessLevel = accessTypeMap[accessType.trim()] || 0;

                console.log("Fetching software details for ID:", softwareId, "Access Level:", accessLevel);

                if (!softwareId) {
                    console.error("Invalid software ID. Cannot fetch details.");
                    showModal("Invalid software ID.");
                    return;
                }

                const xhr = new XMLHttpRequest();
                xhr.open('GET', '/UserAccessManagementSystem/fetchSoftwareDetails?softwareId=' + softwareId, true);
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        const software = JSON.parse(xhr.responseText);
                        console.log("Software object:", software);
                        console.log("_____")
                        console.log(software.accessLevels);

                        // Create elements for software details
                        const titleElement = document.createElement('h3');
                        titleElement.textContent = software.name;

                        const descriptionElement = document.createElement('p');
                        descriptionElement.textContent = software.description;

                        // Create access level element
                        const accessElement = document.createElement('h3');
                        accessElement.textContent = "Access Level: " + software.accessLevels;

                        // Create a container for the details
                        const detailsContainer = document.createElement('div');
                        detailsContainer.appendChild(titleElement);
                        detailsContainer.appendChild(descriptionElement);
                        detailsContainer.appendChild(accessElement); // Append access element to details container

                        // Add editing form if accessLevel is Write (1) or Admin (2)
                        if (software.accessLevels === "Admin" || software.accessLevels === "Write") {
                            console.log("Caeeedlld");
                            const editForm = document.createElement('div');
                            editForm.classList.add('edit-form');

                            const editTitle = document.createElement('h4');
                            editTitle.textContent = "Edit Description";

                            const editTextarea = document.createElement('textarea');
                            editTextarea.id = "editDescription";
                            editTextarea.textContent = software.description;

                            const saveButton = document.createElement('button');
                            saveButton.textContent = "Save";
                            saveButton.onclick = function() {
                                saveDescription(softwareId);
                            };

                            editForm.appendChild(editTitle);
                            editForm.appendChild(editTextarea);
                            editForm.appendChild(saveButton);
                            detailsContainer.appendChild(editForm);
                        }

                        // Clear previous content and append new content
                        const softwareDetailsElement = document.getElementById("softwareDetails");
                        softwareDetailsElement.innerHTML = ''; // Clear previous content
                        softwareDetailsElement.appendChild(detailsContainer);
                        document.getElementById("softwareModal").classList.add("show");
                    } else {
                        console.error("Error fetching software details:", xhr.status, xhr.statusText);
                        showModal("Error fetching software details.");
                    }
                };
                xhr.onerror = function() {
                    console.error("Request failed.");
                    showModal("Request failed.");
                };
                xhr.send();
            }
function saveDescription(softwareId) {
    const newDescription = document.getElementById("editDescription").value;

    // Validate input
    if (!softwareId || !newDescription) {
        alert("Please provide both software ID and description.");
        return;
    }

    // Create FormData object to send data
    const formData = new FormData();
    formData.append("softwareId", softwareId);
    formData.append("newDescription", newDescription);

    console.log("Form data sent:", {
        softwareId: softwareId,
        newDescription: newDescription
    });

    // Send data using fetch API
    fetch('/UserAccessManagementSystem/jsp/updateSoftwareDescription', {
        method: 'POST',
        body: formData
    })
    .then(message => {
            alert("Successfully Upadted!");
            closeModal()
            closeSoftwareModal()
    })
    .catch(error => alert("Error: " + error));
}

        function showModal(message) {
            document.getElementById("modalMessage").innerText = message;
            document.getElementById("myModal").classList.add("show");
        }

        function closeModal() {
            document.getElementById("myModal").classList.remove("show");
        }

        function closeSoftwareModal() {
            document.getElementById("softwareModal").classList.remove("show");
        }
    </script>
</body>
</html>