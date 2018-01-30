package org.modules.admin;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dao.ProjecttrackerDaoException;
import org.dao.admin.ProjectAdminDao;
import org.dao.admin.ProjectAdminDaoException;
import org.dao.admin.ProjectAdminDaoFactory;
import org.dto.ClientDto;
import org.dto.EventDto;
import org.dto.ProjectDto;
import org.dto.ProjectEventDto;
import org.dto.ProjectTaskDto;
import org.dto.TaskDto;
import org.dto.adapter.orm.ProjectObjectFactory;

import com.InvalidDataException;
import com.NotFoundException;
import com.api.foundation.AbstractTransactionApiImpl;
import com.api.persistence.DaoClient;
import com.util.RMT2Date;
import com.util.assistants.Verifier;
import com.util.assistants.VerifyException;

/**
 * Api Implementation of {@link ProjectApi} that manages the creation, querying,
 * updating, and deletion of project entities.
 * 
 * @author Roy Terrell
 * 
 */
class ProjectAdminApiImpl extends AbstractTransactionApiImpl implements ProjectAdminApi {

    private static final Logger logger = Logger.getLogger(ProjectAdminApiImpl.class);

    private ProjectAdminDaoFactory daoFact;

    private ProjectAdminDao dao;

    /**
     * Creates an ProjectApiImpl which creates a stand alone connection.
     */
    protected ProjectAdminApiImpl() {
        super();
        this.dao = this.daoFact.createRmt2OrmDao();
        this.setSharedDao(this.dao);
        logger.info("ProjectAdminApiImpl created with DAO, " + this.dao.getClass().getSimpleName());
        return;
    }

    /**
     * Creates an ProjectApiImpl which creates based on the identified
     * application.
     */
    protected ProjectAdminApiImpl(String appName) {
        super();
        this.dao = this.daoFact.createRmt2OrmDao(appName);
        this.setSharedDao(this.dao);
        logger.info("ProjectAdminApiImpl created with DAO, " + this.dao.getClass().getSimpleName() + ", for application, " + appName);
        return;
    }

    /**
     * Creates an ProjectApiImpl initialized with a shared connection,
     * <i>dao</i>. object.
     * 
     * @param dao
     */
    protected ProjectAdminApiImpl(DaoClient dao) {
        super(dao);
        this.dao = this.daoFact.createRmt2OrmDao(this.getSharedDao());
        logger.info("ProjectAdminApiImpl created with outside Api DAO, " + this.dao.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.RMT2Base#init()
     */
    @Override
    public void init() {
        super.init();
        this.daoFact = new ProjectAdminDaoFactory();
        return;
    }

    @Override
    public List<ClientDto> getClient(ClientDto criteria) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Client criteria is required");
        }

        List<ClientDto> results;
        StringBuilder buf = new StringBuilder();
        try {
            results = dao.fetchClient(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred retrieving client(s) by selection criteria: ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.modules.admin.ProjectAdminApi#getProject(org.dto.ProjectDto)
     */
    @Override
    public List<ProjectDto> getProject(ProjectDto criteria) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Project criteria is required");
        }

        List<ProjectDto> results;
        StringBuilder buf = new StringBuilder();
        try {
            results = dao.fetchProject(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred retrieving project(s) by selection criteria: ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.modules.admin.ProjectAdminApi#getTask(org.dto.TaskDto)
     */
    @Override
    public List<TaskDto> getTask(TaskDto criteria) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Task criteria is required");
        }

        List<TaskDto> results;
        StringBuilder buf = new StringBuilder();
        try {
            results = dao.fetchTask(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred retrieving task(s) by selection criteria: ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.modules.admin.ProjectAdminApi#getEvent(org.dto.EventDto)
     */
    @Override
    public List<EventDto> getEvent(EventDto criteria, Date beginDate, Date endDate) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Event criteria is required");
        }
        List<EventDto> results;
        
        // See if we need to build a date range for event date column.
        StringBuffer sql = new StringBuffer();
        if (beginDate != null && endDate == null) {
            sql.append(" event_date >= ");
            sql.append(RMT2Date.formatDate(beginDate, "yyyy-MM-dd"));
        }
        if (beginDate == null && endDate != null) {
            sql.append(" event_date <= ");
            sql.append(RMT2Date.formatDate(endDate, "yyyy-MM-dd"));
        }
        if (beginDate != null && endDate != null) {
            sql.append(" event_date between( ");
            sql.append(RMT2Date.formatDate(beginDate, "yyyy-MM-dd"));
            sql.append(" and ");
            sql.append(RMT2Date.formatDate(endDate, "yyyy-MM-dd"));
            sql.append(") ");
        }
        criteria.setCriteria(sql.toString());
        try {
            results = dao.fetchEvent(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            StringBuilder buf = new StringBuilder();
            buf.append("Database error occurred retrieving event(s) by selection criteria: ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }

    @Override
    public List<ProjectTaskDto> getProjectTask(ProjectTaskDto criteria) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Project-Task criteria is required");
        }
        
        List<ProjectTaskDto> results;
        StringBuilder buf = new StringBuilder();
        try {
            results = dao.fetchProjectTask(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred retrieving project/task objects by selection criteria, ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }
 
    @Override
    public List<ProjectEventDto> getProjectEvent(ProjectEventDto criteria) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(criteria);
        } catch (VerifyException e) {
            throw new InvalidDataException("Project-Event criteria is required");
        }
        List<ProjectEventDto> results;
        StringBuilder buf = new StringBuilder();
        try {
            results = dao.fetchProjectEvent(criteria);
            if (results == null) {
                return null;
            }
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred retrieving events by project/event by selection criteria, ");
            buf.append(criteria.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
        return results;
    }
    
    /**
     * Validates a client object.
     * 
     * @param client
     *            an instance of {@link ClientDto}
     * @throws InvalidClientIdentifierException
     *             <i>clinet</i> is null, the client id property of
     *             <i>clinet</i> is less than or equal to zero, or <i>clinet</i>
     *             does not exist in the accounting system.
     * @throws NotFoundException <i>client</i> does not exists in the project tracker system.            
     */
    protected void validateClient(ClientDto client) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(client);
        }
        catch (VerifyException e) {
            throw new InvalidDataException("Client data is required");
        }
        try {
            Verifier.verifyNotNegative(client.getClientId());
        }
        catch (VerifyException e) {
            throw new InvalidDataException("The client id must be greater than or equal to zero");
        }

        try {
            Verifier.verifyNotNull(client.getClientName());
        }
        catch (VerifyException e) {
            throw new InvalidDataException("The client's name is required");
        }
        
        // For nex clients, exit.
        if (client.getClientId() == 0) {
            return;
        }

        // When updating and existing client, make API call to determine
        // client's existence. In order to setup a project, the client must
        // already exists in the project tracker system. Otherwise, produce an
        // error indicating tha project/client association is incorrect.
        List<ClientDto> results;
        try {
            // Check if client exists locally
            ClientDto criteria = ProjectObjectFactory.createClientDtoInstance(null);
            criteria.setClientId(client.getClientId());
            results = this.getClient(criteria);
        } catch (ProjectAdminApiException e) {
            this.msg = "Error fetching client: " + client.getClientId();
            throw new ProjectAdminApiException(this.msg, e);
        }
        if (results == null) {
            this.msg = "Project is assoicated with a client id that does not have a profile in the Project Tracker system.  Client Id is "
                    + client.getClientId();
            throw new NotFoundException(this.msg);
        }
        return;
    }

    @Override
    public int updateClient(ClientDto client) throws ProjectAdminApiException {
        try {
            this.validateClient(client);  
        }
        catch (Exception e) {
            throw new ProjectAdminApiException("Client validation error occurred", e);
        }
        try {
            int rc = this.dao.maintainClient(client);
            return rc;
        }
        catch (ProjectAdminDaoException e) {
            throw new ProjectAdminApiException("Unable to update Client data", e);
        }
        
        // TODO: Add logic to call web service that will send client updates to
        // the AddressBook application. This is in case that changes occurred
        // for client name, contact firstname, contact lastname, contact email,
        // et cetera. Most likely, this will be a call to some mechanism that
        // will put the intended message payload on a JMS queue / topic just as
        // the public server does when contacting one of the internal API's to
        // delagate the user's request.
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.modules.admin.ProjectApi#updateProject(org.dto.ProjectDto)
     */
    @Override
    public int updateProject(ProjectDto project) throws ProjectAdminApiException {
        // Validate project object
        try {
            this.validateProject(project);    
        }
        catch (Exception e) {
            throw new ProjectAdminApiException("Project validation error occurred", e);
        }
        
        // Begin to process project data.
        try {
            int rc = this.dao.maintainProject(project);
            return rc;
        }
        catch (ProjectAdminDaoException e) {
            throw new ProjectAdminApiException("Unable to update Project data", e);
        }
    }

    /**
     * Validates a project object.
     * 
     * @param proj
     *            The project object that is being validated.
     * @throws InvalidProjectException
     *             <i>proj</i> is null or no data values are provided for
     *             <i>proj</i>'s properties, description and/or effective date.
     */
    protected void validateProject(ProjectDto proj) throws InvalidProjectException {
        try {
            Verifier.verifyNotNull(proj);
        }
        catch (VerifyException e) {
            throw new InvalidDataException("Project data is required");
        }
        try {
            Verifier.verifyNotNegative(proj.getProjId());
        }
        catch (VerifyException e) {
            throw new InvalidDataException("The project id must be greater than or equal zero");
        }
        try {
            Verifier.verifyNotNull(proj.getProjectDescription());
        }
        catch (VerifyException e) {
            throw new InvalidProjectException("Project description is required");
        }
        try {
            Verifier.verifyNotNull(proj.getProjectEffectiveDate());
        }
        catch (VerifyException e) {
            throw new InvalidProjectException("Project effective date is required");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.modules.admin.ProjectApi#updateTask(org.dto.TaskDto)
     */
    @Override
    public int updateTask(TaskDto task) throws ProjectAdminApiException {
        try {
            this.validateTask(task);
        }
        catch (Exception e) {
            throw new ProjectAdminApiException("Task validation error occurred", e);
        }
        try {
            int rc = this.dao.maintainTask(task);
            return rc;    
        }
        catch (ProjectAdminDaoException e) {
            throw new ProjectAdminApiException("Unable to update Task data", e);
        }
    }

    /**
     * Validates a Task object.
     * 
     * @param task
     *            The task object that is being validated.
     * @throws InvalidTaskException
     *             <i>task</i> is null or <i>task</i>'s description property has
     *             no value.
     */
    protected void validateTask(TaskDto task) throws InvalidTaskException {
        try {
            Verifier.verifyNotNull(task);
        }
        catch (VerifyException e) {
            throw new InvalidTaskException("Task data is required");
        }
        try {
            Verifier.verifyNotNegative(task.getTaskId());
        }
        catch (VerifyException e) {
            throw new InvalidDataException("The task id must be greater than or equal zero");
        }
        try {
            Verifier.verifyNotNull(task.getTaskDescription());
        }
        catch (VerifyException e) {
            throw new InvalidTaskException("Task Description is required");
        }
    }

    @Override
    public int deleteClient(ClientDto client) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(client);
        } catch (VerifyException e) {
            throw new InvalidDataException("Client criteria is required");
        }
        int rc = 0;
        StringBuilder buf = new StringBuilder();
        try {
            rc = dao.deleteClient(client);
            return rc;
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred deleting client(s) by selection criteria: ");
            buf.append(client.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
    }

    @Override
    public int deleteProject(ProjectDto project) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(project);
        } catch (VerifyException e) {
            throw new InvalidDataException("Project criteria is required");
        }
        int rc = 0;
        StringBuilder buf = new StringBuilder();
        try {
            rc = dao.deleteProject(project);
            return rc;
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred deleting project(s) by selection criteria: ");
            buf.append(project.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
    }

    @Override
    public int deleteTask(TaskDto task) throws ProjectAdminApiException {
        try {
            Verifier.verifyNotNull(task);
        } catch (VerifyException e) {
            throw new InvalidDataException("Task criteria is required");
        }
        int rc = 0;
        StringBuilder buf = new StringBuilder();
        try {
            rc = dao.deleteTask(task);
            return rc;
        } catch (ProjecttrackerDaoException e) {
            buf.append("Database error occurred deleting task(s) by selection criteria: ");
            buf.append(task.toString());
            this.msg = buf.toString();
            throw new ProjectAdminApiException(this.msg, e);
        }
    }
}
