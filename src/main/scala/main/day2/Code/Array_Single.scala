// Creating arrays of different types
val intArray: Array[Int] = Array(5, 10, 15, 20)
val stringArray = Array("apple", "banana", "cherry")

// Iterating through arrays using for loop
for (item <- intArray) {
  print(s"$item ")
}
println("")
for (fruit <- stringArray) {
  print(s"$fruit ")
}

// Array initialized with fill
val zeros: Array[Int] = Array.fill(3)(0)
val ones: Array[Int] = Array.fill(4)(1)

// Printing elements
zeros.foreach(num => print(s"$num "))
println("")
ones.foreach(num => print(s"$num "))

// Accessing elements in an array
val values: Array[Double] = Array(2.5, 4.5, 6.5, 8.5)
val secondValue = values(1)

println(s"Array: ${values.mkString(", ")}")
println(s"Second element: $secondValue")

// Modifying array elements
val nums: Array[Int] = Array(1, 2, 3, 4)
nums(2) = 10 // Change the third element

println("Modified Array:")
nums.foreach(num => print(s"$num "))

// Array properties
val nums: Array[Int] = Array(5, 10, 15, 20)
val arrayLength = nums.length

println(s"Array: ${nums.mkString(", ")}")
println(s"Array Length: $arrayLength")

// Using map and filter
val numbers = Array(2, 4, 6, 8)
val tripled = numbers.map(_ * 3)
val odds = numbers.filter(_ % 2 != 0)

println(s"Tripled: ${tripled.mkString(", ")}")
println(s"Odds: ${odds.mkString(", ")}")

// Aggregating values
val scores = Array(10, 20, 30, 40)
val total = scores.reduce(_ + _)
val maxScore = scores.max
val minScore = scores.min

println(s"Scores: ${scores.mkString(", ")}")
println(s"Total: $total, Max: $maxScore, Min: $minScore")

// Array transformations with map
val tempsInCelsius = Array(0, 10, 20, 30)
val tempsInFahrenheit = tempsInCelsius.map(c => c * 9 / 5 + 32)

println(s"Temps in Celsius: ${tempsInCelsius.mkString(", ")}")
println(s"Temps in Fahrenheit: ${tempsInFahrenheit.mkString(", ")}")


