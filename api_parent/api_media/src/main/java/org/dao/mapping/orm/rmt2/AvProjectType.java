package org.dao.mapping.orm.rmt2;


import java.util.Date;
import java.io.*;
import com.api.persistence.db.orm.OrmBean;
import com.SystemException;


/**
 * Peer object that maps to the av_project_type database table/view.
 *
 * @author auto generated.
 */
public class AvProjectType extends OrmBean {




	// Property name constants that belong to respective DataSource, AvProjectTypeView

/** The property name constant equivalent to property, ProjectTypeId, of respective DataSource view. */
  public static final String PROP_PROJECTTYPEID = "ProjectTypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column av_project_type.project_type_id */
  private int projectTypeId;
/** The javabean property equivalent of database column av_project_type.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 */
  public AvProjectType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable projectTypeId
 */
  public void setProjectTypeId(int value) {
    this.projectTypeId = value;
  }
/**
 * Gets the value of member variable projectTypeId
 */
  public int getProjectTypeId() {
    return this.projectTypeId;
  }
/**
 * Sets the value of member variable description
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 */
  public void initBean() throws SystemException {}
}