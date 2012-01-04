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

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
@XStreamAlias("project")
public class Project {
	
	private String name;
	
	@XStreamAsAttribute
	private String templateRoot;
	
	@XStreamAsAttribute
	private String pagesRoot;
	
	@XStreamAsAttribute
	private String assetsRoot;
	
	private List<Property> properties;

	private List<Template> templates;
	
	private List<Asset> assets;
	
	private List<Page> pages;
	
	public void addTemplate(Template template) {
		if(this.templates == null) {
			this.templates = new ArrayList<Template>();
		}
		
		this.templates.add(template);
	}
	
	public void addProperty(Property property) {
		if(this.properties == null) {
			this.properties = new ArrayList<Property>();
		}
		
		this.properties.add(property);
	}
	
	public void addPage(Page page) {
		if(this.pages == null) {
			this.pages = new ArrayList<Page>();
		}
		
		this.pages.add(page);
	}
	
	public void addAsset(Asset asset) {
		if(this.assets == null) {
			this.assets = new ArrayList<Asset>();
		}
		
		this.assets.add(asset);
	}
	
	// Usual accessor's follow

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public String getTemplateRoot() {
		return templateRoot;
	}

	public void setTemplateRoot(String templateRoot) {
		this.templateRoot = templateRoot;
	}

	public String getPagesRoot() {
		return pagesRoot;
	}

	public void setPagesRoot(String pagesRoot) {
		this.pagesRoot = pagesRoot;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public String getAssetsRoot() {
		return assetsRoot;
	}

	public void setAssetsRoot(String assetsRoot) {
		this.assetsRoot = assetsRoot;
	}

}
