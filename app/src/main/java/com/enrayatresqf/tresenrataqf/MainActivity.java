package com.enrayatresqf.tresenrataqf;


import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int jugadores; //Variable para saber el número de jugadores
    private int[] casillas; //Array que representa las casillas

    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);


        casillas = new int[9]; //Inicialización del array con las casillas
        casillas[0]=R.id.imgA1;
        casillas[1]=R.id.imgA2;
        casillas[2]=R.id.imgA3;
        casillas[3]=R.id.imgB1;
        casillas[4]=R.id.imgB2;
        casillas[5]=R.id.imgB3;
        casillas[6]=R.id.imgC1;
        casillas[7]=R.id.imgC2;
        casillas[8]=R.id.imgC3;
    }

    public void aJugar(View vista) { //Pasamos vista que hace referencia al botón
        ImageView imagen;
        for(int cadaCasilla:casillas){  //Recorremos el array casillas elemento a elemento
            imagen=(ImageView)findViewById(cadaCasilla); //Identifico cada casilla como una imagen. Almaceno el ID de cada casilla dentro de la variable imagen
            imagen.setImageResource(R.drawable.blanco); //Le asigno la casilla en blanco para limpiar el tablero
        }

        jugadores=1; //Por defecto va a ser un jugador
        if(vista.getId()==R.id.btn2Jugadores){ //Si la vista que hemos cargado pertenece
            jugadores=2;
        }

        RadioGroup configDifucultad=(RadioGroup)findViewById(R.id.rdgrDificultad);//Creamos variable radioGroup y le asignamos el radio group del programa
        int id=configDifucultad.getCheckedRadioButtonId();
        int dificultad=0;   //Inicializamos la dificultad a facil
        if(id==R.id.rbMedia){     //Cambiamos la dificultad dependiendo del valor elegido
            dificultad=1;
        }else if(id==R.id.rbDificil){
            dificultad=2;
        }

        partida=new Partida(dificultad); //Creamos la partida, le pasamos dificultad
        ((Button)findViewById(R.id.btn1Jugador)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.rdgrDificultad)).setAlpha(0); //No tiene enable, bajamos la transparencia
        ((Button)findViewById(R.id.btn2Jugadores)).setEnabled(false);
    }

    public void toque(View miVista){//Recibimos una casilla por parametro R.id.b2
        if (partida==null){  //Si la partida es nula
            return;
        }
        int casilla=0;
        for(int i=0;i<9;i++){
            if(casillas[i]==miVista.getId()){
                casilla=i;
                break;
            }
        }
    /*
    Toast toast=Toast.makeText(this,"has pulsado la casilla"+casilla,Toast.LENGTH_LONG);
    toast.setGravity(Gravity.CENTER,0,0);
    toast.show();//Muestra la casilla que pulso
    */
        if(partida.compruebaCasilla(casilla)==false){
            return; // Te saca del metodo si compruebaCasilla te dice que está informada la casilla
        }

        marcar(casilla); //Marca la casilla jugador 2
        int resultado= partida.turno();
        if(resultado>0){
            termina(resultado);
            return;//para que finalice y no pete por poner partida=null
        }

        if(jugadores==1) {
            casilla = partida.ia();
            while (partida.compruebaCasilla(casilla) != true) {//Realizamos lo de encontrar casilla hasta que encontremos una vacia
                casilla = partida.ia(); //sobreescribimos para darle el valor para el jugador 2
            }


            //partida.turno();//Cambiamos el turno a 2
            marcar(casilla); //Marca la casilla jugador aleatorio 2
            resultado = partida.turno();//Volvemos a cambiar el turno a 1 para dejarlo preparado para pinchar
            if (resultado > 0) {
                termina(resultado);
            }
        }

    }

    private void termina(int resultado){
        String mensaje;
        if(resultado==1){
            mensaje="Ganan los circulos";
        }else if(resultado==2){
            mensaje="Ganan las aspas";
        }else{
            mensaje="EMPATE!!";
        }

        Toast toast=Toast.makeText(this,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();//Muestra la casilla que pulso
        partida=null;
        ((Button)findViewById(R.id.btn1Jugador)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.rdgrDificultad)).setAlpha(1);
        ((Button)findViewById(R.id.btn2Jugadores)).setEnabled(true);
    }

    private void marcar(int casilla){
        ImageView imagen;
        imagen=(ImageView)findViewById(casillas[casilla]);//Le asigno a una imageView la casilla correspondiente a la que hemos identificado en el toque
        if(partida.jugador==1){ //Si el jugador 1 es el que marca, se pone un circulo, sino el aspa
            imagen.setImageResource(R.drawable.circulo);
        }else{
            imagen.setImageResource(R.drawable.aspa);
        }

    }
}