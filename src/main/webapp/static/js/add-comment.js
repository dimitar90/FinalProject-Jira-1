function addComment() {
		var description = document.commentForm.description.value;
		var taskId = document.commentForm.taskId.value;
		var request = new XMLHttpRequest();
		document.commentForm.description.value = '';
		
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var json = request.responseText;
				var newComment = JSON.parse(json);
				
			   /* if (document.querySelector("#comments tbody tr").innerHTML.indexOf("even") != -1) {
					nextColor = odd;
				} else {
					nextColor = even;
				}
				*/
				/*var html = "<tr role='row' class=" + nextColor + " >" + */
				
				var html = "<tr>" +
								"<td>"  + newComment.user.name + "</td>" +
								"<td>"  + newComment.dateTime.date.year + "/" + newComment.dateTime.date.month + "/" + newComment.dateTime.date.day + " " +
										+ newComment.dateTime.time.hour + ":" + newComment.dateTime.time.minute + ":" + newComment.dateTime.time.second +"</td>" +
								"<td>" + newComment["description"] + "</td>"  +
							"</tr>";
				document.querySelector("#comments tbody").innerHTML = html + document.querySelector("#comments tbody").innerHTML;
			}
		}
		
		request.open("GET", "http://localhost:8080/Jira/comments/add?" + "taskId=" + taskId + "&description=" + description, true);
		request.send();
};

