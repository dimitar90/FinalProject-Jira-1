function addComment() {
		var description = document.commentForm.description.value;
		var taskId = document.commentForm.taskId.value;
		var request = new XMLHttpRequest();
		document.commentForm.description.value = '';
		
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var json = request.responseText;
				var newComment = JSON.parse(json);
				
				console.log(newComment);
	
				var newCommentHtml = "<div class=\"row\">"+
										"<div class=\"col-md-2\">"+
										"<div>" +
											"<div>" +
												"<div>" +
													"<span><img src=\"/Jira/userAvatar/" + newComment.userAvatarName+"\" height=\"100\" width=\"100\"></span>" +
												"</div>" +
											"</div>" +
											"<div>" +
												"<p>" + newComment.username + "</p>" +
											"</div>" +
										"</div>" +
									"</div>" +
									"<div class=\"col-md-10\">" +
										"<div>" +
											"<div>" +
												"<p>" +
													"<b>Written on: </b>" + newComment.dateTime.date.year + "/" + newComment.dateTime.date.month + "/" + newComment.dateTime.date.day + " " + newComment.dateTime.time.hour + ":" + newComment.dateTime.time.minute + ":" + newComment.dateTime.time.second + "</p>" + newComment.description +"</div>" +
										"</div>" +
									"</div>" +
								"</div>";
				
				document.querySelector("#comments").innerHTML = newCommentHtml + document.querySelector("#comments").innerHTML;
			}
		}
		
		request.open("GET", "http://localhost:8080/Jira/comments/add?" + "taskId=" + taskId + "&description=" + description, true);
		request.send();
};

