package io.github.danilkuznetsov.procamp.task3;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3)
public class BenchmarkSuite {

    private List<Integer> numbers;

    @Setup
    public void setUp() {
        numbers = IntStream
                .rangeClosed(0, 100)
                .boxed()
                .collect(Collectors.toList());
    }

    @Benchmark
    public void benchmarkPrimesBrutForceLoop(Blackhole blackhole) {
        for (Integer i : numbers) {
            boolean isFound = true;

            for (int j = 2; j <= i; j++) {
                if (i % j == 0) {
                    isFound = false;
                    break;
                }
            }

            // if found prime number, can utilize it
            if (isFound){
                blackhole.consume(i);
            }
        }
    }

    @Benchmark
    public void benchmarkPrimesBrutForceStream(Blackhole blackhole) {
        numbers.stream()
                .filter(i -> IntStream.rangeClosed(2, i).noneMatch(j -> i % j == 0))
                .forEach(blackhole::consume);
    }

    @Benchmark
    public void benchmarkPrimesBrutForceParallelStream(Blackhole blackhole) {
        numbers.stream()
                .parallel()
                .filter(i -> IntStream.rangeClosed(2, i).noneMatch(j -> i % j == 0))
                .forEach(blackhole::consume);
    }

    @Benchmark
    public void benchmarkExceptionWithStackTrace(Blackhole blackhole) {
        try {
            throw new CustomException("StackTrace is enabled", true);
        } catch (CustomException e) {
            blackhole.consume(e);
        }
    }

    @Benchmark
    public void benchmarkExceptionWithoutStackTrace(Blackhole blackhole) {

        try {
            throw new CustomException("StackTrace is disabled", false);
        } catch (CustomException e) {
            blackhole.consume(e);
        }
    }
}