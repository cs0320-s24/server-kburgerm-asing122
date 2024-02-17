> **GETTING STARTED:** You must start from some combination of the CSV Sprint code that you and your partner ended up with. Please move your code directly into this repository so that the `pom.xml`, `/src` folder, etc, are all at this base directory.

> **IMPORTANT NOTE**: In order to run the server, run `mvn package` in your terminal then `./run` (using Git Bash for Windows users). This will be the same as the first Sprint. Take notice when transferring this run sprint to your Sprint 2 implementation that the path of your Server class matches the path specified in the run script. Currently, it is set to execute Server at `edu/brown/cs/student/main/server/Server`. Running through terminal will save a lot of computer resources (IntelliJ is pretty intensive!) in future sprints.

# Project Details

Our project is an API made up of a few packages. Our server package comprises of the main server class, which delegates different endpoints to different handler classes, including the Load, View, Search, and Broadband handlers. Each of these represents a different request endpoint, "loadcsv", "viewcsv", "searchcsv", and "broadband", respectively. The load, view, and search functions work together, and only the currently loaded csv file can be viewed or searched. Broadband allows the user to look up the percentage estimate of High-speed internet access for any given county and state. Additionally, the broadband endpoint uses a cache that stores data for quick lookup of previously fetched data. The cache can be configured with different settings using different CacheConfig classes. Instructions on how to use these endpoints are located in the "How to" section of this file.

# Design Choices

We chose to use the CSVParser from csv to assist in the handling of csv files between the load, search, and view classes. Each of these classes are dependency-injected with a CSVfile wrapper class which ensures that all classes are referring to the same loaded file at one time.

We also separated our Cache into two packages, one that holds the functionality of the broadband cache and another package involving the setup for the cache. This allows developers to use the generic CacheConfig interface to set up the interface according to their liking.

# Errors/Bugs

There are no known bugs of the program. When users use the endpoints, different error messages apper in certain instances. "error_bad_json" appears ,if the request was ill-formed, "error_bad_request" if a field was missing from a request or the field was ill-formed, and 
"error_bad_request" if the request was missing a needed field, or the field was ill-formed, and "error_datasource" if the given data source wasn't accessible.

# Tests

There is an extensive test suite for this program. Th test suite tests the parsing, all handler classes, and makes sure that errors are thrown when expected and all outputs are correct.

# How to
To use each endpoint:
loadcsv "localhost:3232/loadcsv?filepath=<filepath goes here>"
viewcsv "localhost:3232/viewcsv"
searchcsv "localhost:3232/searchcsv?target=<search target>&hasHeader=<"true" if yes>&column=<(optional) column name or int index>"
broadband "localhost:3232/broadband?state=<state name>&county=<county name>"

