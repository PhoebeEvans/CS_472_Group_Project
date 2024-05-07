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
                        <h3>Guest Checkout</h3>
                        <div id="cartDetails"></div> <!-- Cart details will be displayed here -->

                        <h4>Enter Customer Information</h4>
                        <form id="checkoutForm" action="ProcessTransaction" method="post">
                            <div class="row gtr-uniform">
                                <div class="col-12">
                                    <input type="email" name="customerEmail" id="customerEmail" placeholder="Customer Email" required />
                                </div>
                                <div class="col-12" id="cardInfo" style="display: none;">
								    <h6>Card information not on file. Please enter below:</h6>
								    <input type="text" name="cardNumber" id="cardNumber" placeholder="Card Number" />
								    <input type="text" name="expirationDate" id="expirationDate" placeholder="Expiration Date (MM/YYYY)" />
								    <input type="text" name="ccv" id="ccv" placeholder="CCV" />
								    <div>
								        <input type="radio" name="saveCardOption" id="saveCardYes" value="yes">
								        <label for="saveCardYes">Yes, save my card information.</label>
								        <input type="radio" name="saveCardOption" id="saveCardNo" value="no" checked>
								        <label for="saveCardNo">No, do not save.</label>
								    </div>
								</div>


                                <!-- cart details -->
                                <input type="hidden" name="totalAmount" id="totalAmount" value="" />
                                <input type="hidden" name="startDate" id="startDate" value="" />
                                <input type="hidden" name="endDate" id="endDate" value="" />
                                <input type="hidden" name="roomNumber" id="roomNumber" value="" />
                                <input type="hidden" name="specialRequest" id="specialRequest" value="" />
                                <div class="col-12">
                                    <ul class="actions">
                                        <li><input type="button" value="Checkout" class="primary" onclick="checkCardInfo()" /></li>
                                        <li><a href="index.jsp" class="button">Cancel</a></li>
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
        function loadCartDetails() {
            const cartDetails = document.getElementById('cartDetails');
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            console.log('Cart:', cart);  // debug
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
                    
                    html += '<p>Room Number: ' + roomNumber + ' - $' + price + ' per night</p>' +
                    '<p>Check-in: ' + checkInDate + ', Check-out: ' + checkOutDate + '</p>' +
                    '<p>Nights: ' + nights + '</p>' +
                    '<p>Total: ' + totalPrice + '</p>' +
                    '<hr>';
                });
                
                html += '<h4>Grand total: $' + grandTotal + '</h4>' + '<h6>Coupons will automatically apply if available</h6>' + '<hr>';;
                
              //add cart deets to session
                document.getElementById('totalAmount').value = grandTotal;
                document.getElementById('startDate').value = cart[0].checkInDate;
                document.getElementById('endDate').value = cart[0].checkOutDate;
                document.getElementById('roomNumber').value = cart[0].roomNumber;
                document.getElementById('specialRequest').value = "Any specific requests here";
            } else {
                html += '<p>Your cart is empty.</p>';
            }
            cartDetails.innerHTML = html;
        }
        
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
                    submitForm();
                } else {
                    displayCardInputs();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error checking card information.');
            });
        }

        function displayCardInputs() {
            var cardInfoDiv = document.getElementById('cardInfo');
            cardInfoDiv.style.display = 'block';
            document.querySelector('#checkoutForm input[type="button"]').onclick = function() {
                if (validateCardInputs()) {
                    submitForm();
                } else {
                    alert('Please fill out all card information.');
                }
            };
        }

        function submitForm() {
            var saveCardOption = document.querySelector('input[name="saveCardOption"]:checked').value;
            var form = document.getElementById('checkoutForm');
            if (saveCardOption === 'yes') {
                
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'saveCard';
                input.value = 'true';
                form.appendChild(input);
            }
            form.submit();
        }

        function validateCardInputs() {
            var cardNumber = document.getElementById('cardNumber').value;
            var expirationDate = document.getElementById('expirationDate').value;
            var ccv = document.getElementById('ccv').value;
            return cardNumber && expirationDate && ccv;
        }


        
        document.addEventListener('DOMContentLoaded', function() {
            loadCartDetails();
        });
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
