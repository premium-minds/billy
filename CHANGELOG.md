<!-- https://keepachangelog.com/en/1.0.0/ -->
# Changelog

## [Unreleased v6]

### Added

 - [Adds validation to PT Customer Country](https://github.com/premium-minds/billy/pull/229) fixes for [Portuguese Customer Details Country must be a valid Country Code](https://github.com/premium-minds/billy/issues/216)
 - [Adds integration test for PT](https://github.com/premium-minds/billy/pull/303)
 - [Adds integration test for Spain and test for ESFinancialValidator ](https://github.com/premium-minds/billy/pull/329)

### Changed

 - [Unsplit packages in billy-core and billy-core-jpa](https://github.com/premium-minds/billy/pull/316)
 - [Refactor KeyGenerator to use URL for private key](https://github.com/premium-minds/billy/pull/315)
 - [Replace custom code for XML construction with standard javax.xml way](https://github.com/premium-minds/billy/pull/310)
 - [Improve stream usage in PTFinancialValidator](https://github.com/premium-minds/billy/pull/306)
 - [Deprecate Ticket concept](https://github.com/premium-minds/billy/pull/307)
 - [Fail if series unique code not filled](https://github.com/premium-minds/billy/pull/301)
 - [Fail when series does not exist](https://github.com/premium-minds/billy/pull/299)
 - [Exposes atcud value to the various pt document exporters](https://github.com/premium-minds/billy/pull/298)
 - [Compiles with Java 17](https://github.com/premium-minds/billy/pull/230) fixes for [does not compile with java 11](https://github.com/premium-minds/billy/issues/203)
 - [Fix indentation, replacing tabs with spaces](https://github.com/premium-minds/billy/pull/141)
 - [Moves from old groupId javax.* to new jakarta.*](https://github.com/premium-minds/billy/pull/147)
 - [Replaces deprecated hibernate-entitymanager with hibernate-core](https://github.com/premium-minds/billy/pull/217)
 - [Run unit tests in parallel and in random order](https://github.com/premium-minds/billy/pull/208)
 - [Improve PDF generation unit tests](https://github.com/premium-minds/billy/pull/309)
 - [Replace assert keyword with Junit assertions](https://github.com/premium-minds/billy/pull/330)
 - [Bump querydsl.version from 4.4.0 to 5.0.0](https://github.com/premium-minds/billy/pull/182)
 - [Bump guice.version from 4.2.3 to 5.0.1](https://github.com/premium-minds/billy/pull/210)
 - [Bump guice.version from 5.0.1 to 5.1.0](https://github.com/premium-minds/billy/pull/257)
 - [Bump hibernate.version from 5.6.0.Final to 5.6.1.Final](https://github.com/premium-minds/billy/pull/218)
 - [Bump hibernate.version from 5.6.1.Final to 5.6.2.Final](https://github.com/premium-minds/billy/pull/234)
 - [Bump hibernate.version from 5.6.2.Final to 5.6.3.Final](https://github.com/premium-minds/billy/pull/245)
 - [Bump hibernate.version from 5.6.3.Final to 5.6.4.Final](https://github.com/premium-minds/billy/pull/252)
 - [Bump hibernate.version from 5.6.4.Final to 5.6.5.Final](https://github.com/premium-minds/billy/pull/258)
 - [Bump hibernate.version from 5.6.5.Final to 5.6.6.Final](https://github.com/premium-minds/billy/pull/273)
 - [Bump hibernate.version from 5.6.6.Final to 5.6.7.Final](https://github.com/premium-minds/billy/pull/274)
 - [Bump hibernate.version from 5.6.7.Final to 5.6.8.Final](https://github.com/premium-minds/billy/pull/280)
 - [Bump hibernate.version from 5.6.8.Final to 5.6.9.Final](https://github.com/premium-minds/billy/pull/290)
 - [Bump hibernate.version from 5.6.9.Final to 5.6.10.Final](https://github.com/premium-minds/billy/pull/313)
 - [Bump hibernate.version from 5.6.10.Final to 5.6.11.Final](https://github.com/premium-minds/billy/pull/327)
 - [Bump fop from 1.0 to 2.7](https://github.com/premium-minds/billy/pull/253)
 - [Bump bcpkix-jdk15on from 1.69 to 1.70](https://github.com/premium-minds/billy/pull/232)
 - [Bump slf4j-api from 1.7.32 to 1.7.33](https://github.com/premium-minds/billy/pull/250)
 - [Bump slf4j-api from 1.7.33 to 1.7.35](https://github.com/premium-minds/billy/pull/256)
 - [Bump slf4j-api from 1.7.35 to 1.7.36](https://github.com/premium-minds/billy/pull/259)
 - [Bump slf4j-api from 1.7.36 to 2.0.0](https://github.com/premium-minds/billy/pull/323)
 - [Bump jaxb2-basics-runtime from 0.12.0 to 0.13.1](https://github.com/premium-minds/billy/pull/287)
 - [Bump core from 3.4.1 to 3.5.0](https://github.com/premium-minds/billy/pull/288)
 - [Bump mockito-core from 4.0.0 to 4.1.0](https://github.com/premium-minds/billy/pull/223)
 - [Bump mockito-core from 4.1.0 to 4.2.0](https://github.com/premium-minds/billy/pull/237)
 - [Bump mockito-core from 4.2.0 to 4.3.1](https://github.com/premium-minds/billy/pull/255)
 - [Bump mockito-core from 4.3.1 to 4.4.0](https://github.com/premium-minds/billy/pull/268)
 - [Bump mockito-core from 4.4.0 to 4.5.1](https://github.com/premium-minds/billy/pull/283)
 - [Bump mockito-core from 4.5.1 to 4.6.0](https://github.com/premium-minds/billy/pull/291)
 - [Bump mockito-core from 4.6.0 to 4.6.1](https://github.com/premium-minds/billy/pull/294)
 - [Bump mockito-core from 4.6.1 to 4.7.0](https://github.com/premium-minds/billy/pull/321)
 - [Bump mockito-core from 4.7.0 to 4.8.0](https://github.com/premium-minds/billy/pull/333)
 - [Bump snakeyaml from 1.29 to 1.30](https://github.com/premium-minds/billy/pull/236)
 - [Bump snakeyaml from 1.30 to 1.31](https://github.com/premium-minds/billy/pull/324)
 - [Bump snakeyaml from 1.31 to 1.32](https://github.com/premium-minds/billy/pull/335)
 - [Bump h2 from 1.3.173 to 1.4.200](https://github.com/premium-minds/billy/pull/241)
 - [Bump h2 from 1.4.200 to 2.1.210](https://github.com/premium-minds/billy/pull/251)
 - [Bump h2 from 2.1.210 to 2.1.212](https://github.com/premium-minds/billy/pull/279)
 - [Bump h2 from 2.1.212 to 2.1.214](https://github.com/premium-minds/billy/pull/296)
 - [Bump pdfbox from 2.0.22 to 2.0.24](https://github.com/premium-minds/billy/pull/312)
 - [Bump pdfbox from 2.0.24 to 2.0.26](https://github.com/premium-minds/billy/pull/314)
 - [Bump tiles-maven-plugin from 2.24 to 2.25](https://github.com/premium-minds/billy/pull/224)
 - [Bump junit-jupiter-engine from 5.8.1 to 5.8.2](https://github.com/premium-minds/billy/pull/231)
 - [Bump junit-jupiter-engine from 5.8.2 to 5.9.0](https://github.com/premium-minds/billy/pull/320)
 - [Bump maven-scm-api from 1.12.0 to 1.12.2](https://github.com/premium-minds/billy/pull/240)
 - [Bump maven-scm-api from 1.12.2 to 1.13.0](https://github.com/premium-minds/billy/pull/293)
 - [Bump maven-scm-provider-gitexe from 1.12.0 to 1.12.2](https://github.com/premium-minds/billy/pull/239)
 - [Bump maven-scm-provider-gitexe from 1.12.2 to 1.13.0](https://github.com/premium-minds/billy/pull/292)
 - [Bump maven-jar-plugin from 3.2.0 to 3.2.1](https://github.com/premium-minds/billy/pull/246)
 - [Bump maven-jar-plugin from 3.2.1 to 3.2.2](https://github.com/premium-minds/billy)
 - [Bump build-helper-maven-plugin from 3.2.0 to 3.3.0](https://github.com/premium-minds/billy/pull/244)
 - [Bump maven-compiler-plugin from 3.8.1 to 3.9.0](https://github.com/premium-minds/billy/pull/249)
 - [Bump maven-compiler-plugin from 3.9.0 to 3.10.0](https://github.com/premium-minds/billy/pull/262)
 - [Bump maven-compiler-plugin from 3.10.0 to 3.10.1](https://github.com/premium-minds/billy/pull/270)
 - [Bump maven-javadoc-plugin from 3.3.1 to 3.3.2](https://github.com/premium-minds/billy/pull/260)
 - [Bump maven-javadoc-plugin from 3.3.2 to 3.4.0](https://github.com/premium-minds/billy/pull/282)
 - [Bump maven-javadoc-plugin from 3.4.0 to 3.4.1](https://github.com/premium-minds/billy/pull/322)
 - [Bump nexus-staging-maven-plugin from 1.6.8 to 1.6.11](https://github.com/premium-minds/billy/pull/263)
 - [Bump nexus-staging-maven-plugin from 1.6.11 to 1.6.12](https://github.com/premium-minds/billy/pull/266)
 - [Bump nexus-staging-maven-plugin from 1.6.12 to 1.6.13](https://github.com/premium-minds/billy/pull/284)
 - [Bump maven-jaxb2-plugin from 0.14.0 to 0.15.1](https://github.com/premium-minds/billy/pull/285)

### Removed

 - [Removes @Deprecated methods in SAFT and export service](https://github.com/premium-minds/billy/pull/206)
 - [Removes unused and unpublished Ebean modules](https://github.com/premium-minds/billy/pull/228)
 - [Removes saxon usage](https://github.com/premium-minds/billy/pull/265)
 - [Remove javax.validation](https://github.com/premium-minds/billy/pull/227)
   - Billy no longer throws `javax.validation.ValidationException`. All exceptions were consolidated into `BillyValidationException`
 - [Removes unused class com.premiumminds.billy.core.util.Util](https://github.com/premium-minds/billy/pull/302)
 - [Remove uidRow from JPABaseEntity](https://github.com/premium-minds/billy/pull/334)

### Fixed

 - [Updated PDF Credit Note/Receipt Description Field](https://github.com/premium-minds/billy/pull/278)
 - [Adds missing hashCode and fixes equals for UID](https://github.com/premium-minds/billy/pull/305)
 - [Compare BigDecimal with compateTo and not equals](https://github.com/premium-minds/billy/pull/304)
 - [Fixes non working getTaxes method in DAO taxes implementations](https://github.com/premium-minds/billy/pull/332)

## [Unreleased v5]

## [5.4.2] - 2022-05-05

### Fixed

 - [Updated PDF Credit Note/Receipt Description Field](https://github.com/premium-minds/billy/pull/278)

## [5.4.1] - 2021-11-16

### Fixed

 - [Remove erroneous tax validation](https://github.com/premium-minds/billy/pull/222)

## [5.4.0] - 2021-10-15

### Changed

 - [Bump hibernate.version from 5.4.24.Final to 5.4.27.Final](https://github.com/premium-minds/billy/pull/142)
 - [Bump hibernate.version from 5.4.27.Final to 5.5.3.Final](https://github.com/premium-minds/billy/pull/178)
 - [Bump hibernate.version from 5.5.3.Final to 5.5.5.Final](https://github.com/premium-minds/billy/pull/185)
 - [Bump hibernate.version from 5.5.5.Final to 5.5.6](https://github.com/premium-minds/billy/pull/186)
 - [Bump hibernate.version from 5.5.6 to 5.5.7.Final](https://github.com/premium-minds/billy/pull/192)
 - [Bump hibernate.version from 5.5.7.Final to 5.6.0.Final](https://github.com/premium-minds/billy/pull/205)
 - [Bump bcpkix-jdk15on from 1.67 to 1.68](https://github.com/premium-minds/billy/pull/143)
 - [Bump bcpkix-jdk15on from 1.68 to 1.69](https://github.com/premium-minds/billy/pull/173)
 - [Bump snakeyaml from 1.27 to 1.28](https://github.com/premium-minds/billy/pull/148)
 - [Bump snakeyaml from 1.28 to 1.29](https://github.com/premium-minds/billy/pull/172)
 - [Bump commons-lang3 from 3.11 to 3.12.0](https://github.com/premium-minds/billy/pull/152)
 - [Bump slf4j-api from 1.7.30 to 1.7.31](https://github.com/premium-minds/billy/pull/176)
 - [Bump slf4j-api from 1.7.31 to 1.7.32](https://github.com/premium-minds/billy/pull/181)
 - [Bump mockito-core from 3.6.28 to 3.7.0](https://github.com/premium-minds/billy/pull/144)
 - [Bump mockito-core from 3.7.0 to 3.7.7](https://github.com/premium-minds/billy/pull/145)
 - [Bump mockito-core from 3.7.7 to 3.9.0](https://github.com/premium-minds/billy/pull/156)
 - [Bump mockito-core from 3.9.0 to 3.10.0](https://github.com/premium-minds/billy/pull/166)
 - [Bump mockito-core from 3.10.0 to 3.11.0](https://github.com/premium-minds/billy/pull/170)
 - [Bump mockito-core from 3.11.0 to 3.11.1](https://github.com/premium-minds/billy/pull/174)
 - [Bump mockito-core from 3.11.1 to 3.11.2](https://github.com/premium-minds/billy/pull/177)
 - [Bump mockito-core from 3.11.2 to 3.12.4](https://github.com/premium-minds/billy/pull/193)
 - [Bump mockito-core from 3.12.4 to 4.0.0](https://github.com/premium-minds/billy/pull/200)
 - [Bump junit-jupiter-engine from 5.7.0 to 5.7.1](https://github.com/premium-minds/billy/pull/146)
 - [Bump junit-jupiter-engine from 5.7.1 to 5.7.2](https://github.com/premium-minds/billy/pull/167)
 - [Bump junit-jupiter-engine from 5.7.2 to 5.8.0](https://github.com/premium-minds/billy/pull/196)
 - [Bump junit-jupiter-engine from 5.8.0 to 5.8.1](https://github.com/premium-minds/billy/pull/199)
 - [Bump tiles-maven-plugin from 2.19 to 2.20](https://github.com/premium-minds/billy/pull/154)
 - [Bump tiles-maven-plugin from 2.20 to 2.21](https://github.com/premium-minds/billy/pull/163)
 - [Bump tiles-maven-plugin from 2.21 to 2.22](https://github.com/premium-minds/billy/pull/168)
 - [Bump tiles-maven-plugin from 2.22 to 2.23](https://github.com/premium-minds/billy/pull/175)
 - [Bump tiles-maven-plugin from 2.23 to 2.24](https://github.com/premium-minds/billy/pull/195)
 - [Bump maven-gpg-plugin from 1.6 to 3.0.1](https://github.com/premium-minds/billy/pull/165)
 - [Bump maven-javadoc-plugin from 3.2.0 to 3.3.0](https://github.com/premium-minds/billy/pull/169)
 - [Bump maven-javadoc-plugin from 3.3.0 to 3.3.1](https://github.com/premium-minds/billy/pull/194)
 - [Bump license-maven-plugin from 3.0 to 4.0](https://github.com/premium-minds/billy/pull/155)
 - [Bump license-maven-plugin from 4.0 to 4.1](https://github.com/premium-minds/billy/pull/160)
 - [Bump maven-scm-provider-gitexe from 1.11.2 to 1.11.3](https://github.com/premium-minds/billy/pull/187)
 - [Bump maven-scm-provider-gitexe from 1.11.3 to 1.12.0](https://github.com/premium-minds/billy/pull/198)
 - [Bump maven-scm-api from 1.11.2 to 1.11.3](https://github.com/premium-minds/billy/pull/188)
 - [Bump maven-scm-api from 1.11.3 to 1.12.0](https://github.com/premium-minds/billy/pull/197)

### Fixed

 - [Tax Exemption Code in Portugal Validation](https://github.com/premium-minds/billy/issues/201)
 - [Add validation to invoice number and series](https://github.com/premium-minds/billy/pull/204)

## [5.3.0] - 2020-12-17

### Added

 - [Adds XML validation against XSD file](https://github.com/premium-minds/billy/pull/133)
 - [Adds new calculated field ATCUD value to PTGenericInvoice entity, and set on issue()](https://github.com/premium-minds/billy/pull/140)
   ```
   ALTER TABLE billy_pt_generic_invoice ADD COLUMN IF NOT EXISTS atcud VARCHAR(255) default '0';
   ALTER TABLE billy_pt_generic_invoice_aud ADD COLUMN IF NOT EXISTS atcud VARCHAR(255) default '0';
   ```

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
 - [Bump tiles-maven-plugin from 2.18 to 2.19](https://github.com/premium-minds/billy/pull/139)

### Fixed

- [Corrects the hash verification field](https://github.com/premium-minds/billy/pull/126)
- [Adds missing Credit or Debit information to some invoice types](https://github.com/premium-minds/billy/pull/138). SQL script to fix previous invoices:
  ```
  UPDATE billy_core_generic_invoice SET credit_or_debit = 'CREDIT'
    WHERE id IN (
                SELECT bcgi.id
                    FROM billy_pt_receipt_invoice bpri
                    JOIN billy_pt_invoice bpi ON bpri.id = bpi.id
                    JOIN billy_pt_generic_invoice bpgi ON bpi.id = bpgi.id
                    JOIN billy_core_generic_invoice bcgi ON bpgi.id = bcgi.id
                    WHERE credit_or_debit IS NULL
        )
  UPDATE billy_core_generic_invoice SET credit_or_debit = 'CREDIT'
    WHERE id IN (
                SELECT bcgi.id
                    FROM billy_pt_simple_invoice bpsi
                    JOIN billy_pt_invoice bpi ON bpsi.id = bpi.id
                    JOIN billy_pt_generic_invoice bpgi ON bpi.id = bpgi.id
                    JOIN billy_core_generic_invoice bcgi ON bpgi.id = bcgi.id
                    WHERE credit_or_debit IS NULL
        )
  ```
  - [Corrects credit notes to use debit amount and not credit amount in SAFT](https://github.com/premium-minds/billy/pull/131)

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


[unreleased v6]: https://github.com/premium-minds/billy/compare/release-5.x...HEAD
[unreleased v5]: https://github.com/premium-minds/billy/compare/v5.4.2...release-5.x
[unreleased v4]: https://github.com/premium-minds/billy/compare/v4.0.0...release-4.x
[unreleased v3]: https://github.com/premium-minds/billy/compare/v3.3.1...release-3.x
[5.4.2]: https://github.com/premium-minds/billy/compare/v5.4.1...v5.4.2
[5.4.1]: https://github.com/premium-minds/billy/compare/v5.4.0...v5.4.1
[5.4.0]: https://github.com/premium-minds/billy/compare/v5.3.0...v5.4.0
[5.3.0]: https://github.com/premium-minds/billy/compare/v5.2.0...v5.3.0
[5.2.0]: https://github.com/premium-minds/billy/compare/v5.1.0...v5.2.0
[5.1.0]: https://github.com/premium-minds/billy/compare/v5.0.0...v5.1.0
[5.0.0]: https://github.com/premium-minds/billy/compare/release-4.x...v5.0.0
[4.0.0]: https://github.com/premium-minds/billy/compare/release-3.x...v4.0.0
[3.3.1]: https://github.com/premium-minds/billy/compare/v3.3.0...v3.3.1
[3.3.0]: https://github.com/premium-minds/billy/compare/v3.2.1...v3.3.0
[3.2.1]: https://github.com/premium-minds/billy/compare/v3.2.0...v3.2.1
[3.2.0]: https://github.com/premium-minds/billy/compare/v3.1.2...v3.2.0
[3.1.2]: https://github.com/premium-minds/billy/compare/v3.1.1...v3.1.2
