# Dependencies
A library for ranked universal dependencies

This library includes scripts to load linguistic descriptions in UD and 
SYS files and analyzed texts in CONLLU files into a MySQL base. It also
includes scripts to translate a text analysis from a linguistic description
into another and to check whether a text analysis is complete.

## Overview

In the scripts directory, you will find 1 SQL script:

- dependencies.sql

And a sample PROPERTIES file:

- database.properties

And you will find 5 JAR scripts:

- import-ud-file.jar
- import-sys-file.jar
- import-conllu-file.jar
- translate-analysis.jar
- check-analysis.jar

In the following, you will learn how to create a database with the `dependencies` schema,
how to use the scripts to import linguistic descriptions, text analyses, how to translate
text analyses from a description to another and how to verify if an analysis is complete.

## Dependency Base

A `dependency base` is a MySQL base with the `dependencies` schema provided in the `dependencies.sql` file.
You can store multiple dependency bases in the same machine as long as you give each one of them a different
name. In this section, we will call our dependency base `my-dependencies` for simplicity.

First you must install a service and an application in your machine.

- Install MySQL Service
- Install MySQL Workbench 

When installing MySQL Service, you will be asked to provide a password. Keep that password. You will need it. Once you install MySQL Service and activate it, you will have turned your machine into a MySQL Server. Now you can create MySQL bases in it where you can store actual data.

To create a dependency base, open the application MySQL Workbench and create a connection between the application and the MySQL server. Give it a name such as 'Root at this machine'. The MySQL server (the host of the MySQL Service) is your machine itself. For this reason, you can refer to it by the IP number `127.0.0.1`. The `port` is the number of the service running on your machine. MySQL has number `3306` unless you manually specify another number for it. When you installed MySQL Service, a user named `root` was automatically created so you can identify yourself as `root` and you should provide the password you created here. Test your connection by clicking the 'Test Connection' button. If it is working, click the 'Ok' button to create the connection and click on the connection panel to open it. 

![Connection Screenshot](README/Connection.png "Connection Screenshot")

Once you open a connection with a MySQL Server, you can import data. Specify that you want to import data from a Self-Contained File and specify the path to the `dependencies.sql` file in your machine. Click on the 'New' button to create a new MySQL base with the name you specify. We chose the name `my-dependencies` for our MySQL base. Select `Dump Structure Only` at the option menu. And click on the 'Start Import' button. This will import the `dependencies` schema into your newly created MySQL base.

![Data Import Screenshot](README/DataImport.png "Data Import Screenshot")

Now you have to re-write the database.properties file. Make sure you specify the MySQL base name, the user name, and the password properly. Here is an example of the content:

```properties
  DependencyBase.name=my-dependencies
  DependencyBase.user=root
  DependencyBase.password=123456789
```

Finally, you will have to download the MySQL driver for JAVA, a JAR file that needs to be in the scripts folder for the scripts to work.  I myself tested the scripts with the `mysql-connector-java-5.1.49.jar` file. Both this and newer versions should work fine.

## Linguistic Descriptions



## Text Analyses



