package com.deltegui.plantio;

import com.deltegui.plantio.weather.domain.Range;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangeTest {
    @ParameterizedTest
    @MethodSource("shouldIncludeOtherRangeData")
    public void shouldIncludeOtherRange(Range first, Range compare, boolean expected) {
        assertEquals(first.includes(compare), expected);
    }

    public static Stream<Arguments> shouldIncludeOtherRangeData() {
        return Stream.of(
                // [                 ]
                //      (     )
                Arguments.of(new Range(0, 2), new Range(0, 1), true),

                //       [  ]
                //  (           )
                Arguments.of(new Range(21, 1), new Range(19, 20), true),

                // [                 ]
                // (                 )
                Arguments.of(new Range(0, 2), new Range(0, 2), true),

                // [       ]
                //         (        )
                Arguments.of(new Range(0, 2), new Range(4, 2), true),

                //        [       ]
                // (      )
                Arguments.of(new Range(0, 2), new Range(-4, 2), true),

                //  [       ]
                //      (        )
                Arguments.of(new Range(0, 2), new Range(4, 5), true),

                //       [         ]
                // (        )
                Arguments.of(new Range(0, 2), new Range(-4, 10), true),

                // [     ]
                //               (     )
                Arguments.of(new Range(0, 2), new Range(10, 2), false),

                //               [     ]
                //  (     )
                Arguments.of(new Range(0, 2), new Range(-14, 2), false)
        );
    }
}
