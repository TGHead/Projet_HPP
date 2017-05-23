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
1.47GB / 100MB = 15.0528<br/>
15.0528 * 5.86 mins = 88.21 mins (1h29mins)