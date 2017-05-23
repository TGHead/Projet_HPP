# [TSE] FISE2 Project High Performance Computing

## Project Description:

The underlying scenario addresses the analysis metrics for a dynamic (evolving) social-network graph. Specifically, the 2016 Grand Challenge targets following problems: identification of the posts that currently trigger the most activity in the social network. The corresponding queries require continuous analysis of a dynamic graph under the consideration of multiple streams that reflect updates to the graph.

more information on : [DEBS 2016](http://www.ics.uci.edu/~debs2016/call-grand-challenge.html)

## Project Structure:

### Data Structure:
Here's the class diagram for the base data structure:

![DS Schema](README/DS%20schema.png)

### Producer Schema:

![Producer Schema](README/Producer%20Schema.png)

### Consumer Schema:


## JUnit Test:

### Test Data Modification:
For passing the simple tests, we have to modify some records in the `_expectedQ1.txt` to let the test data 

### JUnit Test Result:

## JMH Benchmark Test:

### Test Results:

Here's the result on the machine ThinkPad-X1(I7-4600U @2.7GHz, 8GB RAM):<br/>

```
# Run complete. Total time: 00:02:33

Benchmark                          (arg)   Mode  Samples    Score  Score error  Units
t.t.h.TestBenchmark_Sample.test      100  thrpt        5  232.462       43.227  ops/s
t.t.h.TestBenchmark_Sample.test     1000  thrpt        5    1.826        0.138  ops/s
t.t.h.TestBenchmark_Sample.test     2000  thrpt        5    0.789        0.032  ops/s
t.t.h.TestBenchmark_Sample.test     5000  thrpt        5    0.357        0.010  ops/s
t.t.h.TestBenchmark_Sample.test    10000  thrpt        5    0.156        0.008  ops/s
```
`arg` means the amount of records in both `post` and `comment` files.<br/>
`Score` means the number of operations could run in 1s.<br/>

<figure class="double">
    <img src="README/Units.png">
    <img src="README/Debit.png">
</figure>

According to the debit, we suppose that the average debit in [1650, 1700]<br/>
588,652 / 1675 = 351.43s (5.86 mins) for 100MB data.<br/>
3.53GB / 100MB = 36.1472<br/>
36.1472 * 5.86 mins = 211.82 mins (3h32mins)

### 100MB Data Test:

Here's a result of *100MB* data test: (Unfinished)

```
# VM invoker: C:\Program Files\Java\jdk1.8.0_102\jre\bin\java.exe
# VM options: -javaagent:D:\Outils\JetBrains\IntelliJ IDEA Community Edition 2017.1\lib\idea_rt.jar=51866:D:\Outils\JetBrains\IntelliJ IDEA Community Edition 2017.1\bin -Dfile.encoding=UTF-8
# Warmup: 5 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: team.tse.hpp.TestBenchmark_100MB.test

# Run progress: 0.00% complete, ETA 00:00:10
# Fork: 1 of 1
# Warmup Iteration   1: 5700.732 s/op
# Warmup Iteration   2: 4713.467 s/op
# Warmup Iteration   3: 4290.647 s/op
# Warmup Iteration   4: 
Process finished with exit code 1
```

By the result of the test, we found that the actual situation isn't the same as we expected cause running 100MB (35.3MB for post and 81.5MB for comment 116.8MB total).<br/>
For running this 100MB test, we need at least `4290.647 s (1h 12mins) >> 5.86 mins(by calculation)`.