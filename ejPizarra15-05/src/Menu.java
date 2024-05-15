import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField salarField;
	private JTextField comField;
	private JTextField elimField;
	private JTextField modNumField;
	private JTextField modSalField;
	private Conexion conn = new Conexion("departamentos");
	private Connection connection = conn.conectarMySQL();
	private PreparedStatement pstm;
	/**
	 * Create the frame.
	 */
	public Menu() {
		
		//No le hagais ni puto caso a esto
		setTitle("Gestion de empleados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 771, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	
		nameField = new JTextField();
		nameField.setBounds(98, 41, 96, 19);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNombre.setBounds(10, 39, 80, 16);
		contentPane.add(lblNombre);
		
		salarField = new JTextField();
		salarField.setColumns(10);
		salarField.setBounds(98, 82, 96, 19);
		contentPane.add(salarField);
		
		JLabel lblSalario = new JLabel("Salario");
		lblSalario.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSalario.setBounds(10, 80, 60, 16);
		contentPane.add(lblSalario);
		
		comField = new JTextField();
		comField.setColumns(10);
		comField.setBounds(98, 123, 96, 19);
		contentPane.add(comField);
		
		JLabel lblComision = new JLabel("Comision");
		lblComision.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblComision.setBounds(10, 121, 80, 16);
		contentPane.add(lblComision);
		
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNumero.setBounds(52, 231, 80, 16);
		contentPane.add(lblNumero);
		
		elimField = new JTextField();
		elimField.setColumns(10);
		elimField.setBounds(36, 257, 96, 19);
		contentPane.add(elimField);
		
		JLabel lblNumeroMod = new JLabel("Numero");
		lblNumeroMod.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNumeroMod.setBounds(221, 231, 80, 16);
		contentPane.add(lblNumeroMod);
		
		modNumField = new JTextField();
		modNumField.setColumns(10);
		modNumField.setBounds(205, 257, 96, 19);
		contentPane.add(modNumField);
		
		
		
		JLabel lblSalar = new JLabel("Salario");
		lblSalar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSalar.setBounds(341, 231, 80, 16);
		contentPane.add(lblSalar);
		
		modSalField = new JTextField();
		modSalField.setColumns(10);
		modSalField.setBounds(325, 257, 96, 19);
		contentPane.add(modSalField);
		
		JLabel lblListado = new JLabel(" ");
		lblListado.setHorizontalAlignment(SwingConstants.CENTER);
		lblListado.setVerticalAlignment(SwingConstants.TOP);
		lblListado.setBounds(484, 41, 241, 281);
		contentPane.add(lblListado);
		
		JLabel lblInsertMessage = new JLabel("");
		lblInsertMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInsertMessage.setBounds(233, 41, 241, 124);
		contentPane.add(lblInsertMessage);
		
		//En esto si teneis que hacer caso
		JButton btnModificarSalario = new JButton("Modificar salario");
		btnModificarSalario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numero = modNumField.getText();
				String salario = modSalField.getText();
				/*Si queremos hacer mas de una query no podemos ponerlas todas en un string y a chuparla
				 *Hay que ponerlas en strings separados y ejecutar una antes que la otra 
				 */
				String query = "Update empleados set salario =  "+salario+" where NumeroEmpleado = " +numero+";";
				String query2 = "Select nombre, salario from empleados where NumeroEmpleado = "+numero+";";
				try {
					/*
					 * Con el PreparedStatement pstm le metemos primero la segunda query, que es la que nos muestra
					 * el nombre y el salario del notas ese que hemos pedido en el menu
					 */
					pstm = connection.prepareStatement(query2);
					
					/*
					 * Como esta query nos devuelve por lo menos una fila tenemos que usar un resultset
					 */
					
					ResultSet resultMessage = pstm.executeQuery();
					
					/*
					 * con el metodo .next() lo que hacemos es movernos de fila. El ResultSet siempre empieza
					 * en un limbo raro y hay que llamar a ese metodo pa q nos ponga en la primera fila
					 */	
					resultMessage.next();
					
					/*
					 *Con el .getString() nos llevamos el valor que tenga esa columna, esto no es un array, aqui se empieza
					 *por el numero 1 porque no estamos malitos de la cabeza
					 *En este caso el numero 1 es el nombre y el 2 es el salario 
					 */
					
					String resultName = resultMessage.getString(1);
					String resultSalar = resultMessage.getString(2);
					
					int elegido = JOptionPane.showConfirmDialog(contentPane, "<html>El salario actual del empleado \"" +resultName + "\" es de:<br>"+resultSalar+"<br> Desea modificarlo?");
					if(elegido ==0) {
						/*
						 * Aqui ya el usuario ha dicho que si, que le cambiemos el sueldo al tontopolla este, asi que ejecutamos
						 * la primera query
						 */
						pstm = connection.prepareStatement(query);
						
						/*
						 * Pa tirar un update/insert/delete hay que usar .executeUpdate(String query) en vez de .executeQuery()
						 * ¿Por que? Y yo que coño se, a mi me ha petado con el .executeQuery() y he probado con esto y me funciona
						 * Ahora yo creo que podeis leer el resto del codigo y mas o menos os enteraréis supongo
						 */
						int resultado = pstm.executeUpdate(query);
						JOptionPane.showMessageDialog(contentPane, "El salario del empleado \""+resultName+"\" es de " +salario, "Modificar salario", 2);
					}else {
						JOptionPane.showMessageDialog(contentPane, "El salario del empleado \""+resultName+"\" se mantendra intacto", "Modificar salario", 2);
					}
				}catch (Exception e1) {
					
				}
				
			}
		});
		btnModificarSalario.setBounds(205, 301, 153, 21);
		contentPane.add(btnModificarSalario);
		
		JButton btnListar = new JButton("Listar empleados personal");
		btnListar.setBounds(515, 10, 207, 45);
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblListado.setText("<html>");
				String query = "Select nombre from empleados where departamento = 2";
				try {
					pstm = connection.prepareStatement(query);
					ResultSet resultado = pstm.executeQuery();
					while(resultado.next()) {
						lblListado.setText(lblListado.getText()+"<br>"+resultado.getString(1));
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				lblListado.setText(lblListado.getText()+"</html>");
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnListar);
		
		
		JButton btnEliminarEmpleado = new JButton("Eliminar empleado");
		btnEliminarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "Delete from empleados where NumeroEmpleado = "+ elimField.getText()+";";
				String query2 = "Select nombre from empleados where NumeroEmpleado = "+ elimField.getText()+";";
				try {
					pstm = connection.prepareStatement(query2);
					ResultSet resultMessage = pstm.executeQuery();
					resultMessage.next();
					String resultName = resultMessage.getString(1);
					int elegido = JOptionPane.showConfirmDialog(btnEliminarEmpleado, "<html>Esta seguro de que desea eliminar al empleado \"" +resultName + "\"?");
					if(elegido ==0) {
						pstm = connection.prepareStatement(query);
						int resultado = pstm.executeUpdate(query);
					}else {
						JOptionPane.showMessageDialog(btnEliminarEmpleado, "El empleado \""+resultName+"\" no se eliminara", "Eliminar empleado", 2);
					}
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminarEmpleado.setBounds(10, 301, 153, 21);
		contentPane.add(btnEliminarEmpleado);
		
		JButton btnInsertarEmpleado = new JButton("Insertar empleado");
		btnInsertarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = "\""+nameField.getText()+"\"";
				String salario = salarField.getText();
				String comision = comField.getText();
				String query = "Insert into empleados (Nombre, Salario, Comision, Departamento) VALUES ("+nombre+", "+salario+", " +comision+", 1);";
				try {
					pstm = connection.prepareStatement(query);
					int resultado = pstm.executeUpdate(query);
					lblInsertMessage.setText("<html> Empleado insertado<br> con exito</html>");
				} catch (Exception e1) {e1.printStackTrace();}
			}
		});
		btnInsertarEmpleado.setBounds(10, 173, 153, 21);
		contentPane.add(btnInsertarEmpleado);
	}
}
