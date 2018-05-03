<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>

<head>
	<link rel="stylesheet" href="<c:url value="/css/style.css" />">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>

 <jsp:include page="navigation-bar.jsp"></jsp:include>
 
<body>

	<h3>Create task</h3><br>
	<s:form method="POST" action="create" enctype="multipart/form-data">
    <table>
    	 <tr>
            <td>Project</td>
            <td>
            <select name="projectId"> 
					<c:forEach items= "${ projects }" var = "p">
						<option value = "${ p.id }">${ p.name }</option>
					</c:forEach>
			 </select>
			 </td>
        </tr>
        <tr>
            <td>Summary</td>
            <td><input type="text" name="summary" /></td>
        </tr>
        
         <tr>
            <td>Description</td>
            <td><input type="text" name="description" /></td>
        </tr>
        
         <tr>
            <td>Due Date</td>
            <td><input type="date" name="dueDate" /></td>
        </tr>
        
         <tr>
            <td>Priority</td>
            <td><select name="priorityId"> 
					<c:forEach items = "${ priorities }" var = "p">
						<option value = "${ p.id }">${ p.type }</option>
					</c:forEach>
			     </select>
			</td>
        </tr>
        
         <tr>
            <td>Issue Type</td>
            <td>
	            <select name="issueTypeId"> 
					<c:forEach items= "${ issueTypes }" var = "it">
						<option value = "${ it.id }">${ it.type.getValue() }</option>
					</c:forEach>
				 </select>
			 </td>
         </tr>
         
          <tr>
            <td>Assignee</td>
            <td>
	            <select name="assigneeId"> 
					<c:forEach items= "${ assignees }" var = "a">
						<option value = "${ a.id }">${ a.name }</option>
					</c:forEach>
			     </select>
			 </td>
        		</tr>
				       <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
				        <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
				        <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
                 <tr>
            <td><input type="submit" value="Create" /></td>
        </tr>
    </table>
	</s:form>
	
	<div class="mod-content">










    <div class="tabwrap tabs2">

        <ul id="issue-tabs" class="tabs horizontal">
                                
            <li data-id="all-tabpanel" data-key="com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel" data-label="All" data-href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel">
                            <a class="ajax-activity-content" id="all-tabpanel" href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel"><strong>All</strong></a>
                        </li>
                                
            <li class="active" id="comment-tabpanel" data-id="comment-tabpanel" data-key="com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel" data-label="Comments" data-href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel">
                            <strong tabindex="0">Comments</strong>
                        </li>
                                
            <li data-id="worklog-tabpanel" data-key="com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel" data-label="Work Log" data-href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel">
                            <a class="ajax-activity-content" id="worklog-tabpanel" href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel"><strong>Work Log</strong></a>
                        </li>
                                
            <li data-id="changehistory-tabpanel" data-key="com.atlassian.jira.plugin.system.issuetabpanels:changehistory-tabpanel" data-label="History" data-href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:changehistory-tabpanel">
                            <a class="ajax-activity-content" id="changehistory-tabpanel" href="/browse/TRANS-2557?page=com.atlassian.jira.plugin.system.issuetabpanels:changehistory-tabpanel"><strong>History</strong></a>
                        </li>
                                
            <li data-id="activity-stream-issue-tab" data-key="com.atlassian.streams.streams-jira-plugin:activity-stream-issue-tab" data-label="Activity" data-href="/browse/TRANS-2557?page=com.atlassian.streams.streams-jira-plugin:activity-stream-issue-tab">
                            <a class="ajax-activity-content" id="activity-stream-issue-tab" href="/browse/TRANS-2557?page=com.atlassian.streams.streams-jira-plugin:activity-stream-issue-tab"><strong>Activity</strong></a>
                        </li>
                </ul>

                    <div class="sortwrap">
                                    <a class="issue-activity-sort-link ajax-activity-content" rel="nofollow" data-tab-sort="" data-order="desc" href="/browse/TRANS-2557?actionOrder=desc" title="Ascending order - Click to sort in descending order">
                        <span class="aui-icon aui-icon-small aui-iconfont-up">Ascending order - Click to sort in descending order</span>
                    </a>
                            </div>
            </div>
    <div class="issuePanelWrapper">
        <div class="issuePanelProgress"></div>
        <div class="issuePanelContainer" id="issue_actions_container">
                                    


<div id="comment-1791163" class="issue-data-block activity-comment twixi-block  expanded">
    <div class="twixi-wrap verbose actionContainer">
        <div class="action-head">
            <a href="#" title="Collapse comment" class="twixi"><span class="icon-default aui-icon aui-icon-small aui-iconfont-expanded"><span>Hide</span></span></a>
            <div class="action-links">
                <a href="/browse/TRANS-2557?focusedCommentId=1791163&amp;page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-1791163" title="Right click and copy link for a permanent link to this comment." class="activitymodule-link issue-comment-action">
                    <span class="icon-default aui-icon aui-icon-small aui-iconfont-link">Permalink</span></a>
                                            </div>
            <div class="action-details">        
    
    
    
                

    <a class="user-hover user-avatar" rel="thomask2" id="commentauthor_1791163_verbose" href="/secure/ViewProfile.jspa?name=thomask2"><span class="aui-avatar aui-avatar-xsmall"><span class="aui-avatar-inner"><img src="https://avatar-cdn.atlassian.com/2a3e2a714f88f3305ff8574728d34bdb?d=mm&amp;s=16" alt="thomask2"></span></span> Thomas L. Kjeldsen</a>
 added a comment  - <span class="commentdate_1791163_verbose subText"><span class="date user-tz" title="27/Apr/2018 11:22 PM"><time class="livestamp" datetime="2018-04-27T23:22:10+0000">27/Apr/2018 11:22 PM</time></span></span>  </div>
        </div>
        <div class="action-body flooded"><p>Could also use "Fix Version/er"</p> </div>
    </div>
    <div class="twixi-wrap concise actionContainer">
        <div class="action-head">
            <a href="#" title="Expand comment" class="twixi"><span class="icon-default aui-icon aui-icon-small aui-iconfont-collapsed"><span>Show</span></span></a>
            <div class="action-details flooded">
                        
    
    
    
                

    <a class="user-hover user-avatar" rel="thomask2" id="commentauthor_1791163_concise" href="/secure/ViewProfile.jspa?name=thomask2"><span class="aui-avatar aui-avatar-xsmall"><span class="aui-avatar-inner"><img src="https://avatar-cdn.atlassian.com/2a3e2a714f88f3305ff8574728d34bdb?d=mm&amp;s=16" alt="thomask2"></span></span> Thomas L. Kjeldsen</a>
 added a comment  - <span class="commentdate_1791163_concise subText"><span class="date user-tz" title="27/Apr/2018 11:22 PM"><time class="livestamp" datetime="2018-04-27T23:22:10+0000">27/Apr/2018 11:22 PM</time></span></span>                    Could also use "Fix Version/er"              </div>
        </div>
    </div>
</div>
                             


<div id="comment-1791882" class="issue-data-block activity-comment twixi-block  expanded">
    <div class="twixi-wrap verbose actionContainer">
        <div class="action-head">
            <a href="#" title="Collapse comment" class="twixi"><span class="icon-default aui-icon aui-icon-small aui-iconfont-expanded"><span>Hide</span></span></a>
            <div class="action-links">
                <a href="/browse/TRANS-2557?focusedCommentId=1791882&amp;page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-1791882" title="Right click and copy link for a permanent link to this comment." class="activitymodule-link issue-comment-action">
                    <span class="icon-default aui-icon aui-icon-small aui-iconfont-link">Permalink</span></a>
                                            </div>
            <div class="action-details">        
    
    
    
                

    <a class="user-hover user-avatar" rel="morten.bjerregaard132130385" id="commentauthor_1791882_verbose" href="/secure/ViewProfile.jspa?name=morten.bjerregaard132130385"><span class="aui-avatar aui-avatar-xsmall"><span class="aui-avatar-inner"><img src="https://avatar-cdn.atlassian.com/528f1347bc433800def7087095924cc7?d=mm&amp;s=16" alt="morten.bjerregaard132130385"></span></span> Morten Bjerregaard</a>
 added a comment  - <span class="commentdate_1791882_verbose subText"><span class="date user-tz" title="30/Apr/2018 7:31 AM"><time class="livestamp" datetime="2018-04-30T07:31:53+0000">30/Apr/2018 7:31 AM</time></span></span>  </div>
        </div>
        <div class="action-body flooded"><p>I agree with Thomas!</p> </div>
    </div>
    <div class="twixi-wrap concise actionContainer">
        <div class="action-head">
            <a href="#" title="Expand comment" class="twixi"><span class="icon-default aui-icon aui-icon-small aui-iconfont-collapsed"><span>Show</span></span></a>
            <div class="action-details flooded">
                        
    
    
    
                

    <a class="user-hover user-avatar" rel="morten.bjerregaard132130385" id="commentauthor_1791882_concise" href="/secure/ViewProfile.jspa?name=morten.bjerregaard132130385"><span class="aui-avatar aui-avatar-xsmall"><span class="aui-avatar-inner"><img src="https://avatar-cdn.atlassian.com/528f1347bc433800def7087095924cc7?d=mm&amp;s=16" alt="morten.bjerregaard132130385"></span></span> Morten Bjerregaard</a>
 added a comment  - <span class="commentdate_1791882_concise subText"><span class="date user-tz" title="30/Apr/2018 7:31 AM"><time class="livestamp" datetime="2018-04-30T07:31:53+0000">30/Apr/2018 7:31 AM</time></span></span>                    I agree with Thomas!              </div>
        </div>
    </div>
</div>
                             </div>
    </div>
</div>
</body>
</html>