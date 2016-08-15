package nl.agiletech.flow.examples.starter.roles;

import nl.agiletech.flow.examples.starter.files.MyAppWarFile;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.aspects.PermissionsAspect;
import nl.agiletech.flow.project.types.File;
import nl.agiletech.flow.project.types.Role;

@Flow
public class DeploySoftwareRole implements Role {
	public File warFile = new MyAppWarFile();

	public DeploySoftwareRole() {
		warFile.ownership.change("tomcat8", "tomcat8");
		warFile.permissions.change(PermissionsAspect.ALL, PermissionsAspect.ALL, PermissionsAspect.READ_EXECUTE);
	}
}
