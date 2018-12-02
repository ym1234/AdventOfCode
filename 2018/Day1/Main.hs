import qualified Data.IntSet as IntSet

-- parse Taken from reddit lul
parse :: String -> [Int]
parse =  map (read . dropWhile (== '+')) . lines

part1 :: [Int] -> Int
part1 = sum

part2 :: [Int] -> Int
part2 x = f IntSet.empty $ scanl (+) 0 $ cycle x

f :: IntSet.IntSet -> [Int] -> Int
f set (x:xs) = if IntSet.member x set then x else f (IntSet.insert x set) xs

main = do
  file <- readFile "input.txt"
  let input = parse file
  let out = putStrLn . show
  out . part1 $ input
  out . part2 $ input

