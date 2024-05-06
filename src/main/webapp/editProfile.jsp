<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Edit Profile - The Caribou Inn</title>
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
            <h1><a href="index.html">Caribou Inn</a></h1>
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
                                <% if (session.getAttribute("isAdmin") != null) { %>
                                    <li><a href="employeePage.jsp">Administrator Portal</a></li>
                                <% } %>
                                <li><a href="index.jsp">Home</a></li>
                                <li><a href="generic.html">Amenities</a></li>
                                <li><a href="elements.html">Reserve a Room</a></li>
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
                        <h3>Update Your Profile</h3>
                        <p>Current information:</p>
                        <p> <%= session.getAttribute("firstName") %> <%= session.getAttribute("lastName") %> </p>
                        <p> <%= session.getAttribute("email") %></p>
                        <form method="post" action="AccountServlet" id="updateProfileForm">
                            <div class="row gtr-uniform">
                                <div class="col-6">
                                    <label>First Name</label>
                                    <input type="text" name="firstName" id="firstName" value="<%= session.getAttribute("firstName") %>" placeholder="First Name" />
                                </div>
                                <div class="col-6">
                                    <label>Last Name</label>
                                    <input type="text" name="lastName" id="lastName" value="<%= session.getAttribute("lastName") %>" placeholder="Last Name" />
                                </div>
                                <div class="col-12">
                                    <label>Email</label>
                                    <input type="email" name="email" id="email" value="<%= session.getAttribute("email") %>" placeholder="Email" />
                                </div>
                                <div class="col-12">
								    <% if (session.getAttribute("hasCardInfo") != null && (Boolean) session.getAttribute("hasCardInfo")) { %>
								        <button type="button" onclick="location.href='manageCardDetails.jsp';">Update Card on File?</button>
								    <% } else { %>
								        <button type="button" onclick="location.href='manageCardDetails.jsp';">Add a Card on File?</button>
								    <% } %>
								</div>
                                <div class="col-12">
                                    <label>New Password</label>
                                    <input type="password" name="newPassword" id="newPassword" value="" placeholder="New Password" />
                                </div>
                                <div class="col-12">
                                    <label>Old Password (Required)</label>
                                    <input type="password" name="oldPassword" id="oldPassword" value="" placeholder="Old Password" />
                                </div>
                                <div class="col-12">
                                    <input type="hidden" name="action" value="updateProfile" />
                                </div>
                                <!-- Break -->
                                <div class="col-12">
                                    <ul class="actions">
                                        <li><input type="submit" value="Update Profile" class="primary" /></li>
                                        <li><a href="index.jsp" class="button">Back to Home</a></li>
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
