<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Manage Card Details - The Caribou Inn</title>
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
                        <h3>Manage Card Details</h3>
                        <% if (session.getAttribute("hasCardInfo") != null && (Boolean) session.getAttribute("hasCardInfo")) { %>
                            <p>Would you like to update/replace the card on file ending in <%= session.getAttribute("lastFourCC") %>?</p>
                        <% } else { %>
                            <p>Add a card on file below.</p>
                        <% } %>
                        <form method="post" action="UpdateCardInfo" id="cardDetailsForm">
                            <div class="row gtr-uniform">
                                <div class="col-6">
                                    <label>Card Number</label>
                                    <input type="text" name="cardNumber" id="cardNumber" placeholder="Card Number" />
                                </div>
                                <div class="col-3">
                                    <label>Expiry Date</label>
                                    <input type="text" name="expiryDate" id="expiryDate" placeholder="MM/YY" />
                                </div>
                                <div class="col-3">
                                    <label>CVC</label>
                                    <input type="text" name="cvc" id="cvc" placeholder="CVC" />
                                </div>
                                <div class="col-12">
                                    <label>Password (Required for Verification)</label>
                                    <input type="password" name="password" id="password" placeholder="Your Password" />
                                </div>
                                <!-- Hidden input field for action -->
                                <div class="col-12">
						            <input type="hidden" name="email" value="<%= session.getAttribute("email") %>" />
						        </div>
                                <div class="col-12">
                                    <input type="hidden" name="action" value="<%= (session.getAttribute("hasCardInfo") != null && (Boolean) session.getAttribute("hasCardInfo")) ? "updateCard" : "addCard" %>" />
                                </div>
                                <!-- Break -->
                                <div class="col-12">
                                    <ul class="actions">
                                        <li><input type="submit" value="<%= (session.getAttribute("hasCardInfo") != null && (Boolean) session.getAttribute("hasCardInfo")) ? "Update Card" : "Add Card" %>" class="primary" /></li>
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
