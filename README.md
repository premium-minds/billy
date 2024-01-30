# Billy

Billy is a billing library for applications providing them with ability to create, manage,  and store billing artifacts such as invoices and credit notes. It also supports the export of the persisted the persisted data in PDF and SAF-T formats.

Billy was built to be easily extended in order to support additional taxing systems for different countries.
Billy's core module was based on the OECD's SAF-T schema, providing a standard module from which one can extend and further develop to support new modules for different countries.

The objective is for all modules to be compliant with the existing regulations for their country. However, we do not in any way intend to provide a certified library, it should be the responsibility of applications that use billy to seek certification for themselves.


## Maven projects
![Maven Central](https://img.shields.io/maven-central/v/com.premiumminds/billy-portugal)

Add the following maven dependency to your project `pom.xml`:

```xml
<dependency>
   <groupId>com.premiumminds</groupId>
   <artifactId>billy-portugal</artifactId>
   <version>9.4.0</version>
</dependency>
```
Check out [sonatype repository](https://oss.sonatype.org/index.html#nexus-search;quick~billy) for latest snapshots and releases.


## Continuous Integration

[![Java CI with Maven](https://github.com/premium-minds/billy/actions/workflows/maven.yml/badge.svg)](https://github.com/premium-minds/billy/actions/workflows/maven.yml)

## Javadoc

| Module               | Javadoc |
|----------------------|---------|
| billy-core           | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-core/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-core) |
| billy-core-jpa       | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-core-jpa/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-core-jpa) |
| billy-gin            | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-gin/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-gin) |
| billy-portugal       | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-portugal/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-portugal) |
| billy-spain          | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-spain/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-spain) |
| billy-france         | [![javadoc](https://javadoc.io/badge2/com.premiumminds/billy-france/javadoc.svg)](https://javadoc.io/doc/com.premiumminds/billy-france) |

## Licence
![GitHub](https://img.shields.io/github/license/premium-minds/billy)

Copyright (C) 2013 [Premium Minds](http://www.premium-minds.com/)

Licensed under the [GNU Lesser General Public Licence](http://www.gnu.org/licenses/lgpl.html)

## Demo

There is a demonstration project on how to use billy in [premium-minds/billy-demo-app](https://github.com/premium-minds/billy-demo-app)
