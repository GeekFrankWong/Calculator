package ������;
/*������
**
**
*/
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.lang.*;
import java.net.*;

public class Calculator {
	public static void main(String args[]){
		new MainWindow();
	}
}

class MainWindow extends JFrame{
	private boolean flag = false,flag4 = true;
	private String s,J1temp = "",J4temp = "";
	private JTextArea J4;
	private JTextArea J1;
	private Point point;//��¼����������Ļ������
	private int width,height;//������Ŀ�Ⱥ͸߶�
	
	public MainWindow(){
		super("������ By XJX");
		addKeyListener(new KeyMonitor());//ע������¼�������
		setFocusable(true);
		setLayout(new GridLayout(1,2,2,0));
		JPanel workspace = new JPanel();
		workspace.setBackground(Color.black);
		add(workspace);
		JPanel historyspace = new JPanel();
		historyspace.setBackground(Color.white);
		add(historyspace);
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		JMenuItem aboutItem = new JMenuItem("About...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		fileMenu.add(aboutItem);
		JDialog dialog = new JDialog();
		dialog.setTitle("About");
		dialog.setSize(450, 300);
		dialog.setLayout(new GridLayout(2,1));
		JTextArea J6 = new JTextArea("�汾��2017_05_01_1.0.0\nWritten By XJX \nMy Email: thexjx@gmail.com\n\n��ӭ�����ҵ���ҳ:");
		J6.setFont(new Font("����",Font.BOLD,20));
		J6.setEditable(false);
		dialog.add(J6);
		JPanel J7 = new JPanel();
		J7.setLayout(new GridLayout(4,1));
		J7.setBackground(Color.white);
		dialog.add(J7);
		
		JLabel MyGithub_Label = new JLabel("Github:");
		MyGithub_Label.setFont(new Font("����",Font.BOLD,15));
		final JLabel MyGithub = new JLabel("https://github.com/JiaxinTse");
		MyGithub.setFont(new Font("����",Font.BOLD,15));
		MyGithub.setBackground(Color.white);
		MyGithub.addMouseListener(new InternetMonitor());
		JLabel MyCnBlog_Label = new JLabel("����԰:");
		MyCnBlog_Label.setFont(new Font("����",Font.BOLD,15));
		final JLabel MyCnBlog = new JLabel("http://www.cnblogs.com/journal-of-xjx/");
		MyCnBlog.setFont(new Font("����",Font.BOLD,15));
		MyCnBlog.addMouseListener(new InternetMonitor());
		J7.add(MyGithub_Label);
		J7.add(MyGithub);
		J7.add(MyCnBlog_Label);
		J7.add(MyCnBlog);
		aboutItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					point = MainWindow.this.getLocation();//�������������Ļ������
					width = MainWindow.this.getWidth();
					height = MainWindow.this.getHeight();
					dialog.setLocation(
					        point.x + width/2 - dialog.getWidth()/2, 
					        point.y + height/2 - dialog.getHeight()/2);
					dialog.setVisible(true);
				}
			}
		);
	
		exitItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
					System.exit(0);
				}
			}
		);
		workspace.setLayout(new GridLayout(2,1));
		J1 = new JTextArea(2,10);
		J1.addKeyListener(new KeyMonitor());
		J1.setFont(new Font("����",Font.BOLD,20));
		J1.setVisible(true);
		J1.setEditable(false);
		J1.setLineWrap(true);//�Զ�����
		J1.setText("");
		workspace.add(J1,BorderLayout.NORTH);
		JPanel J2 = new JPanel();
		J2.setLayout(new GridLayout(5,4));
		J2.addKeyListener(new KeyMonitor());
		
		ButtonMonitor buttonMonitor = new ButtonMonitor();
		JButton[] button = new JButton[20]; 
		button[0] = new JButton("(");
		button[1] = new JButton(")");
		button[2] = new JButton("<--");
		button[3] = new JButton("/");
		button[4] = new JButton("7");
		button[5] = new JButton("8");
		button[6] = new JButton("9");
		button[7] = new JButton("*");
		button[8] = new JButton("4");
		button[9] = new JButton("5");
		button[10] = new JButton("6");
		button[11] = new JButton("-");
		button[12] = new JButton("1");
		button[13] = new JButton("2");
		button[14] = new JButton("3");
		button[15] = new JButton("+");
		button[16] = new JButton("_");
		button[17] = new JButton("0");
		button[18] = new JButton(".");
		button[19] = new JButton("=");
		for(int i = 0;i < 20;i++)
		{
			button[i].addActionListener(buttonMonitor);
			button[i].addKeyListener(new KeyMonitor());
			button[i].setFont(new Font("����",Font.BOLD,20));
			J2.add(button[i]);
		}
		workspace.add(J2);
		
		historyspace.setLayout(new BorderLayout());
		JLabel J3 = new JLabel("��ʷ��¼");
		J3.addKeyListener(new KeyMonitor());
		historyspace.add(J3,BorderLayout.NORTH);
		J4 = new JTextArea();
		J4.addKeyListener(new KeyMonitor());
		J4.setFont(new Font("����",Font.BOLD,20));
		J4.setEditable(false);
		J4.setVisible(true);
		J4.setLineWrap(true);//�Զ�����
		J4.setText("");
		historyspace.add(J4,BorderLayout.CENTER);
		JButton J5 = new JButton("���������ʷ��¼");
		J5.addKeyListener(new KeyMonitor());
		ClearAllButtonMonitor Bu = new ClearAllButtonMonitor();
		J5.addActionListener(Bu);
		historyspace.add(J5,BorderLayout.SOUTH);
		setSize(750,600);
		setMinimumSize(new Dimension(400,300));
		setLocationRelativeTo(null);//��ʾ����Ļ����
		setVisible(true);
	}
	//����ƥ�亯��
	boolean Match(String s)
	{
		CharStack S = new CharStack();
		int ptr = 0;
		while (ptr != s.length())
		{
			if (s.charAt(ptr) == '(')
			{
				S.Push(s.charAt(ptr));
				ptr++;
			}
			else if (s.charAt(ptr) == ')')
			{
				if (S.EmptyStack())
				{
					return false;
				}
				else
				{
					S.Pop();
					ptr++;
				}
			}
			else
			{
				ptr++;
			}
		}
		if (S.EmptyStack())  
			return true; 
		else
			return false;
	}
	
	//��ť������,�ü�����Ҫ���Ƕ����ݴ���
	class ButtonMonitor implements ActionListener{	
		public void actionPerformed(ActionEvent e){
		JButton J = (JButton)e.getSource();
		int ptr = 0;
		boolean flag3 = false;
		String t = "";
		if(flag) 
		{
			J1.setText("");
			flag = false;
		}
					
		if(J.getText() == "<--") 
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else 
			{
				J1temp = J1temp.substring(0,J1temp.length()-1);
				J1.setText(J1temp);
			}
		}
		else if(J.getText() == "=") 
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else
			{
				if(J1temp.charAt(J1temp.length()-1) == '/'||
				   J1temp.charAt(J1temp.length()-1) == '*'||
				   J1temp.charAt(J1temp.length()-1) == '-'||
				   J1temp.charAt(J1temp.length()-1) == '+'||
				   J1temp.charAt(J1temp.length()-1) == '.'||
				   J1temp.charAt(J1temp.length()-1) == '(')
				{
					JOptionPane.showMessageDialog(null, "���ʽ�����߼�����", "Error",JOptionPane.WARNING_MESSAGE);
					J4temp = J4.getText();
					J4temp = J4temp + "\n" + J1.getText() + " = " + "ERROR!";
					J4.setText(J4temp);
				}
				else
				{
					J4temp = J4.getText();
					J4temp = J4temp + "\n" + J1.getText() + " = ";
					J4.setText(J4temp);
					s = J1.getText();
					s += '=';//'='Ϊs��β��־
					if(!Match(s))
					{
						JOptionPane.showMessageDialog(null, "���ʽ���Ų�ƥ�䣡", "Error",JOptionPane.WARNING_MESSAGE);
						J4temp = J4.getText();
						J4temp += "ERROR!";
						J4.setText(J4temp);
					}
					else
					{
						Expression expression =  new Expression(s);//ÿ�ΰ���=�Ѽ�������ʾ��J1�����Ұ����һ����ʷ��¼��J4����
						System.out.println(expression.EvaluateExpression());
						t += expression.EvaluateExpression();
						System.out.println(t);
						if( t.charAt(t.length()-1) == '0' && t.charAt(t.length()-2) == '.')
						{
							J1temp = J1.getText();
							J1temp += " = " + expression.EvaluateExpression();
							J1.setText(J1temp);
							J4temp = J4.getText();
							J4temp += (int)expression.EvaluateExpression();
							J4.setText(J4temp);
						}
						else
						{
							J1temp = J1.getText();
							J1temp += " = " + (int)expression.EvaluateExpression();
							J1.setText(J1temp);
							J4temp = J4.getText();
							J4temp += expression.EvaluateExpression();
							J4.setText(J4temp);
						}
						flag = true;
						s = "";
					}
				}
			}
		}
		else if(J.getText() == "_")//���µ��Ǹ���
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else
			{
				ptr = J1temp.length()-1;
				if(J1temp.charAt(ptr)=='('||
				   J1temp.charAt(ptr)==')'||
				   J1temp.charAt(ptr)=='/'||
				   J1temp.charAt(ptr)=='*'||
				   J1temp.charAt(ptr)=='-'||
				   J1temp.charAt(ptr)=='+'||
				   J1temp.charAt(ptr)=='.'){}
				else
				{
					while( (J1temp.charAt(ptr)=='0'||
							J1temp.charAt(ptr)=='1'||
							J1temp.charAt(ptr)=='2'||
							J1temp.charAt(ptr)=='3'||
							J1temp.charAt(ptr)=='4'||
							J1temp.charAt(ptr)=='5'||
							J1temp.charAt(ptr)=='6'||
							J1temp.charAt(ptr)=='7'||
							J1temp.charAt(ptr)=='8'||
							J1temp.charAt(ptr)=='9'||
							J1temp.charAt(ptr)=='.') && ptr >=0 )
				{
					ptr--;
					if( ptr < 0){break;}
				}
				J1temp = J1temp.substring(0, ptr+1) + "(" + "-" + J1temp.substring(ptr+1, J1temp.length()) + ")";
				J1.setText(J1temp);
				}
			}
		}
		else if(J.getText() == "/"||
				J.getText() == "*"||
				J.getText() == "-"||
				J.getText() == "+")
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else
			{
				if(J1temp.charAt(J1temp.length()-1) == '/'||
				   J1temp.charAt(J1temp.length()-1) == '*'||
				   J1temp.charAt(J1temp.length()-1) == '-'||
				   J1temp.charAt(J1temp.length()-1) == '+'||
				   J1temp.charAt(J1temp.length()-1) == '('||
				   J1temp.charAt(J1temp.length()-1) == '.')
				{}//��������Щ���ź������������
				else
				{
					J1temp = J1.getText();
					J1temp += J.getText();
					J1.setText(J1temp);
				}
			}
		}
		else if(J.getText() == ".")
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else
			{
				ptr = J1temp.length()-1;
				while( (J1temp.charAt(ptr)=='0'||
						J1temp.charAt(ptr)=='1'||
						J1temp.charAt(ptr)=='2'||
						J1temp.charAt(ptr)=='3'||
						J1temp.charAt(ptr)=='4'||
						J1temp.charAt(ptr)=='5'||
						J1temp.charAt(ptr)=='6'||
						J1temp.charAt(ptr)=='7'||
						J1temp.charAt(ptr)=='8'||
						J1temp.charAt(ptr)=='9') && ptr >= 0)
						{
							ptr--;
							if(ptr >= 0)
							{
								if(J1temp.charAt(ptr)=='.')
								{
									flag3 = true;
									break;
								}
							}
							else
								break;
						}	
					
				J1temp = J1.getText();
				if(J1temp.charAt(J1temp.length()-1) == '/'||
				   J1temp.charAt(J1temp.length()-1) == '*'||
				   J1temp.charAt(J1temp.length()-1) == '-'||
				   J1temp.charAt(J1temp.length()-1) == '+'||
				   J1temp.charAt(J1temp.length()-1) == '('||
				   J1temp.charAt(J1temp.length()-1) == ')'||
				   J1temp.charAt(J1temp.length()-1) == '.'||flag3)
				{}
				else
				{
					J1temp = J1.getText();
					J1temp += J.getText();
					J1.setText(J1temp);
				}
			}
		}
		else if(J.getText() == "(")
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals(""))
			{
				J1.setText("(");
			}
			else
			{
				if(J1temp.charAt(J1temp.length()-1) == '0' ||
				   J1temp.charAt(J1temp.length()-1) == '1' ||
				   J1temp.charAt(J1temp.length()-1) == '2' ||
				   J1temp.charAt(J1temp.length()-1) == '3' ||
				   J1temp.charAt(J1temp.length()-1) == '4' ||
				   J1temp.charAt(J1temp.length()-1) == '5' ||
				   J1temp.charAt(J1temp.length()-1) == '6' ||
				   J1temp.charAt(J1temp.length()-1) == '7' ||
				   J1temp.charAt(J1temp.length()-1) == '8' ||
				   J1temp.charAt(J1temp.length()-1) == '9' ||
				   J1temp.charAt(J1temp.length()-1) == '.' ||
				   J1temp.charAt(J1temp.length()-1) == ')' ){}
				else
				{
					J1temp += J.getText();
					J1.setText(J1temp);
				}
			}
		}
		else if(J.getText() == ")")
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals("")){}
			else
			{
				if(J1temp.charAt(J1temp.length()-1) == '.' ||
				   J1temp.charAt(J1temp.length()-1) == '/' ||
				   J1temp.charAt(J1temp.length()-1) == '*' ||
				   J1temp.charAt(J1temp.length()-1) == '-' ||
				   J1temp.charAt(J1temp.length()-1) == '+' ||
				   J1temp.charAt(J1temp.length()-1) == '(' ||
				   J1temp == ""){}
				else
				{
					J1temp += J.getText();
					J1.setText(J1temp);
				}
			}
		}
		else
		{
			J1temp = J1.getText();
			if(J1temp==null || J1temp.equals(""))
			{
				J1temp += J.getText();
				J1.setText(J1temp);
			}
			else
			{
				ptr = J1temp.length()-1;
				if(J1temp.charAt(ptr) == ')'){}
				else
				{
					J1temp += J.getText();
					J1.setText(J1temp);
				}
			}
		}
	}
}
	
	class InternetMonitor extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			JLabel JLabel_temp = (JLabel)e.getSource();
			String J_temp = JLabel_temp.getText();
			System.out.println(J_temp);
			URI uri ;
				try {
					uri = new URI(J_temp);
					Desktop desk=Desktop.getDesktop();
					if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
						try {
							desk.browse(uri);
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
					}
				} catch (URISyntaxException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
		}
		public void mouseEntered(MouseEvent e){
			JLabel JLabel_temp = (JLabel)e.getSource();
			JLabel_temp.setForeground(Color.red);
		}
		public void mouseExited(MouseEvent e){
			JLabel JLabel_temp = (JLabel)e.getSource();
			JLabel_temp.setForeground(Color.blue);
		}

}
			
   class ClearAllButtonMonitor implements ActionListener{
				public void actionPerformed(ActionEvent e){
					J4.setText("");
				}
			}
	
	//���̼�����
	class KeyMonitor extends KeyAdapter{
				private int ptr = 0;
				private boolean flag3 = false;
				public void keyPressed(KeyEvent e){
					int key = e.getKeyCode();
					if(flag) 
					{
						J1.setText("");
						flag = false;
					}
					if(key == KeyEvent.VK_BACK_SPACE)
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else 
						{
							J1temp = J1temp.substring(0,J1temp.length()-1);
							J1.setText(J1temp);
						}
					}
					if(key == KeyEvent.VK_EQUALS || key == KeyEvent.VK_ENTER)
					{
						J1temp = J1.getText();
						if(J1temp.charAt(J1temp.length()-1) == '/'||
						   J1temp.charAt(J1temp.length()-1) == '*'||
						   J1temp.charAt(J1temp.length()-1) == '-'||
						   J1temp.charAt(J1temp.length()-1) == '+'||
						   J1temp.charAt(J1temp.length()-1) == '.'||
						   J1temp.charAt(J1temp.length()-1) == '(')
						{
							JOptionPane.showMessageDialog(null, "���ʽ�����߼�����", "Error",JOptionPane.WARNING_MESSAGE);
							J4temp = J4.getText();
							J4temp += "ERROR!";
							J4.setText(J4temp);
						}
						else
						{
							J4temp = J4.getText();
							J4temp = J4temp + "\n" + J1.getText() + " = ";
							J4.setText(J4temp);
							s = J1.getText();
							s += '=';	//'='Ϊs��β��־
							if(!Match(s))
							{
								JOptionPane.showMessageDialog(null, "���ʽ���Ų�ƥ�䣡", "Error",JOptionPane.WARNING_MESSAGE);
								J4temp = J4.getText();
								J4temp += "ERROR!";
								J4.setText(J4temp);
							}
							else
							{
								Expression expression =  new Expression(s);	//ÿ�ΰ���=�Ѽ�������ʾ��J1�����Ұ����һ����ʷ��¼��J4����,
								if( (int)( expression.EvaluateExpression() * 10 )%10 != 0)	//ȥ��.0
								{
									J1temp = J1.getText();
									J1temp += " = " + expression.EvaluateExpression();
									J1.setText(J1temp);
									J4temp = J4.getText();
									J4temp += expression.EvaluateExpression();
									J4.setText(J4temp);
								}
								else
								{
									J1temp = J1.getText();
									J1temp += " = " + (int)expression.EvaluateExpression();
									J1.setText(J1temp);
									J4temp = J4.getText();
									J4temp += (int)expression.EvaluateExpression();
									J4.setText(J4temp);
								}
								flag = true;	 //ע���ڰ��µȺ�ʱҪ�жϱ��ʽ�Ƿ���ȷ��ֻ����ȷʱ��ִ����������
								s = "";
							}
						}
					}
					if( key == KeyEvent.VK_0 ||
						key == KeyEvent.VK_1 ||
						key == KeyEvent.VK_2 ||
						key == KeyEvent.VK_3 ||
						key == KeyEvent.VK_4 ||
						key == KeyEvent.VK_5 ||
						key == KeyEvent.VK_6 ||
						key == KeyEvent.VK_7 ||
						key == KeyEvent.VK_8 ||
						key == KeyEvent.VK_9 )
					{
						J1temp = J1.getText();
						J1temp += (char)key;
						J1.setText(J1temp);
					}
					if(key == KeyEvent.VK_PERIOD )   //С����
					{
						J1temp = J1.getText();
						ptr = J1temp.length()-1;
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9') && ptr >= 0)
								{
									ptr--;
									if(ptr >= 0)
									{
										if(J1temp.charAt(ptr)=='.')
										{
											flag3 = true;
											break;
										}
									}
									else
										break;
								}	
				
						J1temp = J1.getText();
						if(J1temp.charAt(J1temp.length()-1) == '/'||
						   J1temp.charAt(J1temp.length()-1) == '*'||
						   J1temp.charAt(J1temp.length()-1) == '-'||
						   J1temp.charAt(J1temp.length()-1) == '+'||
						   J1temp.charAt(J1temp.length()-1) == '('||
						   J1temp.charAt(J1temp.length()-1) == ')'||
						   J1temp.charAt(J1temp.length()-1) == '.'||flag3)
						{}
						else
						{
							J1temp = J1.getText();
							J1temp += (char)key;
							J1.setText(J1temp);
						}
					}
					if(key == KeyEvent.VK_DELETE) //����˰�ť���J1��s)
					{
						J1.setText("");
						s = "";
					}
				}
			}
}	