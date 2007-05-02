/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.ui.common.struts2;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.roller.RollerException;
import org.apache.roller.business.RollerFactory;
import org.apache.roller.business.pings.PingTargetManager;
import org.apache.roller.pojos.PingTargetData;
import org.apache.roller.ui.core.util.struts2.UIAction;


/**
 * Base implementation for action that lists available ping targets and can
 * handle deletion of a single ping target (with confirmation).
 */
public abstract class PingTargetsBase extends UIAction {
    
    // list of available ping targets
    private List pingTargets = Collections.EMPTY_LIST;
    
    // ping target we are working on, if any
    private PingTargetData pingTarget = null;
    
    // id of the ping target to work on
    private String pingTargetId = null;
    
    
    // get logger
    protected abstract Log getLogger();
    
    // load up list of ping targets
    protected abstract void loadPingTargets();
    
    
    // prepare method needs to set ping targets list
    public void myPrepare() {
        
        // load list of ping targets
        loadPingTargets();
        
        // load specified ping target if possible
        if(!StringUtils.isEmpty(getPingTargetId())) {
            try {
                PingTargetManager pingTargetMgr = RollerFactory.getRoller().getPingTargetManager();
                setPingTarget(pingTargetMgr.getPingTarget(getPingTargetId()));
            } catch (RollerException ex) {
                getLogger().error("Error looking up ping target - "+getPingTargetId(), ex);
            }
        }
    }
    
    
    /**
     * Display the ping targets.
     */
    public String execute() {
        return LIST;
    }

    
    /**
     * Delete a ping target (load delete confirmation view).
     */
    public String deleteConfirm() {
        
        if(getPingTarget() != null) {
            setPageTitle("pingTarget.confirmRemoveTitle");
            
            return "confirm";
        } else {
            // TODO: i18n
            addError("Cannot delete ping target: " + getPingTargetId());
        }
        
        return LIST;
    }
    
    
    /**
     * Delete a ping target.
     */
    public String delete() {
        
        if(getPingTarget() != null) {
            
            try {
                PingTargetManager pingTargetMgr = RollerFactory.getRoller().getPingTargetManager();
                pingTargetMgr.removePingTarget(getPingTarget());
                RollerFactory.getRoller().flush();
                
                // remove deleted target from list
                getPingTargets().remove(getPingTarget());
                
                // TODO: i18n
                addMessage("Successfully deleted ping target: "+getPingTarget().getName());
                
            } catch (RollerException ex) {
                getLogger().error("Error deleting ping target - "+getPingTargetId(), ex);
                // TODO: i18n
                addError("Error deleting ping target - "+getPingTargetId());
            }
        } else {
            // TODO: i18n
            addError("Cannot delete ping target: " + getPingTargetId());
        }
        
        return LIST;
    }
    
    
    public List getPingTargets() {
        return pingTargets;
    }

    public void setPingTargets(List pingTargets) {
        this.pingTargets = pingTargets;
    }

    public PingTargetData getPingTarget() {
        return pingTarget;
    }

    public void setPingTarget(PingTargetData pingTarget) {
        this.pingTarget = pingTarget;
    }
    
    public String getPingTargetId() {
        return pingTargetId;
    }

    public void setPingTargetId(String pingTargetId) {
        this.pingTargetId = pingTargetId;
    }
    
}