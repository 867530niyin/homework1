# homework1

My solution uses the 6k +- 1 approach to check primes. Half of all numbers are divisible by 2 and an additional sixth are dividible by 3 (half of all numbers divisible by 3 are already divisible by 2). The remaining 2/6 of numbers are expressed by the 6k +- 1. Once a number's divisibility with 2 and 3 is checked, only those remaining 2/6 of numbers need to be checked for divisibility. The threads check the odd numbers in batches of 100 and update counters for any primes they find.

The prime finding process starts by only looking at odd numbers. the only even prime is 2 and it is accounted for. additionally you only need to check for divisibility on integers <= the sqrt of the number being checked. Any divisors above the sqrt are paired with a number below the sqrt. with the 6k +- 1 approach only 1/3sqrt(n) numbers need to be checked to determine if n is prime. this makes the program approximately O(nsqrt(n)).


To compile the program on the command line, navigate to the directory that Main.java is in. Type "javac Main.java" to compile, then "java Main" to run.
