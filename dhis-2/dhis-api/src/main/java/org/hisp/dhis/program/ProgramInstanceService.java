package org.hisp.dhis.program;

/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.common.IllegalQueryException;
import org.hisp.dhis.common.OrganisationUnitSelectionMode;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.trackedentity.TrackedEntityInstance;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Abyot Asalefew
 * @version $Id$
 */
public interface ProgramInstanceService
{
    String ID = ProgramInstanceService.class.getName();

    /**
     * Adds an {@link ProgramInstance}
     *
     * @param programInstance The to ProgramInstance add.
     * @return A generated unique id of the added {@link ProgramInstance}.
     */
    long addProgramInstance( ProgramInstance programInstance );

    /**
     * Soft deletes a {@link ProgramInstance}.
     *
     * @param programInstance the ProgramInstance to delete.
     */
    void deleteProgramInstance( ProgramInstance programInstance );

    /**
     * Deletes a program instance. Based on the forceDelete parameter, the program instance is
     * either soft deleted (false) or hard deleted (true)
     *
     * @param programInstance to delete
     * @param forceDelete     soft delete or hard delete
     */
    void deleteProgramInstance( ProgramInstance programInstance, boolean forceDelete );

    /**
     * Updates an {@link ProgramInstance}.
     *
     * @param programInstance the ProgramInstance to update.
     */
    void updateProgramInstance( ProgramInstance programInstance );

    /**
     * Returns a {@link ProgramInstance}.
     *
     * @param id the id of the ProgramInstance to return.
     * @return the ProgramInstance with the given id
     */
    ProgramInstance getProgramInstance( long id );
    
    /**
     * Returns the {@link ProgramInstance} with the given UID.
     *
     * @param uid the UID.
     * @return the ProgramInstance with the given UID, or null if no match.
     */
    ProgramInstance getProgramInstance( String uid );

    /**
     * Checks for the existence of a PI by UID. Deleted values are not taken into account.
     *
     * @param uid PSI UID to check for
     * @return true/false depending on result
     */
    boolean programInstanceExists( String uid );

    /**
     * Checks for the existence of a PI by UID. Takes into account also the deleted values.
     *
     * @param uid PSI UID to check for
     * @return true/false depending on result
     */
    boolean programInstanceExistsIncludingDeleted( String uid );

    List<String> getProgramInstancesUidsIncludingDeleted( List<String> uids );

    /**
     * Returns a ProgramInstanceQueryParams based on the given input.
     *
     * @param ou                    the set of organisation unit identifiers.
     * @param ouMode                the OrganisationUnitSelectionMode.
     * @param lastUpdated           the last updated for PI.
     * @param lastUpdatedDuration   the last updated duration filter.
     * @param program               the Program identifier.
     * @param programStatus         the ProgramStatus in the given program.
     * @param programStartDate      the start date for enrollment in the given
     *                              Program.
     * @param programEndDate        the end date for enrollment in the given Program.
     * @param trackedEntityType     the TrackedEntityType uid.
     * @param trackedEntityInstance the TrackedEntityInstance uid.
     * @param followUp              indicates follow up status in the given Program.
     * @param page                  the page number.
     * @param pageSize              the page size.
     * @param totalPages            indicates whether to include the total number of pages.
     * @param skipPaging            whether to skip paging.
     * @param includeDeleted        whether to include soft deleted ones
     * @return a ProgramInstanceQueryParams.
     */
    ProgramInstanceQueryParams getFromUrl( Set<String> ou, OrganisationUnitSelectionMode ouMode, Date lastUpdated,
        String lastUpdatedDuration, String program, ProgramStatus programStatus, Date programStartDate,
        Date programEndDate, String trackedEntityType, String trackedEntityInstance, Boolean followUp, Integer page,
        Integer pageSize, boolean totalPages, boolean skipPaging, boolean includeDeleted );

    /**
     * Returns a list with program instance values based on the given
     * ProgramInstanceQueryParams.
     *
     * @param params the ProgramInstanceQueryParams.
     * @return List of PIs matching the params
     */
    List<ProgramInstance> getProgramInstances( ProgramInstanceQueryParams params );

    /**
     * Returns the number of program instance matches based on the given
     * ProgramInstanceQueryParams.
     *
     * @param params the ProgramInstanceQueryParams.
     * @return Number of PIs matching the params
     */
    int countProgramInstances( ProgramInstanceQueryParams params );

    /**
     * Decides whether current user is authorized to perform the given query.
     * IllegalQueryException is thrown if not.
     *
     * @param params the ProgramInstanceQueryParams.
     */
    void decideAccess( ProgramInstanceQueryParams params );

    /**
     * Validates the given ProgramInstanceQueryParams. The params is
     * considered valid if no exception are thrown and the method returns
     * normally.
     *
     * @param params the ProgramInstanceQueryParams.
     * @throws IllegalQueryException if the given params is invalid.
     */
    void validate( ProgramInstanceQueryParams params )
        throws IllegalQueryException;

    /**
     * Retrieve program instances on a program
     *
     * @param program Program
     * @return ProgramInstance list
     */
    List<ProgramInstance> getProgramInstances( Program program );

    /**
     * Retrieve program instances on a program by status
     *
     * @param program Program
     * @param status  Status of program-instance, include STATUS_ACTIVE,
     *                STATUS_COMPLETED and STATUS_CANCELLED
     * @return ProgramInstance list
     */
    List<ProgramInstance> getProgramInstances( Program program, ProgramStatus status );

    /**
     * Retrieve program instances on a TrackedEntityInstance with a status by a program
     *
     * @param entityInstance TrackedEntityInstance
     * @param program        Program
     * @param status         Status of program-instance, include STATUS_ACTIVE,
     *                       STATUS_COMPLETED and STATUS_CANCELLED
     * @return ProgramInstance list
     */
    List<ProgramInstance> getProgramInstances( TrackedEntityInstance entityInstance, Program program, ProgramStatus status );

    /**
     * Enroll a TrackedEntityInstance into a program. Must be run inside a transaction.
     *
     * @param trackedEntityInstance TrackedEntityInstance
     * @param program               Program
     * @param enrollmentDate        The date of enrollment
     * @param incidentDate          The date of incident
     * @param orgunit               Organisation Unit
     * @param uid                   UID to use for new instance
     * @return ProgramInstance
     */
    ProgramInstance enrollTrackedEntityInstance( TrackedEntityInstance trackedEntityInstance, Program program,
        Date enrollmentDate, Date incidentDate, OrganisationUnit orgunit, String uid );

    /**
     * Enroll a TrackedEntityInstance into a program. Must be run inside a transaction.
     *
     * @param trackedEntityInstance TrackedEntityInstance
     * @param program               Program
     * @param enrollmentDate        The date of enrollment
     * @param incidentDate          The date of incident
     * @param orgunit               Organisation Unit
     * @return ProgramInstance
     */
    ProgramInstance enrollTrackedEntityInstance( TrackedEntityInstance trackedEntityInstance, Program program, Date enrollmentDate, Date incidentDate,
        OrganisationUnit orgunit );

    /**
     * Check a program instance if it can be completed automatically. If there
     * is some event of this program-isntance uncompleted or this program has
     * any repeatable stage, then this program cannot be completed automatically
     *
     * @param programInstance ProgramInstance
     * @return True/False value
     */
    boolean canAutoCompleteProgramInstanceStatus( ProgramInstance programInstance );

    /**
     * Complete a program instance. Besides, program template messages will be
     * send if it was defined to send when to complete this program
     *
     * @param programInstance ProgramInstance
     */
    void completeProgramInstanceStatus( ProgramInstance programInstance );

    /**
     * Set status as skipped for overdue events; Remove scheduled events
     *
     * @param programInstance ProgramInstance
     */
    void cancelProgramInstanceStatus( ProgramInstance programInstance );

    /**
     * Incomplete a program instance. This is is possible only if there is
     * no other program instance with active status.
     *
     * @param programInstance ProgramInstance
     */
    void incompleteProgramInstanceStatus( ProgramInstance programInstance );

    /**
     * Prepare a ProgramInstance for storing
     *
     * @param trackedEntityInstance TrackedEntityInstance
     * @param program               Program
     * @param programStatus         ProgramStatus
     * @param enrollmentDate        The date of enrollment
     * @param incidentDate          The date of incident
     * @param orgUnit               Organisation Unit
     * @param uid                   UID to use for new instance
     * @return ProgramInstance
     */
    ProgramInstance prepareProgramInstance( TrackedEntityInstance trackedEntityInstance, Program program,
        ProgramStatus programStatus, Date enrollmentDate, Date incidentDate, OrganisationUnit orgUnit, String uid );
}
