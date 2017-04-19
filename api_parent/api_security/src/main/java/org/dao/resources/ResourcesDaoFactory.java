package org.dao.resources;

import com.RMT2Base;

/**
 * A factory for creating DAO instances that manage resources related entities.
 * 
 * @author rterrell
 * 
 */
public class ResourcesDaoFactory extends RMT2Base {

    /**
     * Default Constructor
     */
    public ResourcesDaoFactory() {
        return;
    }

    /**
     * Creates a database implementataion of ResourceDao interface which is
     * capable of accessing data from relational database management system
     * (RDBMS).
     * 
     * @return an instance of {@link ResourceDao}
     */
    public ResourceDao createRmt2OrmDao() {
        ResourceDao dao = new Rmt2OrmWebServicesDaoImpl();
        return dao;
    }

    /**
     * Creates a database implementataion of ResourceDao interface which is
     * capable of accessing data from relational database management system
     * (RDBMS).
     * 
     * @param appName
     *            application name
     * @return an instance of {@link ResourceDao}
     */
    public ResourceDao createRmt2OrmDao(String appName) {
        ResourceDao dao = new Rmt2OrmWebServicesDaoImpl(appName);
        return dao;
    }

    /**
     * Creates a LDAP implementataion of ResourceDao interface which is capable
     * of accessing data from a LDAP server.
     * 
     * @return an instance of {@link ResourceDao}
     */
    public ResourceDao createLdapDao() {
        ResourceDao dao = new LdapWebServicesDaoImpl();
        return dao;
    }

    /**
     * Creates a JNDI implementataion of ComputerResourceDao interface which is
     * capable of accessing data pertaining one or more application server
     * configurations from a LDAP server.
     * 
     * @return an instance of {@link ComputerResourceDao}
     */
    public ComputerResourceDao createLdapComputerAppServerDao() {
        ComputerResourceDao dao = new LdapComputerAppServerDaoImpl();
        return dao;
    }

}