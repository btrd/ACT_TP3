package main

import (
	"fmt"
	"math"
)

type dynamic struct {
	set bool
	value int
}

func main() {

	fmt.Println(findSolution(100, 100, 50, 50))
}

func findSolution(nbLine int, nbRow int, coordX int, coordY int) int {
	tab := initArray(nbLine, nbRow)

	return calcValue(nbLine, nbRow, coordX, coordY, tab)
}

func initArray(nbLine int, nbRow int) *[][][][]dynamic {
	tab := make([][][][]dynamic, nbLine + 1)
	for i := range tab {
		tab[i] = make([][][]dynamic, nbRow + 1)
		for j := range tab[i] {
			tab[i][j] = make([][]dynamic, nbLine + 1)
			for k := range tab[i][j] {
				tab[i][j][k] = make([]dynamic, nbRow + 1)
			}
		}
	}

	return &tab
}

func calcValue(nbLine int, nbRow int, coordX int, coordY int, tab *[][][][]dynamic) int {
	if nbLine == 1 && nbRow == 1 {
		return 0
	}
	
	arrayOfValues := make([]int, nbLine + nbRow)
	max := 0
	neg := false
	idArray := 0

	neg = cutVertical(nbLine, nbRow, coordX, coordY, &arrayOfValues, &idArray, tab) ||
		cutHorizontal(nbLine, nbRow, coordX, coordY, &arrayOfValues, &idArray, tab)
	
	if neg {
		max = (-1) * (maxNeg(arrayOfValues[:idArray]) - 1)
	} else {
		max = (-1) * (maxPos(arrayOfValues[:idArray]) + 1)
	}

	return max
}

func cutVertical(nbLine int, nbRow int, coordX int, coordY int, arrayOfValues *[]int, idArray *int, tab *[][][][]dynamic) bool {
	neg := false
	tmp := 0

	for iLine := 1; iLine < nbLine; iLine++ {
		if iLine <= coordX {
			tmp = saveAnswer(nbLine - iLine, nbRow, coordX - iLine, coordY, tab)
		} else {
			tmp = saveAnswer(iLine, nbRow, coordX, coordY, tab)
		}
		(*arrayOfValues)[*idArray] = tmp
		(*idArray)++
		if tmp <= 0 {
			neg = true
		}			
	}

	return neg
}

func cutHorizontal(nbLine int, nbRow int, coordX int, coordY int, arrayOfValues *[]int, idArray *int, tab *[][][][]dynamic) bool {
	neg := false
	tmp := 0

	for iRow := 1; iRow < nbRow; iRow++ {
		if iRow <= coordY {
			tmp = saveAnswer(nbLine, nbRow - iRow, coordX, coordY - iRow, tab)
		} else {
			tmp = saveAnswer(nbLine, iRow, coordX, coordY, tab)
		}

		(*arrayOfValues)[*idArray] = tmp
		*idArray++
		if tmp <= 0 {
			neg = true
		}
	}

	return neg
}

func saveAnswer(nbLine int, nbRow int, coordX int, coordY int, tab *[][][][]dynamic) int {
	tmp := 0

	nbLine, nbRow, coordX, coordY = mirror(nbLine, nbRow, coordX, coordY)

	if !(*tab)[nbLine][nbRow][coordX][coordY].set {
		tmp = calcValue(nbLine, nbRow, coordX, coordY, tab)
		(*tab)[nbLine][nbRow][coordX][coordY].value = tmp
		(*tab)[nbLine][nbRow][coordX][coordY].set = true
	} else {
		tmp = (*tab)[nbLine][nbRow][coordX][coordY].value
	}

	return tmp
}

func mirror(nbLine int, nbRow int, coordX int, coordY int) (int, int, int ,int) {
	if nbLine < nbRow {
		nbRow_tmp := nbRow
		coordX_tmp := coordX

		nbRow = nbLine
		nbLine = nbRow_tmp

		coordX = coordY
		coordY = coordX_tmp
	}

	if coordX >= (nbLine / 2) {
		coordX = -(coordX - nbLine) - 1
	}
	if coordY >= (nbRow / 2) {
		coordY = -(coordY - nbRow) - 1
	}

	return nbLine, nbRow, coordX, coordY
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
