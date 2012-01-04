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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.myjerry.mysite.model.Page;
import org.myjerry.mysite.model.ProjectFile;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class PageProcessor {
	
	private List<Page> pages;
	
	private final File parentFolder;
	
	private final List<ProjectFile> files = new ArrayList<ProjectFile>();

	public PageProcessor(List<Page> pages, File parentFolder) {
		this.pages = pages;
		this.parentFolder = parentFolder;
	}

	public List<ProjectFile> process(TemplateProcessor templateProcessor, VelocityContext siteModel) {
		if(pages == null || pages.size() == 0) {
			System.out.println("No pages found to be processed... exiting!");
			return null;
		}

		// process each page one by one
		for(Page page : this.pages) {
			ProjectFile file = buildPage(page, templateProcessor, siteModel);
			this.files.add(file);
		}
		
		return this.files;
	}

	private ProjectFile buildPage(Page page, TemplateProcessor templateProcessor, VelocityContext siteModel) {
		// get the page contents
		String pageContents = null;
		File file = new File(this.parentFolder, page.getFile());
		try {
			pageContents = FileUtils.readFileToString(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// build the page model
		VelocityContext pageModel = new VelocityContext(siteModel);
		pageModel.put("pageTitle", page.getTitle());
		pageModel.put("pageBody", pageContents);
		
		// process with template
		Template template = templateProcessor.getTemplate(page.getTemplateID());
		
		StringWriter writer = new StringWriter();
		template.merge(pageModel, writer);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// build the page object
		return new ProjectFile(page.getPath(), writer.toString()); 
	}

}
