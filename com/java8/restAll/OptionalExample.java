package com.java8.restAll;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

public class OptionalExample {
	public static void main(String[] args) {
		Optional<String> stringOrNot = Optional.of("123");

        // This String reference will never be null
        String alwaysAString = stringOrNot.orElse("");
        System.out.println(alwaysAString);
        
        // This Integer reference will be wrapped again
        Optional<Integer> integerOrNot = stringOrNot.map(Integer::parseInt);
        System.out.println(integerOrNot);
        
        // This int reference will never be null
        int alwaysAnInt = stringOrNot
                .map(s -> Integer.parseInt(s))
                .orElse(0);
        System.out.println(alwaysAnInt);
        
        // Streams also make heavy use of Optional types
        Optional<Integer> anyInteger =
        Arrays.asList(1, 2, 3)
              .stream()
              .filter(i -> i % 2 == 0)
              .findAny();
        anyInteger.ifPresent(System.out::println);
        
        // Primitive types
        OptionalInt anyInt =
        Arrays.stream(new int[] {1, 2, 3 , 4})
              .filter(i -> i % 2 == 0)
              .findAny();

        anyInt.ifPresent(System.out::println);
	}
}
