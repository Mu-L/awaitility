/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.awaitility;

import org.junit.Test;

import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.LongAccumulator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

public class AwaitilityAccumulatorTest {

    @Test(timeout = 2000)
    public void awaitilityCanWaitForLongAccumulators() {
        // Given
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x * y * 2, 3);

        // When
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            accumulator.accumulate(5);
        }).start();

        // Then
        await().untilAccumulator(accumulator, equalTo(30L));
    }

    @Test(timeout = 2000)
    public void awaitilityCanWaitForLongAccumulatorsWithConsumerMatcher() {
        // Given
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x * y * 2, 3);

        // When
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            accumulator.accumulate(5);
        }).start();

        // Then
        await().untilAccumulator(accumulator, value -> assertThat(value).isEqualTo(30L));
    }

    @Test(timeout = 2000)
    public void awaitilityCanWaitForDoubleAccumulators() {
        // Given
        DoubleAccumulator accumulator = new DoubleAccumulator((x, y) -> x * y * 2, 3.2d);

        // When
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            accumulator.accumulate(5.5d);
        }).start();

        // Then
        await().untilAccumulator(accumulator, equalTo(35.2d));
    }

    @Test(timeout = 2000)
    public void awaitilityCanWaitForDoubleAccumulatorsWithConsumerMatcher() {
        // Given
        DoubleAccumulator accumulator = new DoubleAccumulator((x, y) -> x * y * 2, 3.2d);

        // When
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            accumulator.accumulate(5.5d);
        }).start();

        // Then
        await().untilAccumulator(accumulator, value -> assertThat(value).isEqualTo(35.2d));
    }
}