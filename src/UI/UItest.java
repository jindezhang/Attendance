package UI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UItest extends JFrame implements ActionListener{
 JButton button = new JButton();
 public UItest(){
  button.addActionListener(this); //��Ӽ�����
  button.setBounds(100, 100, 150, 50);
  button.setText("���");
  this.add(button);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //���ð��رռ����ɹرմ���
  this.setSize(400,300);
  this.getContentPane().setLayout(null);
  this.setVisible(true);  //���ӻ�
 }
 public void actionPerformed(ActionEvent e) {
  this.setVisible(false);  //���岻�ɼ�
  new Frame2();  //�����µĴ��壬�Դﵽ�л������Ч��
 }
 public static void main(String[] args){
   new UItest();
 }
 private class Frame2 extends JFrame{
  public Frame2(){
   JTextField text = new JTextField();
   this.add(text);
   this.setSize(600, 480);
   this.setVisible(true);
   }
 }
}