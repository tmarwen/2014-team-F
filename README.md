Codefest 2014 team F repository
===========

# The team F

TEAM MEMBER | EXO TEAM
------------ | ------------- 
Ahmed Zaoui | support
Marwen Trabelsi | support
Malek Ben Salem | support
Meriem Zoraî | Support

# How to build

	git clone git@github.com:exo-codefest/2014-team-F.git
	cd 2014-team-F
	mvn clean install

# How to use:
--

**Note:** The Task Management Portlet is intended to use the Spaces as Projects, so there will be no view for project and our main idead was
to use the Spaces embedded in eXo Platform to be our Project scope.
When creating a task, the user has either the choice to create a personal one which will be assigned to him, or create a Project task then
assign it to some member of the dedicated space.

1. Add Some users and some spaces, make some of them join the spaces (Just for tests).
2. From Application Management Page, add the "Task Management Application" to a category of import applications.
3. Add a page and drop the portlet in.
4. Try adding some tasks and displaying them.