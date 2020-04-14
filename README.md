[![Build Status](https://travis-ci.com/mlesniak/markdown-java.svg?branch=master)](https://travis-ci.com/mlesniak/markdown-java)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mlesniak_markdown-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=mlesniak_markdown-java)

# TODO

- [ ] Store git version on build and define endpoint
- [ ] Log to JSON file / one entry per line including exceptions
- [ ] Monitor uptime of the server and alert in case of problems (using free plans)
- [ ] Create Dockerfile
- [ ] Clean up README
- [ ] Configurable names for directories, e.g. static
- [ ] Log performance metrics for evaluation
- [ ] favicon.ico logic will not work as implemented
- [X] Add TravisCI build system
- [X] Fix all sonarqube issues
- [X] Global exception handler
- [X] Caching
- [X] Log to Sematext (https://apps.eu.sematext.com/)
- [X] Add Sonarqube code analysis (mvn verify sonar:sonar)
- [X] Add Sonarqube icon to GitHub page
- [X] Configure hard-core quality gate on sonarcloud
- [X] Push to GitHub
- [X] Refactor WebController
- [X] Add request uuid in mdc using custom filter
