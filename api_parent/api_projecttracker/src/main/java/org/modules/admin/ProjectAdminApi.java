package org.modules.admin;

import java.util.Date;
import java.util.List;

import org.dto.ClientDto;
import org.dto.EventDto;
import org.dto.ProjectDto;
import org.dto.ProjectEventDto;
import org.dto.ProjectTaskDto;
import org.dto.TaskDto;

import com.api.foundation.TransactionApi;

/**
 * An API interface for managing the components of a project such as clients,
 * projects, tasks, and events.
 * <p>
 * Functional examples would be the creation and management of clients, tasks,
 * and events.
 * 
 * @author Roy Terrell
 * 
 */
public interface ProjectAdminApi extends TransactionApi {
    
    /**
     * Obtains a list of all clients based on criteria selected.
     * 
     * @param criteria
     *            an instance of {@link ClientDto} representing selection
     *            criteria.
     * @return A List of {@link ClientDto} objects or null if nothing is found.
     * @throws ProjectApiException
     */
    List<ClientDto> getClient(ClientDto criteria) throws ProjectAdminApiException;

    /**
     * Find list of projects based on selection criteria provided.
     * 
     * @param criteria
     *            an instance of {@link ProjectDto} representing selection
     *            criteria.
     * @return A List of {@link ProjectDto} objects or null if nothing is found.
     * @throws ProjectAdminApiException
     */
    List<ProjectDto> getProject(ProjectDto criteria) throws ProjectAdminApiException;

    /**
     * Find list of tasks based on selection criteria provided.
     * 
     * @param criteria
     *            an instance of {@link TaskDto} representing selection
     *            criteria.
     * @return A List of {@link TaskDto} objects or null if nothing is found.
     * @throws ProjectAdminApiException
     */
    List<TaskDto> getTask(TaskDto criteria) throws ProjectAdminApiException;

    /**
     * Find list of events based on selection criteria provided.
     * 
     * @param criteria
     *            an instance of {@link EventDto} representing selection
     *            criteria.
     * @param beginDate
     *            The beginning of the date range. Optional.
     * @param endDate
     *            The ending of the date range. Optional.
     * @return A List of {@link EventDto} objects or null if nothing is found.
     * @throws ProjectAdminApiException
     */
    List<EventDto> getEvent(EventDto criteria, Date beginDate, Date endDate) throws ProjectAdminApiException;
    
    /**
     * Find list of project related tasks based on selection criteria provided.
     * 
     * @param criteria
     *            an instance of {@link ProjectTaskDto} representing selection
     *            criteria.
     * @return A List of {@link ProjectTaskDto} objects or null if nothing is found.
     * @throws ProjectAdminApiException
     */
    List<ProjectTaskDto> getProjectTask(ProjectTaskDto criteria) throws ProjectAdminApiException;
    
    /**
     * Retrieves timesheet extended event records by client
     * 
     * @param clientId
     *            The id of the client
     * @return A List of {@link ProjectEventDto} objects or null if not found.
     * @throws TimesheetApiException
     */
    List<ProjectEventDto> getProjectEventByClient(Integer clientId) throws ProjectAdminApiException;

    /**
     * Retrieves extended timesheet event records by project
     * 
     * @param projectId
     *            The id of the project
     * @return A List of {@link EventDto} objects or null if not found.
     * @throws ProjectApiException
     */
    List<ProjectEventDto> getProjectEventByProject(Integer projectId) throws ProjectAdminApiException;

    /**
     * Retrieves extended event records by task
     * 
     * @param taskId
     *            The id of the task
     * @return A List of {@link EventDto} objects or null if not found.
     * @throws ProjectApiException
     */
    List<ProjectEventDto> getProjectEventByTask(Integer taskId) throws ProjectAdminApiException;

    /**
     * Creates new or updates an existing project.
     * 
     * @param project
     *            An instance of {@link ProjectDto}
     * @return The id of the new project created or the total number of existing
     *         projects modified.
     * @throws ProjectApiException
     */
    int updateProject(ProjectDto project) throws ProjectAdminApiException;

    /**
     * Creates new or updates an existing project task
     * 
     * @param task
     *            An instance of {@link TaskDto}
     * @return The id of the new task created or the total number of existing
     *         tasks modified.
     * @throws ProjectApiException
     */
    int updateTask(TaskDto task) throws ProjectAdminApiException;
}
