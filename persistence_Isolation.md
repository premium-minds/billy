# Persistence isolation
Prototype of the needed changes to isolate Persistence specific entities from the business logic.

## Proposed structure

```
billy
└──billy-core
│    Address
│    AddressEntity
└──billy-core-jpa
│    module to be deleted
│      * This removes duplication but the need to have a "duplicate" for each country creates a false sensation that the base can be used by it self
│      * Other reasons...
│    JPAAddressEntity
└──billy-portugal
│    PTAddress
│    PTAddressEntity
│    PTAddressBuilder
│    PTAddressDAO
└──billy-portugal-jpa
│    JPAPTAddressEntity
│    JPAPTAddressDAOImpl
└──(billy-portugal-ebean)
       * To be added in the future if needed
     EBPTAddressEntity
     EBPTAddressDAOImpl
```
