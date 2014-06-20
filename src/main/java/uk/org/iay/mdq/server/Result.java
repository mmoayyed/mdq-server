/*
 * Copyright (C) 2014 Ian A. Young.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.iay.mdq.server;

/**
 * Result of a query.
 */
public interface Result {

    /**
     * Returns whether the {@link Result} represents a query for which no data
     * was found.
     * 
     * @return <code>true</code> if the {@link Result} represents a query for
     * which no data was found
     */
    public abstract boolean isNotFound();
    
    /**
     * Gets the <code>ETag</code> for the result.
     *  
     * @return the <code>ETag</code> for this result
     */
    public abstract String getETag();

    /**
     * Gets the rendered result as a byte array. If no result is
     * represented, returns <code>null</code>.
     * 
     * @return the rendered result as a byte array, or <code>null</code>.
     */
    public abstract byte[] getBytes();

}
