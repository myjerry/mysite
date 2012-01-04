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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.myjerry.mysite.model.Project;
import org.myjerry.mysite.model.ProjectFile;
import org.myjerry.mysite.util.HtmlSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class MySite {
	
	private static final XStream XSTREAM;
	
	static {
		XSTREAM = new XStream(new DomDriver());
		XSTREAM.autodetectAnnotations(true);
		XSTREAM.alias("project", Project.class);
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			usage();
		}
		
		final long startTime = System.currentTimeMillis();
		
		String projectFile = args[0];
		String exportFolder = args[1];
		
		createSiteFromProjectFile(projectFile, exportFolder);
		
		final long endTime = System.currentTimeMillis();
		
		System.out.println("Site created in " + (endTime - startTime) + " ms.");
	}
	
	private static void createSiteFromProjectFile(final String projectFile, final String exportFolder) throws IOException {
		File file = new File(projectFile);
		file = file.getAbsoluteFile();
		if(!file.exists()) {
			System.out.println("File does not exists.");
			return;
		}
		
		if(file.isDirectory()) {
			System.out.println("Given file is a directory.");
			return;
		}
		
		String xml = FileUtils.readFileToString(file);
		
		Project project = (Project) XSTREAM.fromXML(xml);
		
		ProjectValidator projectValidator = new ProjectValidator(project);
		boolean success = projectValidator.validate();
		if(!success) {
			System.out.println("Project XML has validation errors, see below for details: ");
			System.out.println(projectValidator.getValidationErrors());
		}
		
		File parentFolder = new File(file.getParent());
		processProject(project, parentFolder, exportFolder);
	}

	private static void processProject(Project project, File parentFolder, final String exportFolder) throws IOException {
		// build up the project processor
		ProjectProcessor processor = new ProjectProcessor(project, parentFolder);
		processor.process();
		
		List<ProjectFile> files = processor.getProjectFiles();
		if(files == null || files.size() == 0) {
			System.out.println("No files found to be written in project... exiting!");
			return;
		}
		
		// build up the list of all files that are being written to disk
		List<String> filesWritten = new ArrayList<String>();
		for(ProjectFile file : files) {
			filesWritten.add(file.getPath());
		}
		
		// start the export process
		File export = new File(exportFolder);
		if(!export.exists()) {
			export.mkdirs();
		} else {
			System.out.println("Export directory already exists... cleaning it up...");
			FileUtils.cleanDirectory(export);
			System.out.println("Done cleaning!");
		}
		
		Set<String> hyperLinks = new HashSet<String>();
		for(ProjectFile file : files) {
			if(file.isHTML()) {
				HtmlSource source = new HtmlSource(file);
				List<String> links = source.extractAllHyperLinks();
				
				// check if the link exists in the exported code
				for(String link : links) {
					String linkAsPath = getLinkAsPath(link);
					if(linkAsPath != null) {
						if(!filesWritten.contains(linkAsPath)) {
							System.out.println("File " + file.getPath() + " contains an invalid hyperlink: " + link);
						}
					}
				}

				// add to the main list for export
				hyperLinks.addAll(links);
				
				// tidy the code
				source.tidyHtml();
			}
			
			// write the file to disk
			file.writeToDisk(export);
		}
		
		// check for all hyperlinks in the files
//		for(String link : hyperLinks) {
//			System.out.println(link);
//		}
	}

	private static String getLinkAsPath(String link) {
		if(link == null) {
			return null;
		}
		
		// remove fragments
		if(link.startsWith("#")) {
			return null;
		}
		
		String linkLower = link.toLowerCase();
		
		// absolute urls
		if(linkLower.startsWith("http://") || linkLower.startsWith("https://") || linkLower.startsWith("mailto:") || linkLower.startsWith("javascript:")) {
			return null;
		}
		
		// remove fragment
		int fragment = link.indexOf('#');
		if(fragment != -1) {
			link = link.substring(0, fragment);
		}
		
		return link;
	}

	private static void usage() {
		System.out.println("$ java -jar mysite <project.xml> <output dir>");
		System.exit(0);
	}

}
