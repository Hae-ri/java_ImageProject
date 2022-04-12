import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class WinImage extends JFrame {

	private JPanel contentPane;
	private ImageIcon image;
	private String FilePath = "C:/LHR/JavaTest/movies";
	private JTable table;
	private String fileName ="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinImage frame = new WinImage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WinImage() {
		setTitle("Pop Image");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//JOptionPane.showMessageDialog(null, "더블클릭");
					// 새 창을 띄워 원본 그림(크기) 보여주기
					WinPopup dialog = new WinPopup(image);
					dialog.setModal(true);
					dialog.setVisible(true);
					
				}
			}
		});
		lblPic.setToolTipText("더블클릭하세요.");
		lblPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblPic.setBounds(0, 0, 100, 100);
		contentPane.add(lblPic);
		
		JButton btnLoadImage = new JButton("\uC774\uBBF8\uC9C0 \uBD88\uB7EC\uC624\uAE30 ...");
		btnLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser(FilePath);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("그림파일", "jpg","gif","png");
				
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int ret = chooser.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getPath();
					FilePath = chooser.getSelectedFile().getParent();
					fileName = path;
					fileName = fileName.replaceAll("\\\\", "/");
					
					// 이미지 크기 조절 
					image = new ImageIcon(path);
					Image pic = image.getImage();
		            pic = pic.getScaledInstance(100,100, Image.SCALE_SMOOTH);
		            ImageIcon imageConv = new ImageIcon(pic);
		            lblPic.setIcon(imageConv); 
				}
			}
		});
		btnLoadImage.setBounds(125, 10, 178, 23);
		contentPane.add(btnLoadImage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 175, 472, 322);
		contentPane.add(scrollPane);
		
		Vector<String> columnName = new Vector<>();
		columnName.add("이름");
		columnName.add("국어");
		columnName.add("영어");
		columnName.add("수학");
		columnName.add("총점");
		columnName.add("평균");
		
		Vector data = new Vector<>();
		for(int j=0; j<100;j++ ) {
			Vector row = new Vector<>();
			for(int i=0; i<6 ; i++) {
				row.add(Integer.toString((int)(Math.random()*101)));
		}
			data.add(row);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data,columnName);
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JButton btnInsert = new JButton("Insert into ...");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
		               Class.forName("com.mysql.cj.jdbc.Driver");
		               Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sampledb?user=root&password=1234");
		               
		               String sql = "insert into imgtbl values ('";
		               sql = sql + fileName + "', LOAD_FILE('" + fileName + "'))";
		               System.out.println(sql);
	               
		               Statement stmt = con.createStatement();
		               if(stmt.executeUpdate(sql) > 0)
		            	   System.out.println("삽입 성공");
		            	   
		            } catch (ClassNotFoundException | SQLException e1) {
		               // TODO Auto-generated catch block
		               e1.printStackTrace();
		            }    
			}
		});
		btnInsert.setBounds(125, 58, 178, 23);
		contentPane.add(btnInsert);
		
		JButton btnSave = new JButton("Save Image");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
		               Class.forName("com.mysql.cj.jdbc.Driver");
		               Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sampledb?user=root&password=1234");
		               
		               String sql = "select image from imgtbl where fname='C:/LHR/JavaTest/movies/4.png' into dumpfile 'C:/LHR/JavaTest/test.png'";
		               //System.out.println(sql);
	               
		               Statement stmt = con.createStatement();
		               stmt.executeQuery(sql);
		            	   
		            } catch (ClassNotFoundException | SQLException e1) {
		               // TODO Auto-generated catch block
		               e1.printStackTrace();
		            }   
				
				
			}
		});
		btnSave.setBounds(125, 109, 178, 23);
		contentPane.add(btnSave);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(table.getRowCount() + "행 " + table.getColumnCount() + "열");
				System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

			}
		});
		
	}
}
