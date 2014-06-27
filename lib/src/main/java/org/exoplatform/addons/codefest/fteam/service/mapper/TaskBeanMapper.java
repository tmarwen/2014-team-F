package org.exoplatform.addons.codefest.fteam.service.mapper;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.security.ConversationState;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by eXo Platform MEA on 27/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskBeanMapper
{
  private static final Log LOG = ExoLogger.getLogger("org.exoplatform.addons.codefest.fteam.service.mapper.TaskBeanMapper");

  public static final TaskBean mapStringifiedTask(String taskId,
                                                  String taskDueDate,
                                                  String taskStartDate,
                                                  String taskType,
                                                  String taskDescription,
                                                  String taskAssignee,
                                                  String taskProject,
                                                  String taskTrigger)
  {
    Date dueDate;
    Date startDate;
    try
    {
      dueDate = DateFormatUtils.SMTP_DATETIME_FORMAT.parse(taskDueDate);
    } catch (ParseException e)
    {
      LOG.error("Error while parsing due task date:", e);
      dueDate = null;
    }
    try
    {
      startDate = DateFormatUtils.SMTP_DATETIME_FORMAT.parse(taskStartDate);
    } catch (ParseException e)
    {
      LOG.error("Error while parsing start task date:", e);
      startDate = null;
    }

    String owner;
    if (taskType.equalsIgnoreCase(TaskType.PERSONAL.name()))
    {
      owner = taskProject;
    }
    else
    {
      ConversationState conversationState = ConversationState.getCurrent();
      owner = conversationState.getIdentity().getUserId();
    }

    TaskBean task = new TaskBean(
        taskId,
        owner,
        dueDate,
        startDate,
        TaskType.valueOf(taskType.toUpperCase()),
        Boolean.parseBoolean(taskTrigger) ? TaskStatus.INPROGRESS : TaskStatus.TODO,
        taskDescription,
        taskAssignee);

    return task;
  }

}
