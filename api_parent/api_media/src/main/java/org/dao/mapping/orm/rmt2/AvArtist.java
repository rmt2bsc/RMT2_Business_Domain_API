package org.dao.mapping.orm.rmt2;


import java.util.Date;
import java.io.*;
import com.api.persistence.db.orm.OrmBean;
import com.SystemException;


/**
 * Peer object that maps to the av_artist database table/view.
 *
 * @author auto generated.
 */
public class AvArtist extends OrmBean {




	// Property name constants that belong to respective DataSource, AvArtistView

/** The property name constant equivalent to property, ArtistId, of respective DataSource view. */
  public static final String PROP_ARTISTID = "ArtistId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";



	/** The javabean property equivalent of database column av_artist.artist_id */
  private int artistId;
/** The javabean property equivalent of database column av_artist.name */
  private String name;



	// Getter/Setter Methods

/**
 * Default constructor.
 */
  public AvArtist() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable artistId
 */
  public void setArtistId(int value) {
    this.artistId = value;
  }
/**
 * Gets the value of member variable artistId
 */
  public int getArtistId() {
    return this.artistId;
  }
/**
 * Sets the value of member variable name
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 */
  public String getName() {
    return this.name;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 */
  public void initBean() throws SystemException {}
}