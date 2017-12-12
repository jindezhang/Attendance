package UI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UItest extends JFrame implements ActionListener{
 JButton button = new JButton();
 public UItest(){
  button.addActionListener(this); //添加监视器
  button.setBounds(100, 100, 150, 50);
  button.setText("点击");
  this.add(button);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //设置按关闭键即可关闭窗体
  this.setSize(400,300);
  this.getContentPane().setLayout(null);
  this.setVisible(true);  //可视化
 }
 public void actionPerformed(ActionEvent e) {
  this.setVisible(false);  //窗体不可见
  new Frame2();  //创建新的窗体，以达到切换窗体的效果
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