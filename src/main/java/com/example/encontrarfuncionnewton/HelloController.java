package com.example.encontrarfuncionnewton;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class HelloController {
    @FXML private TextField tfPuntos,tfValor;
    @FXML private Label labelResultado,lblResultadoValor;
    boolean esX =true;
    String funcion;
    @FXML private LineChart<Number, Number> lcGrafica;

    public double calcularExpresion(double valor){

        Expression expression = new ExpressionBuilder(funcion)
                .variables("x")
                .build();
        expression.setVariable("x",valor);
        return expression.evaluate();
    }

    public void btnCalcularValor(){
        if (funcion!=null&&!tfValor.getText().equals("")){
            lblResultadoValor.setText(calcularExpresion(Double.parseDouble(tfValor.getText()))+"");
        }
    }
    public void btnCalcularFuncion() {
        funcion=null;
        String textoPuntos=tfPuntos.getText();//(-2,9)(0,11)(1,-18)(2,-23)
        textoPuntos=textoPuntos.replace(")",",");
        textoPuntos= textoPuntos.replace("(","");
        final String[] arrregloPuntosIniciales=textoPuntos.split(",");
        double[][] arregloPuntosEnteros = new double[2][arrregloPuntosIniciales.length/2];
        int valorYdelarreglo=0;
        int numeroArregloPInicialesX = 0;
        for (int i =0;i<(arrregloPuntosIniciales.length);i++){//convierte el arreglo string a double
            if (esX){
                arregloPuntosEnteros[0][numeroArregloPInicialesX]=Double.parseDouble(arrregloPuntosIniciales[i]);
            }else {
                arregloPuntosEnteros[1][numeroArregloPInicialesX]=Double.parseDouble(arrregloPuntosIniciales[i]);
                numeroArregloPInicialesX++;
            }
            esX =!esX;
            valorYdelarreglo=i;
        }//fin conversion

        valorYdelarreglo=(valorYdelarreglo+1)/2;
        double[][] arregloB=new double[valorYdelarreglo][valorYdelarreglo];
        int contador=valorYdelarreglo;
        for (int x=0;x<valorYdelarreglo;x++){
            for (int y=0; y<valorYdelarreglo;y++){
                if(x==0){
                    arregloB[0][y]=arregloPuntosEnteros[1][y];
                }else if(y<contador) {
                    System.out.println("( "+arregloB[x-1][y+1]+"-"+ arregloB[x-1][y]+") / (" +arregloPuntosEnteros[0][x+y]+"-"+arregloPuntosEnteros[0][y]+")");
                    arregloB[x][y]=(arregloB[x-1][y+1]-arregloB[x-1][y])/(arregloPuntosEnteros[0][x+y]-arregloPuntosEnteros[0][y]);
                }
            }
            contador--;
        }
        String funcion=arregloB[0][0]+"";
        String x="";
        XYChart.Series series2 = new XYChart.Series();
        for (int i=1;i<valorYdelarreglo;i++){
            x=x+"(x" + (arregloPuntosEnteros[0][i-1]<0 ? "+"+arregloPuntosEnteros[0][i-1]*(-1) : "-"+arregloPuntosEnteros[0][i-1])+")";
            funcion=funcion+ (arregloB[i][0]>0 ? " + "+arregloB[i][0] : " - "+arregloB[i][0]*(-1) ) +  x ;
            //series2.getData().add(new XYChart.Data(arregloPuntosEnteros[0][i-1], arregloPuntosEnteros[1][i-1]));
        }
        //series2.getData().add(new XYChart.Data(arregloPuntosEnteros[0][valorYdelarreglo-1], arregloPuntosEnteros[1][valorYdelarreglo-1]));
        
        labelResultado.setText(funcion);
        this.funcion=funcion;
        XYChart.Series series = new XYChart.Series();

        for (int i=-100;i<=100;i++){

            series.getData().add(new XYChart.Data(i, calcularExpresion(i)));
        }
        //lcGrafica.getData().add(series2);
        lcGrafica.getData().add(series);


    }
}
