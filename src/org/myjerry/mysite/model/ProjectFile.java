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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class ProjectFile {
	
	private String path;
	
	private String contents;
	
	private boolean binary;
	
	private byte[] bytes;
	
	private boolean html = false;

	public ProjectFile(String path, String contents) {
		this.setPath(path);
		this.setContents(contents);
	}
	
	public ProjectFile(String path, byte[] contents) {
		this.setPath(path);
		this.setBytes(contents);
	}

	public boolean isHTML() {
		return html;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if(path != null) {
			String lower = path.toLowerCase();
			if(lower.endsWith(".html") || lower.endsWith(".htm")) {
				html = true;
			}
		}
		
		this.path = path;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.binary = false;
		this.contents = contents;
	}

	public void writeToDisk(File parent) throws IOException {
		File file = new File(parent, this.path);
		if(isBinary()) {
			FileUtils.writeByteArrayToFile(file, this.bytes);
		} else {
			FileUtils.writeStringToFile(file, this.contents);
		}
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.binary = true;
		this.bytes = bytes;
	}

	public boolean isBinary() {
		return binary;
	}

}
