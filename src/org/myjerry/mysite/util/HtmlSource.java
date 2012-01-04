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

package org.myjerry.mysite.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.SourceFormatter;

import org.myjerry.mysite.model.ProjectFile;

/**
 * 
 * @author sangupta
 * @since 2 Jan 2012
 */
public class HtmlSource {
	
	static {
		MicrosoftConditionalCommentTagTypes.register();
	}
	
	private ProjectFile projectFile;
	
	private Source source;
	
	public HtmlSource(ProjectFile file) {
		this.projectFile = file;
		
		String contents = this.projectFile.getContents();
		source = new Source(contents);
	}

	public void tidyHtml() {
		try {
			StringWriter writer = new StringWriter();
			new SourceFormatter(source).setIndentString("  ").setTidyTags(true).writeTo(writer);
			writer.close();
			
			this.projectFile.setContents(writer.toString());
		} catch(Exception e) {
			System.out.println("Unable to prettify HTML for file: " + this.projectFile.getPath());
		}
	}

	public List<String> extractAllHyperLinks() {
		List<String> links = new ArrayList<String>();
		
		List<Element> elements = source.getAllElements(HTMLElementName.A);
		if(elements != null && elements.size() > 0) {
			for(Element element : elements) {
				String href = element.getAttributeValue("href");
				links.add(href);
			}
		}
		
		return links;
	}

}
