/*
*  Copyright (c) 2005-2012, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.siddhi.query.api.query.input;

import org.wso2.siddhi.query.api.condition.Condition;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.query.input.handler.Filter;
import org.wso2.siddhi.query.api.query.input.handler.StreamHandler;
import org.wso2.siddhi.query.api.query.input.handler.Transformer;
import org.wso2.siddhi.query.api.query.input.handler.Window;

import java.util.ArrayList;
import java.util.List;

public class WindowStream implements SingleStream {

    protected String streamId;
    protected AbstractDefinition definition;
    protected String streamReferenceId;

    protected List<StreamHandler> streamHandlers = new ArrayList<StreamHandler>();

    protected int windowPosition = -1;

    protected WindowStream(String streamId) {
        this(streamId, streamId);
    }

    public WindowStream(String streamId, String streamReferenceId) {
        this.streamId = streamId;
        this.streamReferenceId = streamReferenceId;
    }

    public WindowStream(StandardStream standardStream, Window window) {
        streamId = standardStream.getStreamId();
        definition = standardStream.getDefinition();
        streamReferenceId = standardStream.getStreamReferenceId();
        streamHandlers = standardStream.getStreamHandlers();
        windowPosition = standardStream.getStreamHandlers().size();
        streamHandlers.add(window);
    }

    public AbstractDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(AbstractDefinition definition) {
        this.definition = definition;
    }

    public String getStreamId() {
        return streamId;
    }

    public String getStreamReferenceId() {
        return streamReferenceId;
    }

    @Override
    public List<String> getStreamIds() {
        List<String> streamIds = new ArrayList<String>();
        streamIds.add(streamId);
        return streamIds;
    }

    public WindowStream as(String streamReferenceId) {
        this.streamReferenceId = streamReferenceId;
        return this;
    }

    public List<StreamHandler> getStreamHandlers() {
        return streamHandlers;
    }

    public void setStreamHandlers(List<StreamHandler> streamHandlers) {
        this.streamHandlers = streamHandlers;
    }

    public WindowStream filter(Condition filterCondition) {
        streamHandlers.add(new Filter(filterCondition));
        return this;
    }

    public WindowStream filter(Filter filter) {
        streamHandlers.add(filter);
        return this;
    }

    public WindowStream function(String name, Expression... parameters) {
        streamHandlers.add(new Transformer(name, parameters));
        return this;
    }

    public WindowStream function(String extensionName, String functionName,
                                 Expression... parameters) {
        streamHandlers.add(new Transformer(extensionName, functionName, parameters));
        return this;
    }

    public WindowStream function(Transformer transformer) {
        streamHandlers.add(transformer);
        return this;
    }
}
