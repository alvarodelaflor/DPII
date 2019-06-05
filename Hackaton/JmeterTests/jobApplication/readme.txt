This test cannot be verified by the use of jMetter. 
The reason for not being able to do this is because you would need to use two simultaneous CSV files.

A jobApplication needs that the cleaner that performs it no longer has an application to that pending host, 
so we would need a first CSV to create a multitude of cleaners that all make an application to the same host.

On the other hand, the user who makes the application needs to attach a valid curriculum (he has made it and 
it is not a copy). Here, it would be necessary to use a second CSV to attach a valid curriculum for that 
curriculum.

The use of two coordinated CSV files (the one that provides the login of a cleaner and the one that provides 
the curriculum corresponding to that user) is not possible to do in this version of jMeter.

To corroborate the above see the attached image where we can see that are passed as hidden parameters both the 
id of a host and the id of a curriculum.

