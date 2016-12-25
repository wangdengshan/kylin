/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.apache.kylin.metadata.model;

import java.io.Serializable;
import java.util.List;

import org.apache.kylin.common.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ModelDimensionDesc implements Serializable {
    @JsonProperty("table")
    private String table;
    @JsonProperty("columns")
    private String[] columns;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    void init(DataModelDesc model) {
        table = table.toUpperCase();
        if (columns != null) {
            StringUtil.toUpperCaseArray(columns, columns);
        }
        
        if (model != null) {
            table = model.findTable(table).getAlias();
            if (columns != null) {
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = model.findColumn(table, columns[i]).getName();
                }
            }
        }
    }

    public static void capicalizeStrings(List<ModelDimensionDesc> dimensions) {
        if (dimensions != null) {
            for (ModelDimensionDesc modelDimensionDesc : dimensions) {
                modelDimensionDesc.init(null);
            }
        }
    }

    public static int getColumnCount(List<ModelDimensionDesc> modelDimensionDescs) {
        int count = 0;
        for (ModelDimensionDesc modelDimensionDesc : modelDimensionDescs) {
            if (modelDimensionDesc.getColumns() != null) {
                count += modelDimensionDesc.getColumns().length;
            }
        }
        return count;
    }

}
