package org.hisp.dhis.artemis.audit;

/*
 * Copyright (c) 2004-2019, University of Oslo
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

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.qpid.jms.JmsTopic;
import org.hisp.dhis.artemis.MessageManager;
import org.hisp.dhis.audit.AuditScope;
import org.hisp.dhis.render.RenderService;
import org.nfunk.jep.function.Str;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Morten Olav Hansen <mortenoh@gmail.com>
 */
@Component
public class AuditManager
{
    private final MessageManager messageManager;
    private final RenderService renderService;
    private final Map<AuditScope, JmsTopic> auditScopeDestinationMap;

    public AuditManager( MessageManager messageManager, RenderService renderService,  Map<AuditScope, JmsTopic> auditScopeDestinationMap)
    {
        checkNotNull( messageManager );
        checkNotNull( renderService );
        
        this.messageManager = messageManager;
        this.renderService = renderService;
        this.auditScopeDestinationMap = auditScopeDestinationMap;
    }

    public void send( Audit audit )
    {
        //audit.setData( renderService.toJsonAsString( audit.getData() ) );

        messageManager.send( auditScopeDestinationMap.get(audit.getAuditScope()), audit );
    }
}