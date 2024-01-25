package dam.birt.eus;

import java.sql.*;
import java.util.Scanner;

public class OfficialLanguagesReport {
    public static void main(String[] args) throws Exception {
        try {

            // Nos conectamos a la BD y creamos un PreparedStatement
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/world",
                "root",
                "root"
            );

            PreparedStatement pstmt = con.prepareStatement(
                "select country.name, countrylanguage.language, countrylanguage.percentage from country join countrylanguage on country.code = countrylanguage.countrycode where countrylanguage.isofficial = 'T' and country.continent like ? order by country.name"
            );

            System.out.println("Introduce el nombre del continente para ver sus idiomas oficiales:");

            Scanner sc = new Scanner(System.in);
            String continente = sc.nextLine();

            pstmt.setString(1, continente);
            
            // Ejecutamos la sentencia SQL y obtenemos los metadatos del resultado para la cabecera de nuestra tabla
            ResultSet res = pstmt.executeQuery();

            // Imprimimos en pantalla los resultados
            System.out.printf(
                "%-21s %-18s %-15s%n",
                "País",
                "Idioma Oficial",
                "% Hablantes"
            );
            for (int i = 0; i < 52; i++) {
                System.out.print("-");
            }
            System.out.println();
            while (res.next()) {
                System.out.printf(
                    "%-21s %-18s %-15s%n",
                    res.getString(1),
                    res.getString(2),
                    res.getString(3) + "%"
                );
            }
            res.close();
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
