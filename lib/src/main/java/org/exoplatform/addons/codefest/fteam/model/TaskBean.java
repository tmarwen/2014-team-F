package org.exoplatform.addons.codefest.fteam.model;

import java.util.Date;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskBean
{
  private Integer _id;
  private String _owner;
  private Date due_date;
  private String _description;
  private int _duration;

  public TaskBean(Integer id,
                  String owner,
                  Date date,
                  String description,
                  int duration)
  {
    _id = id;
    _owner = owner;
    due_date = date;
    _description = description;
    _duration = duration;
  }

  public Integer getId()
  {
    return _id;
  }

  public String getOwner()
  {
    return _owner;
  }

  public Date getDate()
  {
    return due_date;
  }

  public String getDescription()
  {
    return _description;
  }

  public int getDuration()
  {
    return _duration;
  }
}
