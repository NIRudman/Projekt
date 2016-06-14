/**
 * 
 */
$(document).ready(function() 
{
	var input = [];
	
	// ON THE SUBMIT BUTTON IN REGISTRATION
	$("#reg-form").on("submit", function( event)
	{
		event.preventDefault();
		
		// CREATE AND GET VARIABLES
		input = [];
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
			addUsers();
		}
	});
	
	// VARIABLES
	var amountOfUsers = 0;
	
	function addUsers()
	{
		// REMOVE OLD DATA
		amountOfUsers == 0;
		$('#user-table > tr').remove();
		
		// GET USERS AS JSON FROM API
		$.getJSON("http://localhost:8080/Projekt/rest/users/"
		, function(user) {
					
			$.each(user, function(j, object) {
				
				// SAVE AMOUNT OF USERS
				amountOfUsers++;
				
				// ADD USER IN USER TABLE
				$("#user-table").append(
						'<tr id="row-' + amountOfUsers + '">' + 
						'<td id="id-' + amountOfUsers + '">'
						+ object[0] + '</td>' + // ID
						'<td><input type="text" id="table-fn-' 
						+ amountOfUsers + '" value="'
						+ object[2] + '"/></td>' + // FIRSTNAME
						'<td><input type="text" id="table-ln-' 
						+ amountOfUsers + '" value="'
						+ object[3] + '"/></td>' + // LASTNAME
						'<td><input type="text" id="table-e-' 
						+ amountOfUsers + '" value="'
						+ object[1] + '"/></td><td>' // EMAIL
						
						// CHANGE BUTTON. ADD USER-ID TO ID OF BUTTON
						+ '<button id="update-' + amountOfUsers + '" class="update"' +
						'type="button"><strong>Update</strong></button> &nbsp' +
						
						// DELETE BUTTON. ADD USER-ID TO ID OF BUTTON
						'<button id="delete-' + amountOfUsers + '" class="delete"' +
						'type="button"><strong>Delete</strong></button>' + 
						'</td></tr>'
				);
			});
			buttonEvents()
		});
	}
	
	addUsers();
	
	$("#search-form").on("submit", function( event)
	{
		event.preventDefault();
		
		var sWord = document.getElementById("search-field").value;
		if(sWord != "" && sWord !== 'undefined')
		{
			// GET USERS AS JSON FROM API
			$.getJSON("http://localhost:8080/Projekt/rest/users/search/" + sWord 
			, function(user) {
				
				// REMOVE OLD DATA
				amountOfUsers == 0;
				$('#user-table > tr').remove();
				
				$.each(user, function(j, object) 
				{
					// SAVE AMOUNT OF USERS
					amountOfUsers++;
					
					// ADD USER IN USER TABLE
					$("#user-table").append(
							'<tr id="row-' + amountOfUsers + '">' + 
							'<td id="id-' + amountOfUsers + '">'
							+ object[0] + '</td>' + // ID
							'<td><input type="text" id="table-fn-' 
							+ amountOfUsers + '" value="'
							+ object[2] + '"/></td>' + // FIRSTNAME
							'<td><input type="text" id="table-ln-' 
							+ amountOfUsers + '" value="'
							+ object[3] + '"/></td>' + // LASTNAME
							'<td><input type="text" id="table-e-' 
							+ amountOfUsers + '" value="'
							+ object[1] + '"/></td><td>' // EMAIL
							
							// CHANGE BUTTON. ADD USER-ID TO ID OF BUTTON
							+ '<button id="update-' + amountOfUsers + '" class="update"' +
							'type="button"><strong>Update</strong></button> &nbsp' +
							
							// DELETE BUTTON. ADD USER-ID TO ID OF BUTTON
							'<button id="delete-' + amountOfUsers + '" class="delete"' +
							'type="button"><strong>Delete</strong></button>' + 
							'</td></tr>'
					);
				});
				buttonEvents()
			});
		}
	});
	
	$("#get-users").click(function()
	{
		addUsers();
	});
	
	function buttonEvents()
	{
		// ADD CLICK METHOD HERE, BUTTON DOESNT EXIST BEFORE
		$("button").click(function()
		{
			// CHECK WHICH BUTTON
			if($(this).is(".update"))
			{
				// LOOP THROUGH ALTERNATIVES
				for(var index = 0; index <= amountOfUsers; index++)
				{
					if($(this).is("#update-" + index))
					{
						updateUser(index);
					}
				}
			}
			else if($(this).is(".delete"))
			{
				for(var i = 0; i <= amountOfUsers; i++)
				{
					if($(this).is("#delete-" + i))
					{
						var user = $("#id-" + i).text();
						// DELETE USER FROM ID
						$.ajax({
						    type: "DELETE",
						    url: "http://localhost:8080/Projekt/rest/users/" + user,			    
						});
						addUsers();
					}
				}
			}
		});
	}
	
	// METHOD FOR UPDATE USER AJAX PUT
	function updateUser(index)
	{
		// CREATE VARIABLE
		input = [];
		input.push($("#id-" + index).text());
		var newFirstName = document.getElementById("table-fn-" + index).value;
		var newLastName = document.getElementById("table-ln-" + index).value;
		var newEmail = document.getElementById("table-e-" + index).value;
		
		input.push(newEmail);
		input.push(newFirstName);
		input.push(newLastName);

		
		// CHECK IF EMPTY
		if(input[1] != "" && input[2] != "" && input[3] != "")
		{
			// CHECK IF DEFINED
			if(input !== 'undefined' && input.length > 0)
			{
				// PUT ARRAY TO API AS JSON
				$.ajax({
				    type: "PUT",
				    url: "http://localhost:8080/Projekt/rest/users/update",
				    data: JSON.stringify(input),
				    contentType: "application/json; charset=utf-8",
				    dataType: "json",
				});
			}
			addUsers();
		}
	}
});