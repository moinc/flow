package nl.agiletech.flow.examples.starter.files;

import nl.agiletech.flow.examples.starter.components.TomcatComponent;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.StaticFile;
import nl.agiletech.flow.project.types.Template;

@Flow
public class MyAppWarFile extends StaticFile {
	public Component[] components = new Component[] { new TomcatComponent() };
	public Template source = Template.inline("test.txt");
	public Template destination = Template.inline("/usr/local/tomcat/wars/test.txt");

	@Override
	public String getSource() {
		return source.toString();
	}

	@Override
	public String getDestination() {
		return destination.toString();
	}

	@Override
	public String getOwner() {
		return "tomcat";
	}

	@Override
	public String getGroup() {
		return "tomcat";
	}

	@Override
	public String getMode() {
		return "0755";
	}
}