package team.tse.hpp;

/**
 * Created by Administrator on 2017/5/23.
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class TestBenchmark_100MB {

    private String postfilepath, commentfilepath;
    private Main main;

//    @Param({"100", "1000", "2000", "5000", "10000"})
//    public int arg;


    @Setup(Level.Iteration)
    public void setup() {
        postfilepath = "src/test/Test resources/Tests/data/posts.dat";
        commentfilepath = "src/test/Test resources/Tests/data/comments.dat";
        main = new Main(postfilepath, commentfilepath);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void test( Blackhole bh ) {
        bh.consume(main.fortest());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TestBenchmark_100MB.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
