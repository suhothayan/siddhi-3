/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.siddhi.query.api.expression;

import org.wso2.siddhi.query.api.query.QueryEventSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Minus extends Expression {
    private Expression leftValue;
    private Expression rightValue;

    public Minus(Expression leftValue, Expression rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public Expression getLeftValue() {
        return leftValue;
    }

    public Expression getRightValue() {
        return rightValue;
    }

    @Override
    protected void validate(List<QueryEventSource> queryEventSources, String streamReferenceId,
                            boolean processInStreamDefinition) {
        leftValue.validate(queryEventSources, streamReferenceId, processInStreamDefinition);
        rightValue.validate(queryEventSources, streamReferenceId, processInStreamDefinition);
    }

    @Override
    public String toString() {
        return "Minus{" +
               "leftValue=" + leftValue +
               ", rightValue=" + rightValue +
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

        Minus minus = (Minus) o;

        if (leftValue != null ? !leftValue.equals(minus.leftValue) : minus.leftValue != null) {
            return false;
        }
        if (rightValue != null ? !rightValue.equals(minus.rightValue) : minus.rightValue != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = leftValue != null ? leftValue.hashCode() : 0;
        result = 31 * result + (rightValue != null ? rightValue.hashCode() : 0);
        return result;
    }

    @Override
    protected Map<String, Set<String>> getDependency() {
        Map<String, Set<String>> dependencyMap = new HashMap<String, Set<String>>(leftValue.getDependency());
        for (Map.Entry<String,Set<String>> dependency : rightValue.getDependency().entrySet()) {
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
