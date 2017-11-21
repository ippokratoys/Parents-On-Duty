# Parents-On-Duty
Project for Software Technology (Texnologies Logismikou 2017)
( Spring boot framework )

A web apllication written on Java.
	- Database Language MySql
	- Elastic search for searching and Distance Queries
	- Backend Languge Java with Spirng ( Spring Boot, Spring Security)
	- Template Engine Thymeleaf
	- Front End used HTML5, Bootstrap, JQuery
		-Google Map API
		-Google Map Autocomplete
	- Building With Gradle

Status : Full Functional
Basic configuration (sql,port,https...) on application.properties file
Run: Go to /parentsReal folder via terminal, enter:

	- ./gradlew tasks
	- ./gradlew bootRun
Alternative
	
	- create a war ./gradlew war
	- and then run on a server

Needs a database with name test3(you can change it from application.properties)

	- Support Register(Parent,Host)
	- Admin control over users(block/unblock, reset password ...)
	- Free text search on certain fields
	- Google map for places (and result page)
	- Create event
	- Distance queries
	- ...
