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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.myjerry.mysite.model.Asset;
import org.myjerry.mysite.model.ProjectFile;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class AssetsProcessor {
	
	private List<Asset> assets;
	
	private File root;
	
	private final List<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
	
	public AssetsProcessor(List<Asset> assets, File root) {
		this.assets = assets;
		this.root = root;
	}
	
	public List<ProjectFile> process() {
		if(this.assets == null || this.assets.size() == 0) {
			return projectFiles;
		}
		
		final String rootPath = root.getAbsolutePath();
		
		for(Asset asset : this.assets) {
			if(asset.isFolder()) {
				File src = new File(root, asset.getFolder());
				Collection<File> files = FileUtils.listFiles(src, null, true);
				for(File file : files) {
					try {
						String path = file.getAbsolutePath();

						if(asset.getTo() != null) {
							String removal = rootPath + File.separator + asset.getFolder();
							path = path.substring(removal.length());
							
							path = asset.getTo() + File.separator + path;
						} else {
							path = path.substring(rootPath.length());
						}
						
						byte[] bytes = FileUtils.readFileToByteArray(file);
						projectFiles.add(new ProjectFile(path, bytes));
					} catch(IOException e) {
						
					}
				}
			}
		}
		
		return projectFiles;
	}

}
