<!DOCTYPE HTML>
<html>
<head>
    <title>Available Rooms - Caribou Inn</title>
    <meta charset="utf-8" />
    <%@ page import="java.util.List" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
    <script src="assets/js/jquery.min.js"></script>
</head>
<body class="is-preload">
    <!-- Page Wrapper -->
    <div id="page-wrapper">
        <!-- Header -->
        <header id="header">
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

        <!-- Main Content -->
        <article id="main">
            <section class="wrapper style5">
                <div class="inner">
                    <h2>Available Rooms</h2>
                    <h4><%= request.getParameter("checkInDate") + " to " + request.getParameter("checkOutDate") %></h4>
                  

                    <table>
                        <thead>
                            <tr>
                                <th>Room Number</th>
                                <th>Bed Number</th>
                                <th>Bed Size</th>
                                <th>Balcony</th>
                                <th>Non-Smoking</th>
                                <th>Price ($)</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (request.getAttribute("availableRooms") != null) {
                                List<List<String>> rooms = (List<List<String>>) request.getAttribute("availableRooms");
                                for (List<String> room : rooms) { %>
                                    <tr>
                                        <td><%= room.get(0) %></td>
                                        <td><%= room.get(1) %></td>
                                        <td><%= room.get(2) %></td>
                                        <td><%= room.get(3) %></td>
                                        <td><%= room.get(4) %></td>
                                        <td><%= room.get(5) %></td>
                                        <td>
										    <button class="cart-button button"
										            data-roomnumber="<%= room.get(0) %>"
										            data-price="<%= room.get(5) %>"
										            data-checkindate="<%= (String) request.getAttribute("checkInDate") %>"
										            data-checkoutdate="<%= (String) request.getAttribute("checkOutDate") %>">
										        Add to Cart
										    </button>
										</td>

                                    </tr>
                                <% } 
                            } %>
                        </tbody>
                    </table>
                    
                    <section>
					    <button onclick="window.location.href='checkoutAsAdmin.jsp';" class="primary button">
					        Review Cart and Checkout
					    </button>
					</section>
                </div>
                
            </section>
            
        </article>

        <!-- Footer -->
        <footer id="footer">...</footer>
    </div>

    <script>
		document.addEventListener("DOMContentLoaded", function() {
		    updateButtons();
		});
		
		function addToCart(roomNumber, price, checkInDate, checkOutDate) {
		    var cart = JSON.parse(localStorage.getItem('cart')) || [];
		    
		    var checkIn = new Date(checkInDate);
		    var checkOut = new Date(checkOutDate);
		    var nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24)); //way to get number of nights
		    var totalPrice = nights * price;
		    
		    var cartItem = { 
		            roomNumber: roomNumber, 
		            price: price, 
		            checkInDate: checkInDate, 
		            checkOutDate: checkOutDate,
		            nights: nights,
		            totalPrice: totalPrice
		        };
		        
		        // check for sameitem
		        if (!cart.some(item => item.roomNumber === roomNumber)) {
		            cart.push(cartItem);
		            localStorage.setItem('cart', JSON.stringify(cart));
		        }
		        updateButtons();
		}
		
		function removeFromCart(roomNumber) {
		    var cart = JSON.parse(localStorage.getItem('cart')) || [];
		    cart = cart.filter(item => item.roomNumber !== roomNumber);
		    localStorage.setItem('cart', JSON.stringify(cart));
		    updateButtons();
		}
		
		function updateButtons() {
		    var cart = JSON.parse(localStorage.getItem('cart')) || [];
		    document.querySelectorAll('.cart-button').forEach(button => {
		        var roomNumber = button.dataset.roomnumber;
		        if (cart.some(item => item.roomNumber === roomNumber)) {
		            button.textContent = 'Remove from Cart';
		            button.onclick = function() { removeFromCart(roomNumber); };
		        } else {
		            button.textContent = 'Add to Cart';
		            button.onclick = function() {
		                addToCart(roomNumber, button.dataset.price, button.dataset.checkindate, button.dataset.checkoutdate);
		            };
		        }
		    });
		}
		</script>


    <!-- Additional Scripts -->
    <script src="assets/js/jquery.scrollex.min.js"></script>
    <script src="assets/js/jquery.scrolly.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
