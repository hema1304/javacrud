package jc;
import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class jc {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the applicatio
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jc window = new jc();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
//	Form Constructor
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public jc() {
		initialize();
		Connect();
		table_load();
		
	}
//   Establish the database connection
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud","root","");
		}catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
//	View Records
	public void table_load() {
		try{
			pst=con.prepareStatement("select * from book");
			rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 802, 677);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book shop");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel.setBounds(307, 29, 170, 60);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(22, 118, 335, 313);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(26, 46, 106, 28);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Edition");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(26, 108, 106, 28);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(26, 174, 106, 28);
		panel.add(lblNewLabel_1_1);
		
		txtbname = new JTextField();
		txtbname.setBounds(162, 55, 143, 28);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(162, 117, 143, 28);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(162, 183, 143, 28);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
//Add Records

				String bname,edition,price;
				
				bname =txtbname.getText();
				edition =txtedition.getText();
				price =txtprice.getText();
				
				try {
					pst=con.prepareStatement("insert into book(name,edition,price) values(?,?,?)");
					pst.setString(1,bname);
					pst.setString(2,edition);
					pst.setString(3,price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record added!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}catch(SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(33, 451, 92, 60);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBackground(Color.RED);
		btnExit.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnExit.setBounds(144, 451, 92, 60);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnClear.setBackground(Color.ORANGE);
		btnClear.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnClear.setBounds(260, 451, 92, 60);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 118, 399, 420);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(22, 550, 344, 80);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Book ID");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(10, 23, 114, 27);
		panel_1.add(lblNewLabel_2);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
//	Search Records		
			public void keyReleased(KeyEvent e) {
				try {
					String id=txtbid.getText();
					pst = con.prepareStatement("select name,edition,price from book where id = ? ");
					pst.setString(1,id);
					ResultSet rs=pst.executeQuery();
					
					if(rs.next()==true) {
						String name=rs.getString(1);
						String edition=rs.getString(2);
						String price=rs.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
						
					}
					else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				}catch(SQLException ex) {
					
				}
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(116, 23, 144, 27);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//	 Update form
				String bname,edition,price,bid;
				
				bname =txtbname.getText();
				edition =txtedition.getText();
				price =txtprice.getText();
				bid=txtbid.getText();
				
				try {
					pst=con.prepareStatement("update book set name=?,edition=?,price=? where id=?");
					pst.setString(1,bname);
					pst.setString(2,edition);
					pst.setString(3,price);
					pst.setString(4,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record updated!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}catch(SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnUpdate.setBackground(SystemColor.textHighlight);
		btnUpdate.setBounds(430, 559, 129, 60);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//	Delete record
				String bid;
				
				bid=txtbid.getText();
				
				try {
					pst=con.prepareStatement("delete from book where id=?");
				
					pst.setString(1,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record ed!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}catch(SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Constantia", Font.PLAIN, 20));
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setBounds(605, 559, 129, 60);
		frame.getContentPane().add(btnDelete);
	}
}
