# Parents-On-Duty
Project for Texnologies Logismikou
( Spring boot framework )

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
