<!-- https://keepachangelog.com/en/1.0.0/ -->
# Changelog

## [Unreleased v5]

### Added 

 - [Adds XML validation against XSD file](https://github.com/premium-minds/billy/pull/133)
 
### Changed 

 - [Bump hibernate.version from 5.4.23.Final to 5.4.24.Final](https://github.com/premium-minds/billy/pull/125)
 - [Changes log level to debug for truncated fields in SAFT and QR codes](https://github.com/premium-minds/billy/pull/135)
 - [Change constant used in string lengths to already available Math scale](https://github.com/premium-minds/billy/pull/130)
 - [Sorts invoices entries by entrynumber to export in monotonic order in SAFT](https://github.com/premium-minds/billy/pull/129)
 - [Corrects PTSAFTFileGenerator loggers to use its own](https://github.com/premium-minds/billy/pull/127)
 - [Bump mockito-core from 3.6.0 to 3.6.28](https://github.com/premium-minds/billy/pull/137)
 - [Changes saft.xml export from WINDOWS-1252 to UTF-8](https://github.com/premium-minds/billy/pull/128)
 - [Removes rounding of credit and debit amounts in invoices lines](https://github.com/premium-minds/billy/pull/132)
 - [Exports only the clients and products relevant to the requested period](https://github.com/premium-minds/billy/pull/134)
 - [Removes hardcoded temporary file for SAFT export](https://github.com/premium-minds/billy/pull/124) (fixes [Concurrency bug in saft generation](https://github.com/premium-minds/billy/issues/7))
 
### Fixed

- [Corrects the hash verification field](https://github.com/premium-minds/billy/pull/126)

## [Unreleased v4]

## [Unreleased v3]

### Changed

 - [Adds export SAFT methods without certificate number](https://github.com/premium-minds/billy/pull/108)
 - [Replaces Base64 from commons-codec with Base64 from Java 8](https://github.com/premium-minds/billy/pull/111)

## [5.2.0] - 2020-11-05

### Changed

 - [Bump to Junit 5 and Mockito 3.6.0](https://github.com/premium-minds/billy/pull/122)
 - [Replace old and abandoned bouncycastle bcprov-jdk16](https://github.com/premium-minds/billy/pull/123)
 
## [5.1.0] - 2020-11-04

### Added 

 - [Adds support for pt mandatory QRCode generation](https://github.com/premium-minds/billy/pull/120) - It may require changes to XSL template files to include QR code section

### Fixed

 - [Fixes wrong PT NIF validation](https://github.com/premium-minds/billy/pull/117)
 - [Adds custom method matcher to filter synthetic methods](https://github.com/premium-minds/billy/pull/107)
 
### Changed

 - [Bump querydsl.version from 4.3.1 to 4.4.0](https://github.com/premium-minds/billy/pull/116)
 - [Bump tiles-maven-plugin from 2.17 to 2.18](https://github.com/premium-minds/billy/pull/118)
 - [[Security] Bump junit from 4.13 to 4.13.1](https://github.com/premium-minds/billy/pull/119)
 - [Bump hibernate.version from 5.4.20.Final to 5.4.23.Final](https://github.com/premium-minds/billy/pull/121)

## [5.0.0] - 2020-09-18

### Changed

 - [Update persistence dependencies](https://github.com/premium-minds/billy/pull/109)
 - [Removes unused dependency guice-assistedinject](https://github.com/premium-minds/billy/pull/113)
 - [Removes Joda-time dependency and uses Java 8 LocalDate](https://github.com/premium-minds/billy/pull/112)
 - [Bump snakeyaml from 1.26 to 1.27](https://github.com/premium-minds/billy/pull/115)

## [4.0.0] - 2020-09-03

### Changed

 - [Change entities ids from integer to long. Use identity strategy for id generation](https://github.com/premium-minds/billy/pull/103)
 - [Adds export SAFT methods without certificate number](https://github.com/premium-minds/billy/pull/108)
 - [Replaces Base64 from commons-codec with Base64 from Java 8](https://github.com/premium-minds/billy/pull/111)

## [3.3.1] - 2020-08-14

### Changed

 - [Bump commons-lang3 from 3.10 to 3.11](https://github.com/premium-minds/billy/pull/105)

### Fixed

 - [Replaced System.err calls with log SLf4j log call](https://github.com/premium-minds/billy/pull/104)
 - [Pt credit note data extractor reference](https://github.com/premium-minds/billy/pull/106)

## [3.3.0] - 2020-07-11

### Changed

 - [Bump snakeyaml from 1.25 to 1.26](https://github.com/premium-minds/billy/pull/91)
 - [Bump build-helper-maven-plugin from 3.0.0 to 3.1.0](https://github.com/premium-minds/billy/pull/92)
 - [Bump maven-javadoc-plugin from 3.1.1 to 3.2.0](https://github.com/premium-minds/billy/pull/93)
 - [Bump guice.version from 4.2.2 to 4.2.3](https://github.com/premium-minds/billy/pull/94)
 - [Bump commons-lang3 from 3.9 to 3.10](https://github.com/premium-minds/billy/pull/95)
 - [Bump joda-time from 2.10.5 to 2.10.6](https://github.com/premium-minds/billy/pull/96)
 - [Bump tiles-maven-plugin from 2.16 to 2.17](https://github.com/premium-minds/billy/pull/99)
 - [Bump build-helper-maven-plugin from 3.1.0 to 3.2.0](https://github.com/premium-minds/billy/pull/100)
 - [Update Canary islands 2020 IGIC normal tax value](https://github.com/premium-minds/billy/pull/90)
 
### Fixed

 - [Credit note issues](https://github.com/premium-minds/billy/pull/101)
 - [Corrects behavior for tax selection for a given context](https://github.com/premium-minds/billy/pull/102)
 
## [3.2.1] - 2020-02-24

### Added
 - [Adds support for NS/NA taxed items](https://github.com/premium-minds/billy/pull/89)

## [3.2.0] - 2020-01-31

### Added 
 - [Adds exemption reason and code to InvoiceEntryData](https://github.com/premium-minds/billy/pull/85)
 - [Adds TaxRateType.NONE to the types that use percent as a unit](https://github.com/premium-minds/billy/pull/87)
 - [Adds Support for Receipt Invoices to Billy Portugal](https://github.com/premium-minds/billy/pull/88)

### Changed
 - [Bump mockito-all from 1.9.5 to 1.10.19](https://github.com/premium-minds/billy/pull/37) and set scope to test
 - Bump [maven-source-plugin](https://github.com/apache/maven-source-plugin) from 2.2.1 to 3.2.1
 - [Bump validation-api from 1.1.0.Final to 2.0.1.Final](https://github.com/premium-minds/billy/pull/36)
 - [Bump tiles-maven-plugin from 2.8 to 2.16](https://github.com/premium-minds/billy/pull/40)
 - Bump [Guice](https://github.com/google/guice) to version 4.2.2
 - Bump [snakeyaml](https://bitbucket.org/asomov/snakeyaml/src/default/) to version 1.25 and set scope to test
 - Bump [Junit](https://junit.org/junit4/) to version 4.13
 - Bump [querydsl](https://github.com/querydsl/querydsl) to version 3.7.4
 - Bump [build-helper-maven-plugin](https://www.mojohaus.org/build-helper-maven-plugin/) to 3.0.0
 - Replaced maven-apt-plugin with [apt-maven-plugin](https://github.com/querydsl/apt-maven-plugin), version 1.1.3
 - [Bump nexus-staging-maven-plugin from 1.6.2 to 1.6.8](https://github.com/premium-minds/billy/pull/49)
 - [Bump maven-jar-plugin from 2.4 to 3.2.0](https://github.com/premium-minds/billy/pull/46)
 - [Bump maven-gpg-plugin from 1.5 to 1.6](https://github.com/premium-minds/billy/pull/48)
 - Bump [commons-codec](https://commons.apache.org/proper/commons-codec/) from 1.7 to 1.14
 - [Bump maven-javadoc-plugin from 2.9.1 to 3.1.1](https://github.com/premium-minds/billy/pull/53)
 - [Bump maven-scm-provider-gitexe from 1.8.1 to 1.11.2](https://github.com/premium-minds/billy/pull/51)
 - [Bump jaxb2-basics-annotate from 0.6.2 to 1.1.0](https://github.com/premium-minds/billy/pull/60)
 - [Bump commons-lang3 from 3.1 to 3.9](https://github.com/premium-minds/billy/pull/59)
 - [Bump jaxb2-basics-runtime from 0.6.4 to 0.12.0](https://github.com/premium-minds/billy/pull/58)
 - [Bump slf4j-api from 1.7.29 to 1.7.30](https://github.com/premium-minds/billy/pull/70)
 - [Upgrade to java 8](https://github.com/premium-minds/billy/pull/34)
 - [Bump hibernate-validator to version 5.4.3.FINAL](https://github.com/premium-minds/billy/pull/62)
 - [Bump hibernate-entitymanager to version 4.2.21.Final](https://github.com/premium-minds/billy/pull/63)
 - Replace maven-license-plugin with [license-maven-plugin](http://mycila.mathieu.photography/license-maven-plugin/), version 3.0
 - [Bump maven-release-plugin from 2.4.2 to 2.5.3](https://github.com/premium-minds/billy/pull/68)
 - [Bump javax.el-api from 2.2.4 to 3.0.0](https://github.com/premium-minds/billy/pull/67)
 - [Bump javax.el from 2.2.4 to 2.2.6](https://github.com/premium-minds/billy/pull/66)
 - [Bump maven-surefire-plugin from 2.20.1 to 2.22.2](https://github.com/premium-minds/billy/pull/74)
 - Bump [Joda-Time](https://www.joda.org/joda-time/) to 2.10.5
 - [Removed mandatory end date to taxes](https://github.com/premium-minds/billy/pull/69)
 - Replaced hibernate-jpa-2.0-api with hibernate-jpa-2.1-api
 - [Bump jaxb2-basics from 0.6.2 to 0.12.0](https://github.com/premium-minds/billy/pull/76)
 - [Bump ebean from 10.3.1 to 10.4.7](https://github.com/premium-minds/billy/pull/79)
 - [ Clarify isSubContext in DAOContext](https://github.com/premium-minds/billy/pull/86)
 
### Removed
 - [Removed log4j dependency](https://github.com/premium-minds/billy/pull/55)
 
## [3.1.2] - 2019-12-16
### Changed
 -  [Removed tax values validation](https://github.com/premium-minds/billy/pull/32)
 -  [Update Canary islands 2019 IGIC normal tax value](https://github.com/premium-minds/billy/pull/33)
 
 
[unreleased v5]: https://github.com/premium-minds/billy/compare/v5.2.0...HEAD
[unreleased v4]: https://github.com/premium-minds/billy/compare/v4.0.0...release-4.x
[unreleased v3]: https://github.com/premium-minds/billy/compare/v3.3.1...release-3.x
[5.2.0]: https://github.com/premium-minds/billy/compare/v5.1.0...v5.2.0
[5.1.0]: https://github.com/premium-minds/billy/compare/v5.0.0...v5.1.0
[5.0.0]: https://github.com/premium-minds/billy/compare/release-4.x...v5.0.0
[4.0.0]: https://github.com/premium-minds/billy/compare/release-3.x...v4.0.0
[3.3.1]: https://github.com/premium-minds/billy/compare/v3.3.0...v3.3.1
[3.3.0]: https://github.com/premium-minds/billy/compare/v3.2.1...v3.3.0
[3.2.1]: https://github.com/premium-minds/billy/compare/v3.2.0...v3.2.1
[3.2.0]: https://github.com/premium-minds/billy/compare/v3.1.2...v3.2.0
[3.1.2]: https://github.com/premium-minds/billy/compare/v3.1.1...v3.1.2
