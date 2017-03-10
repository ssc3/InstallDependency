# InstallDependency
A graph that detects package dependencies

GIVEN:
A series of commands which are operations on packages. Eg:

DEPEND TELNET TCPIP NETCARD
DEPEND TCPIP NETCARD
DEPEND DNS TCPIP NETCARD
INSTALL NETCARD
INSTALL foo
REMOVE NETCARD
INSTALL BROWSER
INSTALL DNS
LIST
REMOVE TELNET
INSTALL NETCARD
REMOVE TCPIP
LIST
END


GOAL:
Read the above series of commands and perform install, remove and list operations.

HOW IS THIS IMPLEMENTED:
This is implemented by maintaining a bi-directed graph of every package. Every package has parent edges (indicating all packages that need to be installed before this package) and child edges (indicating all packages dependent on this package). The DEPEND commands build dependencies while INSTALL, REMOVE and LIST do as they say. 

Note: INSTALL and REMOVE are transitive. So, they can go multiple levels. 

DETAILS:
Please read Test1-SystemDependencies.pdf
