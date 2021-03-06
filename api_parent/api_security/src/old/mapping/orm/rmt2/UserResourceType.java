package org.dao.mapping.orm.rmt2;


import com.SystemException;
import com.api.persistence.db.orm.OrmBean;

/**
 * Peer object that maps to the user_resource_type database table/view.
 *
 * 
 */
public class UserResourceType extends OrmBean {




	// Property name constants that belong to respective DataSource, UserResourceTypeView

/** The property name constant equivalent to property, RsrcTypeId, of respective DataSource view. */
  public static final String PROP_RSRCTYPEID = "RsrcTypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column user_resource_type.rsrc_type_id */
  private int rsrcTypeId;
/** The javabean property equivalent of database column user_resource_type.description */
  private String description;
/** The javabean property equivalent of database column user_resource_type.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_resource_type.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_resource_type.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * 
 */
  public UserResourceType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable rsrcTypeId
 *
 * 
 */
  public void setRsrcTypeId(int value) {
    this.rsrcTypeId = value;
  }
/**
 * Gets the value of member variable rsrcTypeId
 *
 * 
 */
  public int getRsrcTypeId() {
    return this.rsrcTypeId;
  }
/**
 * Sets the value of member variable description
 *
 * 
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * 
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * 
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * 
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * 
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * 
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * 
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * 
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * 
 */
  public void initBean() throws SystemException {}
}