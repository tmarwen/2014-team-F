/**
 * Created by The eXo Platform MEA
 * Author : Marwen Trabelsi
 *         mtrabelsi@exoplatform.com
 * June 28, 2014
 */
(function ($) {

  var portal = window.eXo.env.portal;

  var taskManager = {
    rest: (portal.rest) ? portal.rest : 'rest',
    portalName: (portal.portalName) ? portal.portalName : 'classic',
    context: (portal.context) ? portal.context : '/portal',
    accessMode: (portal.accessMode) ? portal.accessMode : 'public',
    userName: (portal.userName) ? portal.userName : '',
    restTaskManagerURI:  'task-rest-manager'
  };

  var restTaskManagerURL = "/" + taskManager.rest + "/" + taskManager.restTaskManagerURI;

  $.getJSON(restTaskManagerURL + "/" + "getAllTask", function(data){
    var items = [];
    $.each( data, function( key, val ) {
      items.push( "<li id='" + key + "'>" + val + "</li>" );
      console.log(val);
    });
  });

  $("#tabs").tabs();


})(jQuery);