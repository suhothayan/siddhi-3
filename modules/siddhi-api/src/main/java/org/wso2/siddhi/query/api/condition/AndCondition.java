/*
 * Copyright (c) 2005 - 2014, WSO2 Inc. (http://www.wso2.org) All Rights
 * Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.siddhi.query.api.condition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AndCondition extends Condition {

    Condition leftCondition;
    Condition rightCondition;

    public AndCondition(Condition leftCondition, Condition rightCondition) {
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
    }

    public Condition getLeftCondition() {
        return leftCondition;
    }

    public Condition getRightCondition() {
        return rightCondition;
    }

//    @Override
//    protected void validate(List<QueryEventSource> queryEventSourceList, ConcurrentMap<String, AbstractDefinition> streamTableDefinitionMap, String streamReferenceId,
//                            boolean processInStreamDefinition) {
//        leftCondition.validate(queryEventSourceList, streamTableDefinitionMap, streamReferenceId, processInStreamDefinition);
//        rightCondition.validate(queryEventSourceList, streamTableDefinitionMap, streamReferenceId, processInStreamDefinition);
//    }

    @Override
    public String toString() {
        return "AndCondition{" +
                "leftCondition='" + leftCondition + '\'' +
                ", rightCondition=" + rightCondition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AndCondition that = (AndCondition) o;

        if (leftCondition != null ? !leftCondition.equals(that.leftCondition) : that.leftCondition != null) {
            return false;
        }
        if (rightCondition != null ? !rightCondition.equals(that.rightCondition) : that.rightCondition != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = leftCondition != null ? leftCondition.hashCode() : 0;
        result = 31 * result + (rightCondition != null ? rightCondition.hashCode() : 0);
        return result;
    }


    @Override
    protected Map<String, Set<String>> getDependency() {
        Map<String, Set<String>> dependencyMap = new HashMap<String, Set<String>>(leftCondition.getDependency());
        for (Map.Entry<String, Set<String>> dependency : rightCondition.getDependency().entrySet()) {
            Set<String> attributeSet = dependencyMap.get(dependency.getKey());
            if (attributeSet != null) {
                attributeSet.addAll(dependency.getValue());
            } else {
                dependencyMap.put(dependency.getKey(), dependency.getValue());
            }

        }
        return dependencyMap;
    }
}
