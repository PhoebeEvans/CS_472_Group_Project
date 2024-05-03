<!DOCTYPE HTML>
<!--
	Spectral by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>

<head>
	<title>Employee Access - Caribou Inn</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="assets/css/main.css" />
	<noscript>
		<link rel="stylesheet" href="assets/css/noscript.css" />
	</noscript>
</head>

<body class="is-preload">

	<!-- Page Wrapper -->
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
			<h1><a href="index.html">CARIBOU INN - Administrator Portal</a></h1>
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
			<header>
				<h2>Administrator Portal</h2>
				<p>Run reports, check room availability, and access and edit user profile</p>
			</header>
			<section class="wrapper style5">
				<div class="inner">

					<h3>Run a report</h3>
					<p>Enter in a date range to run a financial report. Once submitted 
					it should access a database of transactions.</p>
					<div class="form">
                        <form class="grid">
                            <input type="date" id="beginDate" placeholder="Start Date">
                            <input type="date" id="endDate" placeholder="End Date">
                        </form>
                    </div>
					
					<ul class="actions">
						<li><input type="submit" value="Submit" class="primary" /></li> <!-- Change to run financial report -->
					</ul>

					<hr />

					<h3>Check Room Availability</h3>
					<p>Check how many rooms are available on the selected day.</p>
					<div class="form">
                        <form class="grid">
                        	<div class="row gtr-uniform">
                        		<div class="col-6">
						            <input type="date" id="searchDate" placeholder="Check In Date">
						        </div>
                            	<div class="col-12">
						            <input type="button" onclick="filterRooms()" class="primary" value="Check Availability"> <!-- Change to check availability -->
						        </div>
						     </div>
                        </form>
                    </div>
					<hr />
						
					<h3>Access Accounts</h3>
					<p>Enter in user's full name and email to access account.</p>
					<form method="post" action="AccountServlet" id="createAccountForm">
						    <div class="row gtr-uniform">
						        <div class="col-12">
						            <input type="email" name="otherUserEmail" id="otherUserEmail" value="" placeholder="Email" />
						        </div>
							        <!-- Hidden input field for action -->
							        <div class="col-12">
								        <input type="hidden" name="action" value="accessOtherAccount" />
							        </div>
						        <!-- Break -->
						        <div class="col-12">
						            <ul class="actions">
						                <li><input type="submit" value="Search" class="primary" /></li>
						            </ul>
						        </div>
						    </div>
						</form>
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