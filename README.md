# DSDV
This program simulates DSDV routing protocol. More about DSDV [here](https://en.wikipedia.org/wiki/Destination-Sequenced_Distance_Vector_routing)
##Requirements
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 or higher (Might work on older java versions too, however this program was developed on 1.8)

##Setup
Download and compile program.
```
$ git clone https://github.com/DeividasZilinskas/DSDV.git
$ cd DSDV/
$ ./compile.sh
```

##Usage
Run six instances with diferent config files. Each instance of this program acts like a modem using DSDV protocol.
```
$ cd out/
$ java simulate.Main ../a.csv
$ java simulate.Main ../b.csv (on a new terminal tab)
$ java simulate.Main ../c.csv (on a new terminal tab)
$ java simulate.Main ../d.csv (on a new terminal tab)
$ java simulate.Main ../e.csv (on a new terminal tab)
$ java simulate.Main ../f.csv (on a new terminal tab)
```
Each instance of this program starts sending its and receiving others routing table and each instance will print its routing table every second. 
##Config file example

**Filename:** modemName.csv  
**File content:**
```
modemName, 0, modemPort(int)
knownNeighborname, distanceToNeighbor(int), NeighborPort(int)
knownNeighborname2, distanceToNeighbor2(int), NeighborPort2(int)
.
.
.
knownNeighbornameN, distanceToNeighborN(int), NeighborPortN(int)
```  
**Filename:** a.csv  
**File content:**
```
A,0,3030
B,2,3031
C,1,3032
D,1,3033
```
- - - - - - -  
###### &copy; 2016 [Deividas Žilinskas](https://github.com/DeividasZilinskas)
