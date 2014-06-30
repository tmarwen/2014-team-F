package org.exoplatform.addons.codefest.fteam.service.rest;

import org.apache.commons.lang3.StringUtils;
import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.addons.codefest.fteam.service.mapper.TaskBeanMapper;
import org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils;
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
import java.util.ArrayList;
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

  private TaskService taskService;

  public TaskRestService(TaskService taskService)
  {
    this.taskService = taskService;
  }

  @GET
  @Path("/get/task/{taskId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTask(@PathParam("taskId") String taskId)
      throws Exception
  {

    TaskBean tasks = taskService.getTask(taskId);
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }

  @GET
  @Path("/list/byStatus/{status}/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listTasksByStatus(@PathParam("status") String status,
                                    @PathParam("userName") String userName) throws Exception
  {
    List<TaskBean> tasks = taskService.getTasksByStatus(status);
    return Response.ok(
        TaskManagementUtils.filterUserTasks(tasks, userName),
        MediaType.APPLICATION_JSON)
        .cacheControl(cacheControl)
        .build();
  }

  @GET
  @Path("/list/byType/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listTasksByType(@PathParam("type") String type) throws Exception
  {
    List<TaskBean> tasks = taskService.getTasksByType(type);
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }

  @GET
  @Path("/list/byProject/{project}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listTasksByProject(@PathParam("project") String project) throws Exception
  {
    List<TaskBean> allProjects = taskService.getProjectTasks(project);
    return Response.ok(allProjects, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }


  @GET
  @Path("/list/all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listAllTasks() throws Exception
  {
    List<TaskBean> tasks = taskService.getAllTasks();
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }

  @GET
  @Path("/list/all/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listAllUserTasks(@PathParam("userName") String userName) throws Exception
  {
    List<TaskBean> tasks = taskService.getAllTasks();
    return Response.ok(
        TaskManagementUtils.filterUserTasks(tasks, userName),
        MediaType.APPLICATION_JSON)
        .cacheControl(cacheControl)
        .build();
  }

  @GET
  @Path("/add/task")
  public void createTask(@QueryParam("taskId") String id,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("dueTime") String dueTime,
                         @QueryParam("startDate") String startDate,
                         @QueryParam("startTime") String startTime,
                         @QueryParam("type") String type,
                         @QueryParam("description") String description,
                         @QueryParam("assignee") String assignee,
                         @QueryParam("project") String project,
                         @QueryParam("trigger") String trigger) throws Exception
  {

    if (!StringUtils.isEmpty(trigger) && trigger.equalsIgnoreCase("YES"))
      trigger = TaskBeanMapper.TASK_TRIGGER;

    TaskBean task = TaskBeanMapper.mapStringifiedTask(
        id,
        description,
        dueDate,
        dueTime,
        startDate,
        startTime,
        type,
        assignee,
        project,
        trigger
    );

    taskService.addTask(task);

  }

  @GET
  @Path("/update/task/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public void updateTask(@PathParam("id") String id,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("dueTime") String dueTime,
                         @QueryParam("startDate") String startDate,
                         @QueryParam("startTime") String startTime,
                         @QueryParam("type") String type,
                         @QueryParam("description") String description,
                         @QueryParam("assignee") String assignee,
                         @QueryParam("project") String project,
                         @QueryParam("trigger") String trigger) throws Exception
  {

    TaskBean task = TaskBeanMapper.mapStringifiedTask(
        id,
        description,
        dueDate,
        dueTime,
        startDate,
        startTime,
        type,
        assignee,
        project,
        trigger
    );
    taskService.updateTask(id, task);
  }

  @GET
  @Path("/update/task/{id}/status")
  @Produces(MediaType.APPLICATION_JSON)
  public void updateTask(@PathParam("id") String id,
                         @QueryParam("status") String status) throws Exception
  {
    TaskBean task = taskService.getTask(id);
    task.setStatus(TaskStatus.valueOf(status));
    taskService.updateTask(id, task);
  }
}
