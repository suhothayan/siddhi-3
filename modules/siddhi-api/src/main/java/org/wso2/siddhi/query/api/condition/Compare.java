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

import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.ExpressionValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Compare extends Condition {
    private Expression rightExpression;
    private Operator operator;
    private Expression leftExpression;

    public Compare(Expression leftExpression, Operator operator,
                   Expression rightExpression) {
        this.rightExpression = rightExpression;
        this.operator = operator;
        this.leftExpression = leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

//    @Override
//    protected void validate(List<QueryEventSource> queryEventSourceList, ConcurrentMap<String, AbstractDefinition> streamTableDefinitionMap, String streamReferenceId,
//                            boolean processInStreamDefinition) {
//        if (operator.equals(Operator.INSTANCE_OF)) {
//
//            if (rightExpression instanceof StringConstant) {
//                String dataType = ((StringConstant) rightExpression).getValue();
//                if (!(dataType.equalsIgnoreCase(String.class.getSimpleName()) || dataType.equalsIgnoreCase(Integer.class.getSimpleName())
//                      || dataType.equalsIgnoreCase(Double.class.getSimpleName()) || dataType.equalsIgnoreCase(Boolean.class.getSimpleName())
//                      || dataType.equalsIgnoreCase(Long.class.getSimpleName()) || dataType.equalsIgnoreCase(Float.class.getSimpleName()))) {
//                    throw new MalformedAttributeException("\"" + dataType + "\" is not a valid attribute data type");
//                }
//            }
//
//        }
//        ExpressionValidator.validate(rightExpression, queryEventSourceList, streamReferenceId, processInStreamDefinition);
//        ExpressionValidator.validate(leftExpression, queryEventSourceList, streamReferenceId, processInStreamDefinition);
//    }

    @Override
    public String toString() {
        return "Compare{" +
               "rightExpression=" + rightExpression +
               ", operator=" + operator +
               ", leftExpression=" + leftExpression +
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

        Compare compare = (Compare) o;

        if (leftExpression != null ? !leftExpression.equals(compare.leftExpression) : compare.leftExpression != null) {
            return false;
        }
        if (operator != compare.operator) {
            return false;
        }
        if (rightExpression != null ? !rightExpression.equals(compare.rightExpression) : compare.rightExpression != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = rightExpression != null ? rightExpression.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (leftExpression != null ? leftExpression.hashCode() : 0);
        return result;
    }

    @Override
    protected Map<String, Set<String>> getDependency() {
        Map<String, Set<String>> dependencyMap = new HashMap<String, Set<String>>(ExpressionValidator.getDependency(leftExpression));
        for (Map.Entry<String, Set<String>> dependency : ExpressionValidator.getDependency(rightExpression).entrySet()) {
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
