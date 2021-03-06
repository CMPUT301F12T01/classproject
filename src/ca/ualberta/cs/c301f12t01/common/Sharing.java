/**
 * Copyright 2012 Neil Borle, Mitchell Home, Bronte Lee, Aaron
 * Padlesky, Eddie Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ca.ualberta.cs.c301f12t01.common;

/**
 * Sharing -- Specifies the sharing of a Task or a Report.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public enum Sharing {
    /** The item is visible only to the user who created it. */
    LOCAL,
    /** The item should be published, and be visible by all users. */
    GLOBAL {
        @Override
        public boolean isLocal() { return false; }
        @Override
        public boolean isGlobal() { return true; }
    },
    /**
     * Designates that a Report should be shared only with the Task fulfiller
     * and the Task creator. This should only affect a Report. If this applied
     * to a Task, it has the same effect as {@link #LOCAL}.
     */
    TASK_CREATOR {
        @Override
        public boolean isPrivate() { return true; }
    };

    public boolean isLocal() { return true; }
    public boolean isGlobal() { return false; }
    public boolean isPrivate() { return false; }
}
