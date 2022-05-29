package com.enrayatresqf.tresenrataqf;


import java.util.Random;
public class Partida {

    public final int dificultad;
    public int jugador;
    private int []casillasOcupadas;
    private final int[][] combinacionesGanadoras={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public Partida(int dificultad){
        this.dificultad=dificultad;
        jugador=1;
        casillasOcupadas=new int[9];
        for(int i=0;i<casillasOcupadas.length;i++){//Inicializamos el array que iremos informando a 1 cuando pulsemos la casilla
            casillasOcupadas[i]=0;
        }
    }

    /**
     *Nos devuelve valor de casilla clave si cumple cuantalLleva==2 y casilla!=1
     */
    public int dosEnRaya(int jugadorEnTurno){
        int casilla=-1; //La iniciamos en una casilla que no existe -1
        int cuantasLleva=0;
        for(int i=0;i<combinacionesGanadoras.length;i++){
            for(int pos:combinacionesGanadoras[i]){
                if(casillasOcupadas[pos]==jugadorEnTurno){
                    cuantasLleva++;
                }

                if(casillasOcupadas[pos]==0){
                    casilla=pos;
                }
            }//fin for anidado
            if (cuantasLleva == 2 && casilla != -1) {
                return casilla;
            }

            //Reseteamos valores
            casilla=-1;
            cuantasLleva=0;
        }//fin for princiapl
        return -1;
    }

    public int ia(){ //Método que devuelve un valor aleatorio entre 0 y 8
        int casilla;
        casilla=dosEnRaya(2);// compruebo si
        if(casilla!=-1){
            return casilla;
        }

        if(dificultad>0){
            casilla=dosEnRaya(1);
            if(casilla!=-1){
                return casilla;
            }
        }
        //Si no entra al anterior marcar una esquina
        if(dificultad==2){
            if(casillasOcupadas[0]==0)  return 0;
            if(casillasOcupadas[2]==0)  return 2;
            if(casillasOcupadas[6]==0)  return 6;
            if(casillasOcupadas[8]==0)  return 8;
        }

        Random casilla_azar=new Random();
        casilla=casilla_azar.nextInt(9);
        return casilla;
    }



    /**
     * Método turno devuelve 3 si empatan, 2 si gana jugador 2, 1 si gana jugador 1, 0 (juego en ejecucion)ninguna de las anteriores
     * @return
     */
    public int turno(){

        //3 Empate
        //1 Gana jugador 1
        //2 Gana jugador 2
        //0 Ninguna de las anteriores
        boolean empate=true;
        boolean ultimoMovimiento=true;
        for(int i=0;i<combinacionesGanadoras.length;i++){

            for(int pos:combinacionesGanadoras[i]){
                //System.out.println("Valor en posición "+pos+" "+casillasOcupadas[pos]);
                if(casillasOcupadas[pos]!=jugador){//Si alguno de los tres elementos del bucle es distinto del jugador es false y se mantiene Si es distinto de 1 o 2
                    ultimoMovimiento=false;
                }

                //Si encuentra valores 0 significa que seguimos jugando por lo que no puede haber empate
                if(casillasOcupadas[pos]==0){
                    empate=false;
                }

            }//cierre for anidado
            // System.out.println("------------------");
            if(ultimoMovimiento){
                return jugador;
            }

            ultimoMovimiento=true;
        }//cierre for principal
        if(empate){
            return 3;//Sale del método si ve que han empatado
        }

        jugador++;
        if(jugador>2){
            jugador=1;
        }

        return 0;
    }

    public boolean compruebaCasilla(int casilla){

        if(casillasOcupadas[casilla]!=0){
            return false;
        }else{
            casillasOcupadas[casilla]=jugador;
        }
        return true;
    }


}