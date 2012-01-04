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

package org.myjerry.mysite.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
@XStreamAlias("template")
public class Template {
	
	@XStreamAsAttribute
	private String id;
	
	@XStreamAsAttribute
	private String file;
	
	public Template() {
		
	}
	
	public Template(String id, String file) {
		this.id = id;
		this.file = file;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1024);
		
		builder.append("[Template: id=");
		builder.append(this.id);
		builder.append(", file=");
		builder.append(this.file);
		builder.append("]");
		
		return builder.toString();
	}
	
	// Usual accessor's follow

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
