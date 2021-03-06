package com.fran.xogadores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * Primer atributo que crea la conexion necesaria para la base. Segundo y tercer
 * atributo tambien sirve para la conexion con la base pero para sacar los
 * resultados de la base. Creamos un atributo para cargar la ruta de la base y
 * usarla sin problema en diferentes ordenadores, sistemas operativos, etc.
 * Creamos el arraylist de la clase jugador para guardar los id y la
 * puntuacion.Por ultimo un DefaultTableModel donde se meteran los datos de la
 * base.
 *
 * @author Fran & Luis
 */
public class Puntuacion {

    public Connection connect;
    ResultSet rs;
    Statement s;
    private String ruta = "Puntuacion.db";
    ArrayList<Xogador> lista = new ArrayList();
    DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Constructor predeterminado.
     */
    public Puntuacion() {
    }

    /**
     * Metodo conectar que solo ejerce la conexion con la base.
     */
    public void conectar() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:" + ruta);
            if (connect != null) {
                System.out.println("Base conectada.");
            }
        } catch (SQLException sqe1) {
            System.out.println("Erro:" + sqe1.getMessage());
        }
    }

    /**
     * Metodo que fai un select naa base e o garda nun arraylist .
     *
     * @return ArrayList no que se almacena.
     */
    public ArrayList cargarArray() {
        try {
            s = connect.createStatement();
            rs = s.executeQuery("select * from xogador order by puntos desc;");
            while (rs.next()) {
                lista.add(new Xogador(rs.getString(1), rs.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Puntuacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     * Metodo que garda os datos da consulta nunha taboa.
     */
    public void consultar() {
        modelo.setColumnCount(0);
        modelo.setRowCount(0);
        modelo.addColumn("ID");
        modelo.addColumn("Points");
        try {
            s = connect.createStatement();
            rs = s.executeQuery("select * from xogador order by puntos desc;");
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString(1), rs.getInt(2)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Puntuacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de pechar a conexion coa base.
     */
    public void pechar() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Puntuacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
