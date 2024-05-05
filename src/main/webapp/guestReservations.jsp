<!DOCTYPE HTML>
<html>
<head>
    <title>Your Reservations - Caribou Inn</title>
    <meta charset="utf-8" />
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.Map" %>
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
            <h1><a href="index.html">Caribou Inn - Your Reservations</a></h1>
        </header>

        <!-- Main Content -->
        <article id="main">
            <section class="wrapper style5">
                <div class="inner">
                    <h2>Your Reservations</h2>
                    <hr>
                    <div class="row gtr-uniform">
                        <!-- Future Reservations -->
                        <div class="col-6">
                            <h3>Future Reservations</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Reservation ID</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Room Number</th>
                                        <th>Cancel</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (request.getAttribute("futureReservations") != null) {
                                        List<Map<String, String>> futureList = (List<Map<String, String>>) request.getAttribute("futureReservations");
                                        for (Map<String, String> reservation : futureList) { %>
                                            <tr>
                                                <td><%= reservation.get("reservationID") %></td>
                                                <td><%= reservation.get("startDate") %></td>
                                                <td><%= reservation.get("endDate") %></td>
                                                <td><%= reservation.get("roomNumber") %></td>
                                                <td><button onclick="cancelReservation(<%= reservation.get("reservationID") %>);">Cancel</button></td>
                                            </tr>
                                        <% }
                                    } %>
                                </tbody>
                            </table>
                        </div>

                        <!-- Past Reservations -->
                        <div class="col-6">
                            <h3>Past Reservations</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Reservation ID</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Room Number</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (request.getAttribute("pastReservations") != null) {
                                        List<Map<String, String>> pastList = (List<Map<String, String>>) request.getAttribute("pastReservations");
                                        for (Map<String, String> reservation : pastList) { %>
                                            <tr>
                                                <td><%= reservation.get("reservationID") %></td>
                                                <td><%= reservation.get("startDate") %></td>
                                                <td><%= reservation.get("endDate") %></td>
                                                <td><%= reservation.get("roomNumber") %></td>
                                            </tr>
                                        <% }
                                    } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </article>

        <!-- Footer -->
        <footer id="footer">...</footer>
    </div>

    <!-- Scripts -->
    <script src="assets/js/jquery.scrollex.min.js"></script>
    <script src="assets/js/jquery.scrolly.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>

    <script>
        function cancelReservation(reservationId) {
            // call servlet here for cancellation handling
            alert('Cancel reservation: ' + reservationId);
            
            window.location.href = 'CancelReservation?reservationId=' + reservationId;
        }
    </script>
</body>
</html>
