package calculator.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Calculator extends JFrame
{
	JButton []btn=new JButton[20];
	String []Btn= {"1","2","3","+","C","4","5","6","-","←","7","8","9","*","1/X","0","+/-",".","/","="};
	JSplitPane sp;
	JTextArea Inbox=new JTextArea(2,305);
	JTextField Outbox=new JTextField(1);
	MyAction a=new MyAction();
	Calculator() 
	{
		this.setTitle("计算器");
		this.setSize(320,200);
		JPanel box=new JPanel();
		JPanel button=new JPanel();
		sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,box,button);
		sp.setDividerLocation(60);
		sp.setEnabled(false);
		sp.setDividerSize(0);
		Outbox.setHorizontalAlignment(JTextField.RIGHT);
		Outbox.setBorder(null);
		box.add(Inbox);
		box.add(Outbox);
		Inbox.setBounds(0,0,305,40);
		Outbox.setBounds(0,40,305,20);
		box.setLayout(null);
		for(int i=0;i<Btn.length;i++) 
		{
			btn[i]=new JButton(Btn[i]);
			//System.out.println(i+" "+ [i]+" "+btn[i].getText());
			button.add(btn[i]);
		}
		button.setLayout(new GridLayout(4,5,2,2));
		this.add(sp);
		JButton []b= {btn[0],btn[1],btn[2],btn[3],btn[5],btn[6],btn[7],btn[8],btn[10],btn[11],btn[12],btn[13],btn[15],btn[17],btn[18]};
		for(int j=0;j<b.length;j++)ReadButton(b[j]);	
		JButton []fb= {btn[4],btn[9],btn[14],btn[16],btn[19]};
		for(int k=0;k<fb.length;k++)fb[k].addActionListener(a);
		
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	class MyAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==btn[4])//清除
			{
				Inbox.setText("");
				Outbox.setText("");
			}
			else if(e.getSource()==btn[9])//退格
			{
				if(!Inbox.getText().equals(""))
					Inbox.setText(Inbox.getText().substring(0, Inbox.getText().length()-1));
			}
			else if(e.getSource()==btn[14])//求一个数的倒数
			{
				String str=Inbox.getText();
				try {
					if(str.equals(""))System.out.println("输入框为空");
					else if(str.contains("."))
					{						
						Outbox.setText("error");
						System.out.println("小数不支持求倒数");	
					}
					else if(str.contains("/"))
					{
						String[] astr=str.split("\\/");
						int fz=Integer.parseInt(astr[0]);
						int fm=Integer.parseInt(astr[1]);
						Outbox.setText(fm+"/"+fz);
					}
					else
					{
						int a=Integer.parseInt(str);
						if(a!=0)Outbox.setText("1/"+a);
						else
						{
							Outbox.setText("error");
							System.out.println("零没有倒数");
						}
					}
				}catch(Exception E)
				{
					Outbox.setText("error");
					System.out.println("输入错误导致计算失败");
				}
			}
			else if(e.getSource()==btn[16])//负数
			{
				String str=Inbox.getText();
				try {
					if(str.equals(""))System.out.println("输入框为空");
					else if(str.contains("."))
					{
						double b=Double.parseDouble(str);
						b=-b;
						Inbox.setText(""+b);
					}
					else
					{
						int a=Integer.parseInt(str);
						a=-a;
						Inbox.setText(""+a);
					}
				}catch(Exception E)
				{
					Outbox.setText("error");
					System.out.println("输入错误导致计算失败");
				}
			}
			else if(e.getSource()==btn[19])//等于
			{
				String str = Inbox.getText();
				if(str.equals(""))
				{
					Outbox.setText("0");
				}
				else {
					Object o = null;
					try {
						o = compute(str);
						String s=o.toString();
						Outbox.setText(s);
						System.out.println("计算成功");
					} catch (Exception E) {
						Outbox.setText("input error");
						System.out.println("输入错误导致计算失败");
//						E.printStackTrace();
					}	
				}
			}
		}
	}
	//定义读取按钮方法
	public void ReadButton(JButton button)
	{
			button.addActionListener(new ActionListener()
			{
				/* （非 Javadoc）
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) 
				{
					String str = button.getText();
					Inbox.append(str);
					//Inbox.setText(Inbox.getText()+str);
				}
	        });
	}
	//定义计算方法        eval() 函数可将字符串转换为代码执行
	public  Object compute(String s) throws ScriptException 
	{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			Object result = engine.eval(s);
			return  result;
	}

	public static void main(String[] args)
	{
		new Calculator();
	}
}