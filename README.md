# Billy
Billy is a billing library for applications providing them with ability to create, manage,  and store billing artifacts such as invoices and credit notes. It also supports the export of the persisted the persisted data in PDF and SAF-T formats.

Billy was built to be easily extended in order to support additional taxing systems for different countries. 
Billy's core module was based on the OECD's SAF-T schema, providing a standard module from which one can extend and further develop to support new modules for different countries.

The objective is for all modules to be compliant with the existing regulations for their country. However, we do not in any way intend to provide a certified library, it should be the responsibility of applications that use billy to seek certification for themselves.


## Maven projects
Add the following maven dependency to your project `pom.xml`:

```xml
<dependency>
   <groupId>com.premiumminds</groupId>
   <artifactId>billy-portugal</artifactId>
   <version>1.0b</version>
</dependency>
```
Check out [sonatype repository](https://oss.sonatype.org/index.html#nexus-search;quick~billy) for latest snapshots and releases.


##Continuous Integration
[![Build Status](https://travis-ci.org/premium-minds/billy.png?branch=master)](https://travis-ci.org/premium-minds/billy)

CI is hosted by [travis-ci.org](https://travis-ci.org/)

##Licence
Copyright (C) 2013 [Premium Minds](http://www.premium-minds.com/)

Licensed under the [GNU Lesser General Public Licence](http://www.gnu.org/licenses/lgpl.html)
