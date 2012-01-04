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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.myjerry.mysite.model.Template;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class TemplateProcessor {
	
	private final VelocityEngine engine = new VelocityEngine();

	private List<Template> templates;
	
	private final Map<String, org.apache.velocity.Template> cache = new HashMap<String, org.apache.velocity.Template>();
	
	public TemplateProcessor(List<Template> templates, File root) {
		this.templates = templates;

		// initialize the velocity engine
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
		properties.setProperty("file" + VelocityEngine.RESOURCE_LOADER + ".class", FileResourceLoader.class.getName());
		
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, root.getAbsolutePath());
		
		engine.init(properties);
	}

	public void process() {
		// read all templates
		if(this.templates == null || this.templates.size() == 0) {
			System.out.println("No template has been defined... exiting!");
			return;
		}
		
		// initialize velocity engine
		Velocity.init();
		
		for(Template template : this.templates) {
			processTemplate(template, engine);
		}

	}

	private void processTemplate(Template model, VelocityEngine engine) {
		org.apache.velocity.Template template = engine.getTemplate(model.getFile());
		this.cache.put(model.getId(), template);
	}

	public org.apache.velocity.Template getTemplate(String id) {
		return cache.get(id);
	}
}
