This software provides visual demonstration of Neumann & Wunsch's Global Sequence Alignment algorithm. 
The main purpose is to make the logic of the algorithm more understandable. 

The software provides two options for sequence alignment:
 - Protein alignment
 - Genomic sequence alignment 

How to use --- 
 - You need Java 8+ to build and execute the source code
 - Or preferable to have Java 11 as the software was build with JDK 11 
 - You can also use prebuild jar file provided with source codes under target folder

Features: 

- Choose alignment type
- Set prefered match, mismatch, gap penalty scores
- input sequence (limit 40 character length, otherwise the illusatration would have been a mess) 
- step by algorithm with Next button
- start over with Clear table
- see formula
- get alignment whenever you want with alignment score
- see traceback path illustration when reaching the last row of the matrix
