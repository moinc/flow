# introduction
Flow is an infrastructure orchestration and provisioning tool. 
This tool is meant as an alternative to Puppet, Salt or Chef.

# how to use
- Clone the project
- Cd into the project's root directory
- Use <code>$ gradle build</code> to build
- Run the StarterProject using the command: <code>$ java -jar build/1.0/cmp/libs/agiletech-flow-cmp-1.0.jar compile -p build/1.0/StarterProject/libs/StarterProject.jar -r INSPECT -n StarterProject/webserver2-nodedata.json -c StarterProject/flowconfig.json</code>
- Enjoy the output

# eclipse
- Use <code>$ gradle eclipse</code> to generate eclipse files

# license
This project is currently offered under the GNU AGPL 3 license.
