import Data.IntSet

-- parse Taken from reddit lul
parse :: String -> [Int]
parse =  fmap (read . dropWhile (== '+')) . lines

part1 :: [Int] -> Int
part1 = sum

part2 :: [Int] -> Int
part2 = f empty . scanl (+) 0 . cycle

f :: IntSet -> [Int] -> Int
f set (x:xs) = if member x set then x else f (insert x set) xs

main :: IO ()
main = putStrLn =<< show . (([part1, part2] <*>) . pure) <$> parse <$> readFile "input.txt"
