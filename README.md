RAML Tools library
==================

This library wraps `raml-parser-2` to make it more easily used in HMRC projects.

Code Coverage
-------------

To get coverage, run:

```sbt clean coverage test``` in order to generate the coverage stats.

```sbt coverageReport``` will then generate the report in the `target/scala-2.12/scoverage-report` directory.

Libraries Used
--------------

RAML
    https://github.com/raml-org/raml-java-parser

## Installing

Include the following dependency in your SBT build

``` scala
libraryDependencies += "uk.gov.hmrc" %% "raml-tools" % "[INSERT-VERSION]"
```

License
=======

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
