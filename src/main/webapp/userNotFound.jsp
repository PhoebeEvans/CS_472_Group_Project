<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>User Not Found - The Caribou Inn</title>
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
                        <h3>User Not Found</h3>
                        <p>The email address you entered does not match any account in our records. Please check the email address and try again.</p>
                        <ul class="actions">
                            <li><a href="index.jsp" class="button primary">Back to Home</a></li>
                            <li><a href="<%= request.getParameter("referrer") != null ? request.getParameter("referrer") : "employeePage.jsp" %>" class="button">Try Again</a></li>
                        </ul>
                    </section>
                </div>
            </section>
        </article>

        <!-- Footer -->
        <footer id="footer">
            <!-- Footer content -->
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
