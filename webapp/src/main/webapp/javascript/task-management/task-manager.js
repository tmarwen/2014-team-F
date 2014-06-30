/**
 * Created by The eXo Platform MEA
 * Author : Marwen Trabelsi
 *         mtrabelsi@exoplatform.com
 * June 28, 2014
 */
(function ($) {

  var portal = window.eXo.env.portal;

  var TaskManagerUtil = {
    rest: (portal.rest) ? portal.rest : 'rest',
    portalName: (portal.portalName) ? portal.portalName : 'classic',
    context: (portal.context) ? portal.context : '/portal',
    accessMode: (portal.accessMode) ? portal.accessMode : 'public',
    userName: (portal.userName) ? portal.userName : '',
    restTaskManagerURI: 'task-rest-manager'
  };

  var restTaskManagerURL = "/" + TaskManagerUtil.rest + "/" + TaskManagerUtil.restTaskManagerURI,
    userSpaceRESTUrl = "/" + TaskManagerUtil.rest + TaskManagerUtil.context + "/social/spaces/mySpaces/show.json",
    todoTasksRESTUrl = restTaskManagerURL + "/list/byStatus/todo/" + TaskManagerUtil.userName,
    inProgressTasksRESTUrl = restTaskManagerURL + "/list/byStatus/inprogress/" + TaskManagerUtil.userName,
    doneTasksRESTUrl = restTaskManagerURL + "/list/byStatus/done/" + TaskManagerUtil.userName,
    personalTasksRESTUrl = restTaskManagerURL + "/list/byType/personal/" + TaskManagerUtil.userName,
    allUserTasksRESTUrl = restTaskManagerURL + "/list/all/" + TaskManagerUtil.userName,
    allProjectTasksRESTUrl = restTaskManagerURL + "/list/byType/project",
    projectTasksRESTUrl = restTaskManagerURL + "/list/byProject/{project}",
    updateTaskRESTUrl = restTaskManagerURL + "/update/task/{id}/status?status={status}",
    taskItemHeaderResolver = "taskHeader-",
    taskItemContentResolver = "taskContent-";
  var userSpaces = [];

  $("#taskOperationTabs").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
  $("#taskOperationTabs li").removeClass("ui-corner-top").addClass("ui-corner-left");

  $("#taskListTab").tabs();

  // initialize input task dates
  $('#startEndTaskDateCouple .time').timepicker({
    'minTime': '9:00',
    'maxTime': '18:00',
    'showDuration': true,
    'timeFormat': 'H:i'
  });

  $('#startEndTaskDateCouple .date').datepicker({
    'format': 'm-d-yyyy',
    'autoclose': true
  });

  //Setup task type fields
  $("#taskTypeWrapper").on('change', 'input:radio', function () {
    var value = this.value;
    if (value == "Personal") {
      $("#taskProject").prop("disabled", true);
      $("#taskAssignee").prop("disabled", true);
    }
    else {
      $("#taskProject").prop("disabled", false);
      $("#taskAssignee").prop("disabled", false);
    }
  });

  $.getJSON(userSpaceRESTUrl, function (data) {
    var mySpaces = data.spaces;
    var $taskProjectSelectList = $('select#taskProject');
    var $perProjectProjectList = $('#perProjectTaskTab select#perProjectTaskProject');
    clearTaskProjectList();
    $.each(mySpaces, function (key, val) {
      var spaceName = val.name;
      userSpaces.push(val);
      $("<option></option>", {value: spaceName, text: val.displayName})
        .data({
          spaceName: spaceName,
          spaceUrl: val.spaceUrl,
          groupId: val.groupId,
          url: val.url
        })
        .appendTo($taskProjectSelectList)
        .appendTo($perProjectProjectList);
    });
  });

  pullTodoTasks();
  pullInProgressTasks();
  pullDoneTasks();
  pullAllUserTasks();

  function pullTodoTasks() {
    var $todoTasksContainer = $('#todoTaskTab');
    $todoTasksContainer.empty();
    $.getJSON(todoTasksRESTUrl, function (todoTasks) {
      $.each(todoTasks, function (key, val) {
        var $taskDiv = appendTaskItemToContainer($todoTasksContainer, val);
        var $startTaskButton = $("<button></button>");
        $startTaskButton.attr({
          id: "startTask" + val.id
        })
          .data("taskId", val.id)
          .addClass("btn btn-danger startTaskButton pull-right")
          .html("Start this Task!")
          .appendTo($taskDiv);
      });
      $todoTasksContainer.accordion({
        collapsible: true,
        heightStyle: "content"
      });
    });
  }

  function pullInProgressTasks() {
    var $inProgressTasksContainer = $('#inProgressTaskTab');
    $inProgressTasksContainer.empty();
    $.getJSON(inProgressTasksRESTUrl, function (inProgressTasks) {
      $.each(inProgressTasks, function (key, val) {
        var $taskDiv = appendTaskItemToContainer($inProgressTasksContainer, val);
        var $finishTaskButton = $("<button></button>");
        $finishTaskButton.attr({
          id: "finishTask" + val.id
        })
          .data("taskId", val.id)
          .addClass("btn btn-warning finishTaskButton pull-right")
          .html("Are You Done?")
          .appendTo($taskDiv);
      });
      $inProgressTasksContainer.accordion({
        collapsible: true,
        heightStyle: "content"
      });
    });
  }

  function pullDoneTasks() {
    var $doneTasksContainer = $('#doneTaskTab');
    $doneTasksContainer.empty();
    $.getJSON(doneTasksRESTUrl, function (doneTasks) {
      $.each(doneTasks, function (key, val) {
        appendTaskItemToContainer($doneTasksContainer, val);
      });
      $doneTasksContainer.accordion({
        collapsible: true,
        heightStyle: "content"
      });
    });
  }

  function pullAllUserTasks() {
    var $allTasksContainer = $('#allTaskTab #list');
    $allTasksContainer.empty();
    $.getJSON(allUserTasksRESTUrl, function (allTasks) {
      $.each(allTasks, function (key, val) {
        var $taskDiv = appendTaskItemToContainer($allTasksContainer, val);
        if (val.type === "TODO") {
          var $startTaskButton = $("<button></button>");
          $startTaskButton.attr({
            id: "startTask" + val.id
          })
            .data("taskId", val.id)
            .addClass("btn btn-danger startTaskButton pull-right")
            .html("Start this Task!")
            .appendTo($taskDiv);
        }
        else if (val.type === "INPRORESS") {
          var $finishTaskButton = $("<button></button>");
          $finishTaskButton.attr({
            id: "finishTask" + val.id
          })
            .data("taskId", val.id)
            .addClass("btn btn-warning finishTaskButton pull-right")
            .html("Are You Done?")
            .appendTo($taskDiv);
        }
      });
      $allTasksContainer.accordion({
        collapsible: true,
        heightStyle: "content"
      });
    });
  }

  $('#todoTaskTab').on('click', 'button.startTaskButton', function () {
    var taskId = $(this).data('taskId');
    var updateURL = updateTaskRESTUrl
      .replace('{status}', 'INPROGRESS')
      .replace('{id}', taskId);
    $.get(updateURL)
      .done(function () {
        $('#todoTaskTab')
          .find("h3#" + taskItemHeaderResolver + taskId.replace(/\s/g, ""))
          .fadeOut(300, function () {
            $(this).remove();
            $('#todoTaskTab')
              .find('div#' + taskItemContentResolver + taskId.replace(/\s/g, ""))
              .fadeOut(200, function () {
                $(this).remove();
              });
            pullInProgressTasks();
          });
      });
  });

  $('#inProgressTaskTab').on('click', 'button.finishTaskButton', function () {
    var taskId = $(this).data('taskId');
    var updateURL = updateTaskRESTUrl
      .replace('{status}', 'DONE')
      .replace('{id}', taskId);
    $.get(updateURL)
      .done(function () {
        $('#inProgressTaskTab')
          .find("h3#" + taskItemHeaderResolver + taskId.replace(/\s/g, ""))
          .fadeOut(300, function () {
            $(this).remove();
            $('#inProgressTaskTab')
              .find('div#' + taskItemContentResolver + taskId.replace(/\s/g, ""))
              .fadeOut(200, function () {
                $(this).remove();
              });
            pullDoneTasks();
          });
      });
  });

  $("#addTaskForm").on('change', 'select#taskProject', function () {
    clearTaskAssigneeList();
    var $selectedSpace = $(this).find(':selected');
    var spaceUrl = $selectedSpace.data('url');
    var $taskAssigneeSelectList = $('select#taskAssignee');
    $.getJSON(buildSpaceMemberUrl(spaceUrl), function (data) {
      var spaceMembers = data.names;
      $.each(spaceMembers, function (key, val) {
        $("<option></option>", {value: val, text: val})
          .data({
            userName: val
          })
          .appendTo($taskAssigneeSelectList);
      });
    });
  });

  $("#perProjectTaskTab").on('change', 'select#perProjectTaskProject', function () {
    var $selectedSpace = $(this).find(':selected');
    var spaceUrl = $selectedSpace.data('url');
    $.getJSON(projectTasksRESTUrl.replace('{project}', spaceUrl), function (projectTasks) {
      var $perProjectTasksContainer = $('#projectTasksContainer');
      $perProjectTasksContainer.empty();
      $.each(projectTasks, function (key, val) {
        appendTaskItemToContainer($perProjectTasksContainer, val);
      });
      $perProjectTasksContainer.accordion({
        collapsible: true,
        heightStyle: "content"
      });
    });
  });

  function appendTaskItemToContainer(container, task) {
    var $taskId = $("<h3></h3>");
    $taskId.html(task.id)
      .attr("id", taskItemHeaderResolver + task.id.replace(/\s/g, ""))
      .appendTo(container);
    var $taskDiv = $("<div></div>");
    $taskDiv
      .attr("id", taskItemContentResolver + task.id.replace(/\s/g, ""))
      .addClass("taskItemContainer");
    var $taskDescription = $("<textarea></textarea>");
    $taskDescription.val(task.description)
      .prop({"disabled": true})
      .appendTo($taskDiv);
    var $taskOwnerContainer = $("<div></div>");
    $taskOwnerContainer.addClass("taskItemProperty");
    var $taskOwnerLabel = $("<label></label>");
    $taskOwnerLabel
      .html("Owner: ")
      .appendTo($taskOwnerContainer);
    var $taskOwnerInput = $("<input />");
    $taskOwnerInput
      .attr({
        type: "text",
        placeholder: task.owner,
        readonly: true
      })
      .appendTo($taskOwnerContainer);
    $taskOwnerContainer.appendTo($taskDiv);
    var $taskAssigneeContainer = $("<div></div>");
    $taskAssigneeContainer.addClass("taskItemProperty");
    var $taskAssigneeLabel = $("<label></label>");
    $taskAssigneeLabel
      .html("Assignee: ")
      .appendTo($taskAssigneeContainer);
    var $taskAssigneeInput = $("<input />");
    $taskAssigneeInput
      .attr({
        type: "text",
        placeholder: task.assignee,
        readonly: true
      })
      .appendTo($taskAssigneeContainer);
    $taskAssigneeContainer.appendTo($taskDiv);
    var $taskStartDateContainer = $("<div></div>");
    $taskStartDateContainer.addClass("taskItemProperty");
    var $taskStartDateLabel = $("<label></label>");
    $taskStartDateLabel
      .html("Start Date: ")
      .appendTo($taskStartDateContainer);
    var $taskStartDateInput = $("<input />");
    $taskStartDateInput
      .attr({
        type: "text",
        placeholder: new Date(task.startDate.time),
        readonly: true
      })
      .appendTo($taskStartDateContainer);
    $taskStartDateContainer.appendTo($taskDiv);
    var $taskDueDateContainer = $("<div></div>");
    $taskDueDateContainer.addClass("taskItemProperty");
    var $taskDueDateLabel = $("<label></label>");
    $taskDueDateLabel
      .html("Due Date: ")
      .appendTo($taskDueDateContainer);
    var $taskDueDateInput = $("<input />");
    $taskDueDateInput
      .attr({
        type: "text",
        placeholder: new Date(task.dueDate.time),
        readonly: true
      })
      .appendTo($taskDueDateContainer);
    $taskDueDateContainer.appendTo($taskDiv);
    var $taskStatusContainer = $("<div></div>");
    $taskStatusContainer.addClass("taskItemProperty");
    var $taskStatusLabel = $("<label></label>");
    $taskStatusLabel
      .html("Status: ")
      .appendTo($taskStatusContainer);
    var $taskStatusInput = $("<input />");
    $taskStatusInput
      .attr({
        type: "text",
        placeholder: task.status,
        readonly: true
      })
      .appendTo($taskStatusContainer);
    $taskStatusContainer.appendTo($taskDiv);
    $taskDiv.appendTo(container);
    return $taskDiv;
  }

  function clearTaskProjectList() {
    $("select#taskProject option")
      .not(".taskProjectPlaceholder")
      .remove();
  }

  function clearTaskAssigneeList() { // TODO
    $("select#taskAssignee option")
      .not(".taskAssigneePlaceholder")
      .remove();
  }

  function buildSpaceMemberUrl(spaceUrl) {
    return "/" + TaskManagerUtil.rest
      + "/social/people/suggest.json"
      + "?nameToSearch="
      + "&currentUser=" + TaskManagerUtil.userName
      + "&typeOfRelation=member_of_space"
      + "&spaceUrl=" + spaceUrl;
  }

})(jQuery);