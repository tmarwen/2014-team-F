package org.exoplatform.addons.codefest.fteam.service.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.security.ConversationState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eXo Platform MEA on 27/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskBeanMapper
{
  private static final Log LOG = ExoLogger.getLogger("org.exoplatform.addons.codefest.fteam.service.mapper.TaskBeanMapper");

  private static final String SPACE = " ";
  public static final String TASK_TRIGGER = "Start Immediately";

  public static final TaskBean mapStringifiedTask(String taskId,
                                                  String taskDescription,
                                                  String taskDueDate,
                                                  String taskDueTime,
                                                  String taskStartDate,
                                                  String taskStartTime,
                                                  String taskType,
                                                  String taskAssignee,
                                                  String taskProject,
                                                  String taskTrigger)
  {
    Date dueDate;
    Date startDate;
    String dueDateAndTime = new StringBuilder().append(taskDueDate).append(SPACE).append(taskDueTime).toString();
    String startDateAndTime = new StringBuilder().append(taskStartDate).append(SPACE).append(taskStartTime).toString();
    dueDate = TaskManagementUtils.parse(dueDateAndTime);
    startDate = TaskManagementUtils.parse(startDateAndTime);

    String owner;
    if (taskType.equalsIgnoreCase(TaskType.PROJECT.name()))
    {
      owner = taskProject;
    }
    else
    {
      ConversationState conversationState = ConversationState.getCurrent();
      owner = taskAssignee = conversationState.getIdentity().getUserId();
    }

    TaskBean task = new TaskBean(
        taskId,
        owner,
        dueDate,
        startDate,
        TaskType.valueOf(taskType.toUpperCase()),
        (!StringUtils.isEmpty(taskTrigger) && taskTrigger.equals(TASK_TRIGGER)) ? TaskStatus.INPROGRESS : TaskStatus.TODO,
        taskDescription,
        taskAssignee);

    return task;
  }

}
