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
    restTaskManagerURI:  'task-rest-manager'
  };

  var restTaskManagerURL = "/" + TaskManagerUtil.rest + "/" + TaskManagerUtil.restTaskManagerURI;
  var userSpaceRESTUrl = "/" + TaskManagerUtil.rest + TaskManagerUtil.context + "/social/spaces/mySpaces/show.json";
  var userSpaces = [];

//  $("#taskOperationTabs").tabs();

  $( "#taskOperationTabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
  $( "#taskOperationTabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );

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
  $("#taskTypeWrapper").on('change', 'input:radio', function() {
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

  $.getJSON(userSpaceRESTUrl, function(data){
    var mySpaces = data.spaces;
    var $taskProjectSelectList = $('select#taskProject');
    clearTaskProjectList();
    $.each(mySpaces, function( key, val ) {
      var spaceName = val.name;
      userSpaces.push(val);
      $("<option></option>", {value: spaceName, text: val.displayName})
        .data({
          spaceName: spaceName,
          spaceUrl: val.spaceUrl,
          groupId: val.groupId,
          url: val.url
        })
        .appendTo($taskProjectSelectList);
    });
  });

  $("#addTaskForm").on('change', 'select#taskProject', function() {
    clearTaskAssigneeList();
    var $selectedSpace = $(this).find(':selected');
    var spaceUrl = $selectedSpace.data('spaceUrl');
    var $taskAssigneeSelectList = $('select#taskAssignee');
    $.getJSON(buildSpaceMemberUrl(spaceUrl), function(data){
      var spaceMembers = data.names;
      $.each(spaceMembers, function( key, val ) {
        $("<option></option>", {value: val, text: val})
          .data({
            userName: val
          })
          .appendTo($taskAssigneeSelectList);
      });
    });
  });

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