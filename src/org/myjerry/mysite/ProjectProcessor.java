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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.myjerry.mysite.model.Project;
import org.myjerry.mysite.model.ProjectFile;
import org.myjerry.mysite.model.Property;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class ProjectProcessor {
	
	private static final String SITE_DEBUG = "siteDebug";

	private Project project;
	
	private File parentFolder;
	
	private final TemplateProcessor templateProcessor;
	
	private final PageProcessor pageProcessor;
	
	private final AssetsProcessor assetsProcessor;
	
	private final List<ProjectFile> files = new ArrayList<ProjectFile>();
	
	private final VelocityContext siteModel = new VelocityContext();
	
	public ProjectProcessor(Project project, File parentFolder) {
		if(project == null) {
			throw new IllegalArgumentException("Project cannot be null.");
		}
		
		this.project = project;
		this.parentFolder = parentFolder;
		
		// set up various processors
		File root;
		
		// initialize assets
		if(project.getAssetsRoot() == null || project.getAssetsRoot().length() == 0) {
			root = this.parentFolder;
		} else {
			root = new File(this.parentFolder, project.getAssetsRoot());
		}
		this.assetsProcessor = new AssetsProcessor(project.getAssets(), root);
		
		// initialize templates
		if(project.getTemplateRoot() == null || project.getTemplateRoot().length() == 0) {
			root = this.parentFolder;
		} else {
			root = new File(this.parentFolder, project.getTemplateRoot());
		}
		this.templateProcessor = new TemplateProcessor(project.getTemplates(), root);
		
		// initialize pages
		if(project.getPagesRoot() == null || project.getPagesRoot().length() == 0) {
			root = this.parentFolder;
		} else {
			root = new File(this.parentFolder, project.getPagesRoot());
		}
		this.pageProcessor = new PageProcessor(project.getPages(), root);
	}

	public void process() {
		// clean any previous invocation
		this.files.clear();

		// process templates
		this.templateProcessor.process();
		
		// build the site model
		siteModel.put("siteTitle", this.project.getName());
		
		if(this.project.getProperties() != null && this.project.getProperties().size() > 0) {
			for(Property property : this.project.getProperties()) {
				siteModel.put(property.getName(), property.getValue());
			}
		}
		
		if(!siteModel.containsKey(SITE_DEBUG)) {
			siteModel.put(SITE_DEBUG, false);
		}
		
		// process pages
		List<ProjectFile> projectFiles = this.pageProcessor.process(this.templateProcessor, this.siteModel);
		this.files.addAll(projectFiles);
		
		// process assets
		projectFiles = this.assetsProcessor.process();
		this.files.addAll(projectFiles);
	}

	public List<ProjectFile> getProjectFiles() {
		return Collections.unmodifiableList(files);
	}

}
