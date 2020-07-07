# Dependencies
A library for ranked universal dependencies

## Overview

In the scripts directory, you will find 1 SQL script:

- dependencies.sql

And a sample database.properties file:

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
