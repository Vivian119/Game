package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {
	private final Pool world;
	public GameOfLife(int rows, int columns) {
		world = new Pool(rows, columns);
		world.setBackground(Color.LIGHT_GRAY);
		new Thread(world).start();
		add(world);
	}
//	==================================================================================
//	==================================================================================
	public static void main(String[]args) {

		GameOfLife frame = new GameOfLife(75, 150);
		
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
//		添加
		JMenu options = new JMenu("选项");
		menu.add(options);
		JMenu changeSpeed = new JMenu("速度");
		menu.add(changeSpeed);
		
		JMenuItem start=options.add("开始演化");
		start.addActionListener(frame.new StartActionListener());

		JMenuItem init = options.add("初始态1");
		init.addActionListener(frame.new initActionListener());

		JMenuItem init_2 = options.add("初始态2");

		JMenuItem random=options.add("随机分布");
		random.addActionListener(frame.new RandomActionListener());
		
		JMenuItem stop=options.add("停止迭代");
		stop.addActionListener(frame.new StopActionListener());

		JMenuItem pause=options.add("暂停");
		pause.addActionListener(frame.new PauseActionListener());

		JMenuItem slow=changeSpeed.add("慢");
		slow.addActionListener(frame.new SlowActionListener());

		JMenuItem fast=changeSpeed.add("快");

		JMenuItem hyper=changeSpeed.add("相当快");
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,700);
		frame.setTitle("生命游戏");
		frame.setVisible(true);
		frame.setResizable(true);
	}

	class RandomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setRandom();
		}
	}
	class StartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setShape();
		}
	}
	class StopActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setStop();
		}
	}
	class PauseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			
		}
	}
	class SlowActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedSlow();
		}
	}

	private class initActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			world.setBackground(Color.LIGHT_GRAY);
			world.setInit_1();
		}
	}

	
}

