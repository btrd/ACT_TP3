package main

import (
	"fmt"
	"math"
)

func main() {

	fmt.Println(findSolution(5, 5, 4, 1))
}

func findSolution(nbLine int, nbRow int, coordX int, coordY int) int {
	tab := initArray(nbLine, nbRow)

	return calcValue(nbLine, nbRow, coordX, coordY, tab)
}

func initArray(nbLine int, nbRow int) *[][][][]int {
	tab := make([][][][]int, nbLine + 1)
	for i := range tab {
		tab[i] = make([][][]int, nbRow + 1)
		for j := range tab[i] {
			tab[i][j] = make([][]int, nbLine + 1)
			for k := range tab[i][j] {
				tab[i][j][k] = make([]int, nbRow + 1)
				for l := range tab[i][j][k] {
					tab[i][j][k][l] = math.MinInt64
				}
			}
		}
	}

	return &tab
}

func printArray(tab *[][][][]int) {
	for i := range *tab {
		for j := range (*tab)[i] {
			for k := range (*tab)[i][j] {
				fmt.Println((*tab)[i][j][k])
			}
			fmt.Println()
		}
		fmt.Println()
	}

}

func calcValue(nbLine int, nbRow int, coordX int, coordY int, tab *[][][][]int) int {
	if nbLine == 1 && nbRow == 1 {
		return 0
	} else {

		arrayOfValues := make([]int, nbLine + nbRow)
		tmp := 0
		max := 0
		neg := false
		idArray := 0

		for iLine := 1; iLine < nbLine; iLine++ {
			if iLine <= coordX {
				// if (*tab)[nbLine - iLine][nbRow][coordX - iLine][coordY] == math.MinInt64 {

					tmp = calcValue(nbLine - iLine, nbRow, coordX - iLine, coordY, tab)
				// 	(*tab)[nbLine - iLine][nbRow][coordX - iLine][coordY] = tmp
				// } else {
				// 	tmp = (*tab)[nbLine - iLine][nbRow][coordX - iLine][coordY]
				// }
			} else {
				// if (*tab)[nbLine][nbRow][coordX][coordY] == math.MinInt64 {
					tmp = calcValue(iLine, nbRow, coordX, coordY, tab)
				// 	(*tab)[nbLine][nbRow][coordX][coordY] = tmp
				// } else {
				// 	tmp = (*tab)[nbLine][nbRow][coordX][coordY]
				// }
			}
			arrayOfValues[idArray] = tmp
			idArray++
			if tmp <= 0 {
				neg = true
			}			
		}

		

		for iRow := 1; iRow < nbRow; iRow++ {
			if iRow <= coordY {
				// if (*tab)[nbLine][nbRow - iRow][coordX][coordY - iRow] == math.MinInt64 {
					tmp = calcValue(nbLine, nbRow - iRow, coordX, coordY - iRow, tab)
				// 	(*tab)[nbLine][nbRow - iRow][coordX][coordY - iRow] = tmp
				// } else {
				// 	tmp = (*tab)[nbLine][nbRow - iRow][coordX][coordY - iRow]
				// }
			} else {
				// if (*tab)[nbLine][nbRow][coordX][coordY] == math.MinInt64 {
					tmp = calcValue(nbLine, iRow, coordX, coordY, tab)
				// 	(*tab)[nbLine][nbRow][coordX][coordY] = tmp
				// } else {
				// 	tmp = (*tab)[nbLine][nbRow][coordX][coordY]
				// }
			}

			arrayOfValues[idArray] = tmp
			idArray++
			if tmp <= 0 {
				neg = true
			}

		}
		if neg {
			max = (-1) * (maxNeg(arrayOfValues[:idArray]) - 1)
		} else {
			max = (-1) * (maxPos(arrayOfValues[:idArray]) + 1)
		}

		// fmt.Println(arrayOfValues[:idArray])
		// fmt.Println(neg)
		// fmt.Printf("Max : %d", max)
		//printArray(tab)
		// fmt.Println(max)

		return max
	}
}

func maxNeg(array []int) int {
	max := math.MinInt64
	for _, elt := range array {
		if elt <= 0 {
			max = int(math.Max(float64(elt), float64(max)))
		}
	}

	return max
}

func maxPos(array []int) int {
	max := 0
	for _, elt := range array {
		if elt > 0 {
			max = int(math.Max(float64(elt), float64(max)))
		}
	}

	return max
}
