/**
 *
 * mysite - Static Site Generator
 * Copyright (c) 2012, myJerry Developers
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.myjerry.mysite;

import org.myjerry.mysite.model.Project;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class ProjectValidator {

	public ProjectValidator(Project project) {
	}

	public boolean validate() {
		return true;
	}

	public String getValidationErrors() {
		return null;
	}

}
