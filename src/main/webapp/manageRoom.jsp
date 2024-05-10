<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Manage Room - The Caribou Inn</title>
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
                        <h3>Manage Room Details</h3>
                        <form method="post" action="UpdateOrAddRoom">
                            <div class="row gtr-uniform">
                                <div class="col-6">
                                    <label for="roomNumber">Room Number</label>
                                    <input type="text" name="roomNumber" id="roomNumber" value="${roomDetails.roomNumber}" placeholder="Room Number" required />
                                </div>
                                <div class="col-6">
                                    <label for="bedNumber">Bed Number</label>
                                    <input type="text" name="bedNumber" id="bedNumber" value="${roomDetails.bedNumber}" placeholder="Bed Number" required />
                                </div>
                                <div class="col-6">
                                    <label for="bedSize">Bed Size</label>
                                    <input type="text" name="bedSize" id="bedSize" value="${roomDetails.bedSize}" placeholder="Bed Size" required />
                                </div>
                                <div class="col-6">
                                    <label for="price">Price</label>
                                    <input type="text" name="price" id="price" value="${roomDetails.price}" placeholder="Price" required />
                                </div>
                                <div class="col-6">
                                    <label>Room has a balcony:</label>
                                    <div>
                                        <input type="radio" name="hasBalcony" id="hasBalconyYes" value="true" ${roomDetails.hasBalcony ? "checked" : ""} />
                                        <label for="hasBalconyYes">Yes</label>
                                        <input type="radio" name="hasBalcony" id="hasBalconyNo" value="false" ${!roomDetails.hasBalcony ? "checked" : ""} />
                                        <label for="hasBalconyNo">No</label>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <label>Room is non-smoking:</label>
                                    <div>
                                        <input type="radio" name="nonsmoking" id="nonsmokingYes" value="true" ${roomDetails.nonsmoking ? "checked" : ""} />
                                        <label for="nonsmokingYes">Yes</label>
                                        <input type="radio" name="nonsmoking" id="nonsmokingNo" value="false" ${!roomDetails.nonsmoking ? "checked" : ""} />
                                        <label for="nonsmokingNo">No</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <ul class="actions">
                                        <li><input type="submit" value="Submit" class="primary" /></li>
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
