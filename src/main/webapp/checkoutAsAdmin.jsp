<!DOCTYPE HTML>
<html>
<head>
    <title>Checkout - The Caribou Inn</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
</head>
<body>
    <div id="page-wrapper">
        <header id="header" class="alt">
            <h1><a href="index.html">Caribou Inn</a></h1>
            <nav id="nav">
                <ul>
                    <li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a></li>
                </ul>
            </nav>
        </header>

        <article id="main">
            <section class="wrapper style5">
                <div class="inner">
                    <section>
                        <h3>Checkout as Administrator</h3>
                        <div id="cartDetails"></div> <!-- Cart details will be displayed here -->

                        <h4>Enter Customer Information</h4>
                        <form id="checkoutForm">
                            <div class="row gtr-uniform">
                                <div class="col-12">
                                    <input type="email" name="customerEmail" id="customerEmail" placeholder="Customer Email" required />
                                </div>
                                <div class="col-12" id="cardInfo" style="display: none;">
                                    <input type="text" name="cardNumber" id="cardNumber" placeholder="Card Number" />
                                    <input type="text" name="expirationDate" id="expirationDate" placeholder="Expiration Date (MM/YYYY)" />
                                    <input type="text" name="ccv" id="ccv" placeholder="CCV" />
                                </div>
                                <div class="col-12">
                                    <ul class="actions">
                                        <li><input type="button" value="Checkout" class="primary" onclick="checkCardInfo()" /></li>
                                        <li><a href="employeePage.jsp" class="button">Cancel</a></li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                    </section>
                </div>
            </section>
        </article>

        <footer id="footer">...</footer>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', loadCartDetails);

        function loadCartDetails() {
	        const cartDetails = document.getElementById('cartDetails');
	        const cart = JSON.parse(localStorage.getItem('cart')) || [];
	        console.log('Cart:', cart);  // debug
	        cart.forEach(item => {
	            console.log('room no:', item.roomNumber);
	        });
	        let html = '<h4>Your Cart</h4>';
	        let grandTotal = 0;
	        if (cart.length > 0) {
	            cart.forEach((item) => {
	            	const roomNumber = item.roomNumber;
	                const price = item.price;
	                const checkInDate = item.checkInDate;
	                const checkOutDate = item.checkOutDate;
	                const nights = item.nights;
	                const totalPrice = item.totalPrice;
	                grandTotal += totalPrice;
	                
	                console.log('roomNumber Var: ', (item.roomNumber).toString());

	                html += '<p>Room Number: ' + roomNumber + ' - $' + price + ' per night</p>' +
	                '<p>Check-in: ' + checkInDate + ', Check-out: ' + checkOutDate + '</p>' +
	                '<p>Nights: ' + nights + '</p>'
	                '<p>Total: ' + totalPrice + '</p>'
	                '<hr>';
	            });
	            
	            html += '<hr>' +
	            		'<h4>Grand total: $' + grandTotal + '</h4>' + '<hr>';
	        } else {
	            html += '<p>Your cart is empty.</p>';
	        }
	        console.log('HTML:', html); // debug
	        cartDetails.innerHTML = html;
	    }
	    document.addEventListener('DOMContentLoaded', loadCartDetails);

        function checkCardInfo() {
            var email = document.getElementById('customerEmail').value;
            if (!email) {
                alert("Please enter an email address.");
                return;
            }
            fetch('CheckForCardInfo', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'email=' + encodeURIComponent(email)
            })
            .then(response => response.json())
            .then(data => {
                if (data.cardExists) {
                    document.getElementById('checkoutForm').submit(); // Submit form if card info exists
                } else {
                    document.getElementById('cardInfo').style.display = 'block'; // Show card input fields if no info
                }
            })
            .catch(error => console.error('Error:', error));
        }
    </script>

    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/jquery.scrollex.min.js"></script>
    <script src="assets/js/jquery.scrolly.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
