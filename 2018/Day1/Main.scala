import scala.io.Source
import scala.collection.mutable.HashSet

def cycle[T](seq: Stream[T]) = Stream.continually(seq).flatten
val input = Source.fromFile("input.txt").getLines.toStream.map(_.toInt)

val part1 = input.foldLeft(0)(_+_)

val set = new HashSet[Int]
val part2 = cycle(input).scanLeft(0)(_+_).find(i => if (set.contains(i)) { true } else { set += i; false }).getOrElse(-1)

println(s"$part1, $part2");

