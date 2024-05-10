<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Edit Profile As Admin - The Caribou Inn</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
</head>
<body>

    <!-- Page Wrapper -->
    <div id="page-wrapper">

        <!-- Header -->
        <header id="header" class="alt">
            <h1><a href="index.jsp">Caribou Inn</a></h1>
            <nav id="nav">
							<ul>
								<li class="special">
							        <% if (session.getAttribute("firstName") != null) { %>
							            Hello, <%= session.getAttribute("firstName") %>
							        <% } else { %>
							            Hello, Guest
							        <% } %>
							    </li>
								<li class="special">
									<a href="#menu" class="menuToggle"><span>Menu</span></a>
									<div id="menu">
										<ul>
											<!--MENU LIST THAT IS DEPENDANT ON USER ACCOUNT  -->
											<% if (session.getAttribute("isAdmin") != null) { %>
												<li><a href="employeePage.jsp">Administrator Portal</a></li>
		                                    <% } %>
											<li><a href="index.jsp">Home</a></li>
											<li><a href="chooseRoomsAsGuest.jsp">Reserve a Room</a></li>
											<li><a href="ViewReservations">Your Reservations</a></li>
											<% if (session.getAttribute("firstName") != null) { %>
												<li><a href="editProfile.jsp">Edit Profile</a></li>
                                      	 		<li><a href="AccountServlet?action=logout">Sign Out</a></li>
		                                    <% } else { %>
		                                        <li><a href="login.html">Log In or Sign Up</a></li>
		                                    <% } %>
										</ul>
									</div>
								</li>
							</ul>
						</nav>
        </header>

        <!-- Main -->
        <article id="main">
            <section class="wrapper style5">
                <div class="inner">
                    <section>
                        <h3>Edit Another User's Profile</h3>
                        <form method="post" action="AccountServlet">
                            <div class="row gtr-uniform">
                                <div class="col-6">
                                    <label for="otherUserFirstName">First Name</label>
                                    <input type="text" name="otherUserFirstName" id="otherUserFirstName" value="<%= request.getAttribute("otherUserFirstName") %>" placeholder="First Name" />
                                </div>
                                <div class="col-6">
                                    <label for="otherUserLastName">Last Name</label>
                                    <input type="text" name="otherUserLastName" id="otherUserLastName" value="<%= request.getAttribute("otherUserLastName") %>" placeholder="Last Name" />
                                </div>
                                <div class="col-6">
                                    <label for="otherUserEmail">Email</label>
                                    <input type="email" name="otherUserEmail" id="otherUserEmail" value="<%= request.getAttribute("otherUserEmail") %>" placeholder="Email" readonly />
                                </div>
                                <div class="col-6">
                                    <label>New Password</label>
                                    <input type="password" name="otherUserNewPassword" id="otherUserNewPassword" value="" placeholder="New Password" />
                                </div>
                                <div class="col-12">
								    <label>User is an Admin:</label>
								    <div>
								        <input type="radio" name="otherUserIsAdmin" id="otherUserIsAdminYes" value="true" <%= request.getAttribute("otherUserIsAdmin") != null && (Boolean) request.getAttribute("otherUserIsAdmin") ? "checked" : "" %> />
								        <label for="otherUserIsAdminYes">Yes</label>
								    </div>
								    <div>
								        <input type="radio" name="otherUserIsAdmin" id="otherUserIsAdminNo" value="false" <%= request.getAttribute("otherUserIsAdmin") != null && !(Boolean) request.getAttribute("otherUserIsAdmin") ? "checked" : "" %> />
								        <label for="otherUserIsAdminNo">No</label>
								    </div>
								</div>
                                <!-- Admin verification -->
                                <div class="col-6">
                                    <label for="adminEmail">Your Email (for verification)</label>
                                    <input type="email" name="adminEmail" id="adminEmail" placeholder="Your Email" />
                                </div>
                                <div class="col-6">
                                    <label for="adminPassword">Your Password (for verification)</label>
                                    <input type="password" name="adminPassword" id="adminPassword" placeholder="Your Password" />
                                </div>
                                <div class="col-12">
                                	<input type="hidden" name="action" value="updateOtherUser" />
                                    <ul class="actions">
                                        <li><input type="submit" value="Update Profile" class="primary" /></li>
                                        <li><a href="employeePage.jsp" class="button">Cancel</a></li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                    </section>
                </div>
            </section>
        </article>

        <!-- Footer -->
        <footer id="footer">
            ...
        </footer>

    </div>

    <!-- Scripts -->
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/jquery.scrollex.min.js"></script>
    <script src="assets/js/jquery.scrolly.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>

</body>
</html>
