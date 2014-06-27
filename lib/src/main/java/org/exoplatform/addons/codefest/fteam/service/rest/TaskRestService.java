package org.exoplatform.addons.codefest.fteam.service.rest;

import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.resource.ResourceContainer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
@Path("/task-rest-manager")
public class TaskRestService implements ResourceContainer
{
  private static final Log LOG = ExoLogger.getLogger(TaskService.class);

  private static final CacheControl cacheControl;

  static
  {
    RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
    cacheControl = new CacheControl();
    cacheControl.setNoCache(true);
    cacheControl.setNoStore(true);
  }

  private TaskService _taskService;

  public TaskRestService(TaskService taskService)
  {
    _taskService = taskService;
  }

  @GET
  @Path("/getTask/{taskId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTaskOfProject(@PathParam("taskId") String taskId)
      throws Exception
  {

    TaskBean tasks = _taskService.getTask(taskId);
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }

  @GET
  @Path("/getAllTask")
  public Response getAllProject() throws Exception
  {
    List<TaskBean> tasks = _taskService.getAllTasks();
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }


  @GET
  @Path("/createTask")
  public void createTask(@QueryParam("taskId") long taskId,
                         @QueryParam("owner") String owner,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("startDate") String startDate,
                         @QueryParam("type") String type,
                         @QueryParam("status") String status,
                         @QueryParam("description") String description,
                         @QueryParam("assignee") String assignee) throws Exception
  {

    TaskBean task = new TaskBean();

    task.setOwner(owner);
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    Date _duedate = formatter.parse(dueDate);
    Date _startdate = formatter.parse(startDate);
    task.setDueDate(_duedate);
    task.setStartDate(_startdate);
    task.setDescription(description);
    task.setAssignee(assignee);
    if (status.equals("todo"))
    {
      task.setStatus(TaskStatus.TODO);
    }
    if (status.equals("INPROGRESS"))
    {
      task.setStatus(TaskStatus.INPROGRESS);
    }
    if (status.equals("DONE"))
    {
      task.setStatus(TaskStatus.DONE);
    }

    if (type.equals("PERSONAL"))
    {
      task.setType(TaskType.PERSONAL);
    }

    if (type.equals("PROJECT"))
    {
      task.setType(TaskType.PROJECT);
    }

    _taskService.addTask(task);

  }

  @GET
  @Path("/updatetask")
  public void updateTask(@QueryParam("taskId") long taskId,
                         @QueryParam("owner") String owner,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("startDate") String startDate,
                         @QueryParam("type") String type,
                         @QueryParam("status") String status,
                         @QueryParam("description") String description,
                         @QueryParam("assignee") String assignee) throws Exception
  {

    TaskBean task = new TaskBean();

    task.setOwner(owner);
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    Date _duedate = formatter.parse(dueDate);
    Date _startdate = formatter.parse(startDate);
    task.setDueDate(_duedate);
    task.setStartDate(_startdate);
    task.setDescription(description);
    task.setAssignee(assignee);
    if (status.equals("todo"))
    {
      task.setStatus(TaskStatus.TODO);
    }
    if (status.equals("INPROGRESS"))
    {
      task.setStatus(TaskStatus.INPROGRESS);
    }
    if (status.equals("DONE"))
    {
      task.setStatus(TaskStatus.DONE);
    }

    if (type.equals("PERSONAL"))
    {
      task.setType(TaskType.PERSONAL);
    }

    if (type.equals("PROJECT"))
    {
      task.setType(TaskType.PROJECT);
    }

    _taskService.addTask(task);

  }
}
