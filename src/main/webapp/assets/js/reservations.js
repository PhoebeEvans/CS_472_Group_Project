function filterRooms() {
    var checkInDate = document.getElementById("checkInDate").value;
    var checkOutDate = document.getElementById("checkOutDate").value;
    var adults = document.getElementById("adults").value;
    var children = document.getElementById("children").value;

	fetch('room.json')
        .then(response => response.json())
        .then(data => {
            const hotelContainer = document.getElementById('dynamicRegistration');

            data.forEach(hotel => {
                const card = document.createElement('div');
                card.classList.add('card');

                const cardContent = `
                    <section class="spotlight">
							<div class="image"><img src="images/pic01.jpg" alt="" /></div><div class="content">
								<h2>${hotel.roomName}<br />
								${hotel.available}</h2>
								<p>Sleeps ${hotel.sleepCount} people. All ammenities are included when booked.</p>
								<input type="button" class="btn" value="Book">
							</div>
					</section>
                `;
                card.innerHTML = cardContent;

                hotelContainer.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Error fetching hotels:', error);
        });
        
    // Send the selected date to the backend (Java) to filter rooms
    // Example AJAX request or fetch API can be used here
    // Upon receiving filtered room data, update the HTML dynamically
}

function callReservationFilter() {
    // Make an AJAX request to the server-side endpoint
    fetch('/reservationFilter')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            // Handle the response from the server
            console.log(data); 
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}