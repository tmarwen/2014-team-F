package org.exoplatform.addons.codefest.fteam.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskBean
{
  private String id;
  private String owner;
  private Date dueDate;
  private Date startDate;
  private TaskType type;
  private String description;
  private TaskStatus status;
  private String assignee;

  public TaskBean() {}

  public TaskBean(String _id,
                  String _owner,
                  Date due_date,
                  Date start_date,
                  TaskType type,
                  TaskStatus status,
                  String _description,
                  String assignee)
  {
    this.id = _id;
    this.owner = _owner;
    this.dueDate = due_date;
    this.startDate = start_date;
    this.type = type;
    this.status = status;
    this.description = _description;
    this.assignee = assignee;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getOwner()
  {
    return owner;
  }

  public void setOwner(String owner)
  {
    this.owner = owner;
  }

  public Date getDueDate()
  {
    return dueDate;
  }

  public void setDueDate(Date dueDate)
  {
    this.dueDate = dueDate;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public void setStartDate(Date startDate)
  {
    this.startDate = startDate;
  }

  public TaskType getType()
  {
    return type;
  }

  public void setType(TaskType type)
  {
    this.type = type;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public TaskStatus getStatus()
  {
    return status;
  }

  public void setStatus(TaskStatus status)
  {
    this.status = status;
  }

  public String getAssignee()
  {
    return assignee;
  }

  public void setAssignee(String assignee)
  {
    this.assignee = assignee;
  }

  public int hashCode()
  {
    return new HashCodeBuilder(17, 31).
        append(id).
        toHashCode();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof TaskBean))
      return false;
    if (obj == this)
      return true;

    TaskBean task = (TaskBean) obj;
    return new EqualsBuilder().
        append(id, task.id).
        isEquals();
  }
}
