/**
 * 
 */
$(document).ready(function() 
{
	// ON THE SUBMIT BUTTON IN REGISTRATION
	$("#reg-form").on("submit", function( event)
	{
		event.preventDefault();
		
		// CREATE AND GET VARIABLES
		var input = [];
		var newFirstName = document.getElementById("first-name").value;
		var newLastName = document.getElementById("last-name").value;
		var newEmail = document.getElementById("email").value;
		
		// CHECK IF EMPTY
		if(newFirstName != "" && newLastName != "" && newEmail != "")
		{
			input.push(newEmail);
			input.push(newFirstName);
			input.push(newLastName);
		}
		
		// CHECK IF ARRAY IS MADE
		if(input !== 'undefined' && input.length > 0)
		{
			// POST ARRAY TO API AS JSON
			$.ajax({
			    type: "POST",
			    url: "http://localhost:8080/Projekt/rest/users/insert",
			    data: JSON.stringify(input),
			    contentType: "application/json; charset=utf-8",
			    dataType: "json",
			    success: function(data){alert(data);},
			    failure: function(errMsg) {
			        alert(errMsg);
			    }
			});
			location.reload();
		}
	});
	
	// VARIABLES
	var amountOfUsers = 0;
	
	// GET USERS AS JSON FROM API
	$.getJSON("http://localhost:8080/Projekt/rest/users/"
	, function(user) {
				
		$.each(user, function(j, object) {
			
			// SAVE AMOUNT OF USERS
			amountOfUsers++;
			
			console.log("hej" + amountOfUsers);
			
			// ADD USER IN USER TABLE
			$("#user-table").append(
					'<tr id="row-' + amountOfUsers + '"><td>' 
					+ object[0] + '</td><td>' // ID
					+ object[2] + '</td><td>' // FIRSTNAME
					+ object[3] + '</td><td>' // LASTNAME
					+ object[1] + '</td><td>' // EMAIL
					
					// CHANGE BUTTON. ADD USER-ID TO ID OF BUTTON
					+ '<button id="change-' + amountOfUsers + '" class="change"' +
					'type="button"><strong>Change</strong></button> &nbsp' +
					
					// DELETE BUTTON. ADD USER-ID TO ID OF BUTTON
					'<button id="delete-' + amountOfUsers + '" class="delete"' +
					'type="button"><strong>Delete</strong></button>' + 
					'</td></tr>'
			);
		});
		
		// ADD CLICK METHOD HERE, BUTTON DOESNT EXIST BEFORE
		$("button").click(function()
		{
			console.log("hej");				
		});
	});
	
});