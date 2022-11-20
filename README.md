[![Java CI with Maven](https://github.com/DKharchenko25/OnlineShop_SpringData_SpringWeb_SpringSecurity_JSP/actions/workflows/maven.yml/badge.svg)](https://github.com/DKharchenko25/OnlineShop_SpringData_SpringWeb_SpringSecurity_JSP/actions/workflows/maven.yml)
# Online Shop

### Path: ```localhost:8081```

### Used technologies:
 - Java 8;
 - Spring (Web, Data, Security);
 - JSP;

### Test logins and passwords for access:

1. Admin account:

- login: ```admin```;
- password: ```0000```;

2. User account:

- login: ```customer```;
- password: ```1111```;

### Instructions for Docker:

1. Build project with mvn ```mvn verify``` command;
2. Run docker compose with ```docker-compose up``` command;
3. Insert initial data to postgres database:
- open terminal in postgres container and execute ```psql --host=database --username=postgres```
- insert password ```1111```
- copy and execute sql queries ```INSERT INTO role (id, name) VALUES (DEFAULT, 'ROLE_CUSTOMER');``` and ```INSERT INTO role (id, name) VALUES (DEFAULT, 'ROLE_ADMIN');```
4. Open browser and use application with http://localhost:8081;
5. Register administrator with username that contains word ```admin``` and that's it.