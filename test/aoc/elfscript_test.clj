(ns aoc.elfscript-test
  (:require [aoc.elfscript :as sut]
            [clojure.test :refer :all]))

(deftest ocr
  (are [out in] (sut/ocr in)
    "PZGPKPEB" '("###..####..##..###..#..#.###..####.###.."
                 "#..#....#.#..#.#..#.#.#..#..#.#....#..#."
                 "#..#...#..#....#..#.##...#..#.###..###.."
                 "###...#...#.##.###..#.#..###..#....#..#."
                 "#....#....#..#.#....#.#..#....#....#..#."
                 "#....####..###.#....#..#.#....####.###..")

    "FCJAPJRE" '("####..##....##..##..###....##.###..####."
                 "#....#..#....#.#..#.#..#....#.#..#.#...."
                 "###..#.......#.#..#.#..#....#.#..#.###.."
                 "#....#.......#.####.###.....#.###..#...."
                 "#....#..#.#..#.#..#.#....#..#.#.#..#...."
                 "#.....##...##..#..#.#.....##..#..#.####.")))

(deftest normalizer
  (is (= "BLKJRBAG"
         (sut/ocr '("%%%  %    %  %   %% %%%  %%%   %%   %% "
                    "%  % %    % %     % %  % %  % %  % %  %"
                    "%%%  %    %%      % %  % %%%  %  % %   "
                    "%  % %    % %     % %%%  %  % %%%% % %%"
                    "%  % %    % %  %  % % %  %  % %  % %  %"
                    "%%%  %%%% %  %  %%  %  % %%%  %  %  %%%")
                  {:off \space :on \%}))))

(deftest throws-on-unknown
  (is (thrown? Exception
               (sut/ocr '("###..####..##..###..#..#.###..####.###.."
                          "#..#....#.#..#.#..#.#.#..#..#.#....#...."
                          "#..#...#..#....#..#.##...#..#.###......."
                          "###...#...#.##.###..#.#..###..#....#..#."
                          "#....#....#..#.#....#.#..#....#.......#."
                          "#....####..###.#....#..#.#....####.###..")))))
