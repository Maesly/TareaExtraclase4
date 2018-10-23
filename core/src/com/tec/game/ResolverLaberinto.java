package com.tec.game;

public class ResolverLaberinto {

    final int N = 8;  //El tamaño de la matriz, que es de NxN
    int[][] solucionFinal;


    /**
     * Método para imprimir la matriz
     * @param sol Matriz con la solución
     */
    void imprimirSolucion(int sol[][])
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(" " + sol[i][j] + " ");
            System.out.println();
        }
        System.out.println("  ");
    }

    int[][] getSolucionFinal( ){
        return solucionFinal;
    }


    /**
     * Revisa que el indice sea válido
     * @param laberinto matriz
     * @param x Posición x
     * @param y Posición y
     * @return true / false
     */
    boolean isSafe(int laberinto[][], int x, int y)
    {
        //Si x o y están fuera de la matriz, retorna falso
        return (x >= 0 && x < N && y >= 0 &&
                y < N && laberinto[x][y] == 1);
    }

    /**
     * Función recursiva para resolver el laberinto utilizando Bactracking, usa la función auxiliar,
     * retorna true si encuentra la solución.
     * @param laberinto Matriz con el laberinto
     * @return true / false
     * @throws InterruptedException
     */
    boolean resolverLaberinto(int laberinto[][]) throws InterruptedException {
        int solucion[][] = { {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        //Hace la llada recursiva a la función auxiliar
        if (resolverLaberintoAux(laberinto, 0, 0, solucion) == false)
        {
            //Retorna falso si no encuentra la solución
            System.out.print("No existe solución para el laberinto.");
            return false;
        }

        //Acá retorna true si encuentra la solución, e imprime la matriz.
        imprimirSolucion(solucion);
        solucionFinal = solucion;
        return true;
    }


    /**
     * Resuelve el laberinto
     * @param maze
     * @param x
     * @param y
     * @param sol
     * @return
     * @throws InterruptedException
     */
    boolean resolverLaberintoAux(int maze[][], int x, int y, int sol[][]) throws InterruptedException {
        // Si x y y son válidos, retorna true
        if (x == N - 1 && y == N - 1)
        {
            sol[x][y] = 1;
//            imprimirSolucion(sol); //Imprime la solución
//            try {
//                TimeUnit.SECONDS.sleep(1);   //Detiene la ejecución 1 segundo, pero esto es
//            } catch (InterruptedException e) {       //solo para cuando ocupamos ver la ejecución paso a paso.
//                e.printStackTrace();
//            }
            return true;
        }

        //Chequea con la funcion si el espacio es válido
        if (isSafe(maze, x, y) == true)
        {
            //Marca ese espacio el la matriz de solución con un 1
            sol[x][y] = 1;

            //imprimirSolucion(sol);    //Acá de nuevo, imprime la solucion con ese paso y se detiene por un segundo
            //TimeUnit.SECONDS.sleep(1);

            //Se mueve en x
            if (resolverLaberintoAux(maze, x + 1, y, sol))
                return true;

            //Si moviendose en x no funciona, entonces se mueve para abajo en y
            if (resolverLaberintoAux(maze, x, y + 1, sol))
                return true;

            //Si nada funciona hace bactack, pone un 0 y prueba nuevos caminos
            sol[x][y] = 0;

            //imprimirSolucion(sol);  //Acá de nuevo, imprime la solucion con ese paso y se detiene por un segundo
            //TimeUnit.SECONDS.sleep(1);

            return false;
        }

        return false;
    }

}