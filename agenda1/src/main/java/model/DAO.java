package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	/** Modulo de conexão **/

	// Parametro de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "41526389a";

	// Metodo de conexao
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	/* CRUD CREATE */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome, fone, email) values (?, ?, ?)";
		try {
			// abrir conexao com o bd
			Connection con = conectar();

			// Preparar a querry para execuçao no bd
			PreparedStatement pst = con.prepareStatement(create);

			// Substituir os parâmetros (?) pelo conteudo do javabeans);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// execitar a query
			pst.executeUpdate();
			// Encerrar a conexao com o bd
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/* CRUD READ */
	public ArrayList<JavaBeans> listarContatos() {
		// Criando obj para acessar o javaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// enquanto houver contatos
			while (rs.next()) {
				// vatiaveis aux que recebem dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o arrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

}
