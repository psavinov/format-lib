format-lib
==========

Format Library

Library provides functionality for reading/writing custom Java objects which
corresponds to the following requirements:
1.Object should contain declared fields of following types only:
 - java.lang.Integer
 - java.lang.String
 - java.util.Date
 - java.lang.Boolean
 - java.lang.Byte

2.Each field of object must have setter and getter methods named accordingly 
to the Sun Naming Convention.

3.Object should provide public constructor without any arguments or be
constructor-less.  

Format library provides 3 basic interfaces:

- IReader, performs reading of object's collection of predetermined type
- IWriter, performs writing of object's collection of predetermined type
- IObject, readable/writable object

- an abstract class AbstractObject, which contains implementation of IObject
interface based on Java Reflection API. It's recommended for all IObject
implementations to extend AbstractObject class.

- XMLReader implementation of IReader interface which allows to read objects
from XML-files.
- XMLWriter implementation of IWriter interface which allows to write objects
to XML files accordingly to the specified object's type.

- BinaryReader implementation of IReader to perform reading from binary files
accordingly with the assignment file format description.
- BinaryWriter implementation of IWriter to perform writing to binary files of
assignment-specific format.

Positive features of library:
 - Provides easy way to store simple objects in XML/binary format without
 dependency to specific object's class
 - Easy extensible interfaces provides an opportunity to create new writers
 and readers fast enough
 - It's easy to add new types, it's needed just to upgrade ETypes enumeration
 and PropertyUtil utility class to achieve new types processing
 
Negative features of library:
 - Few types supported by default
 - There is no mechanism to find one record in the data-source and change it,
 to edit some object it's necessary to "check-out" whole list, edit and
 save the whole list again, maybe a lack of productivity, but good enough for
 small lists of data.