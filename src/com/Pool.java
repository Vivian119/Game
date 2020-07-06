package com;

import javax.swing.*;
import java.awt.*;

public class Pool extends JPanel implements Runnable{
	//  生死状态变量
	private static final int DEAD = 0;
	private static final int ALIVE = 1 ;
// 参数的初步设计
	private final int rows;
	private final int columns;
	private int speed = 8;
	private static int[][] shape ;
	private static int[][] zero ;
	private static int[][] pauseshape ;

	private int[][] currentGeneration;
	private int[][] nextGeneration;
// 更改标志
	private volatile boolean isChanging = false;
// 构造方法的实际是初始化各种数据
	public Pool(int rows, int columns) {
		this.rows = rows;
		this.columns=columns;

		shape = new int[rows][columns] ;
		zero = new int[rows][columns] ;
		pauseshape = new int[rows][columns] ;

		int[][] generation1 = new int[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				generation1[i][j] = DEAD;
			}
		}
		int[][] generation2 = new int[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				generation2[i][j]= ALIVE;
			}
		}
		currentGeneration = generation1;
		nextGeneration = generation2;
	}

	public void transfrom(int[][] generation, int[][] pauseshape) {
		for(int i = 0;i < rows;i++) {
			if (columns >= 0) {
				System.arraycopy(generation[i], 0, pauseshape[i], 0, columns);
			}
		}
	}
	/*
	重写run方法，线程执行方案
	 */
	public void run() {
		long start = System.currentTimeMillis();
//		10分钟后停止演化
		while ((System.currentTimeMillis() - start) <= 10 * 60 * 1000) {
			synchronized (this) {
				while (isChanging) {
					try {
						this.wait();//线程等待
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				sleep(speed);//线程挂起
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						evolve(i, j);//计算细胞的生死状态
					}
				}
				int[][] temp = currentGeneration;
				currentGeneration = nextGeneration;
				nextGeneration = temp;
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						nextGeneration[i][j] = DEAD;
					}
				}
				transfrom(currentGeneration, pauseshape);
				repaint();//重新绘制面板
			}
		}
	}


	public void changeSpeedSlow() {
		speed=80;
	}
	public void changeSpeedFast() {
		speed=30;
	}
	public void changeSpeedHyper() {
		speed=1;
	}
//	画窗口
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(currentGeneration[i][j]== ALIVE) {
					g.fillRect(j*10, i*10, 10, 10);//方格的大小
				} else {
					g.drawRect(j*10, i*10, 10, 10);//方格的大小
				}
			}
		}
	}
	public void setShape() {
		setShape(shape);
	}
//	随机分布
	public void setRandom() {
		
//				返回伪随机的，均匀分布 int值介于0（含）和指定值（不包括），从该随机数生成器的序列绘制。
		
		setShapetemp(shape);
	}
	// 初始测试状态2：打枪
	public void setInit_2(){
		shape[1][25] = pauseshape[1][25] = ALIVE;
		shape[2][25] = pauseshape[2][25] = ALIVE;
		shape[2][23] = pauseshape[2][23] = ALIVE;
		shape[3][13] = pauseshape[3][13] = ALIVE;
		shape[3][14] = pauseshape[3][14] = ALIVE;
		shape[3][21] = pauseshape[3][21] = ALIVE;
		shape[3][22] = pauseshape[3][22] = ALIVE;
		shape[3][35] = pauseshape[3][35] = ALIVE;
		shape[3][36] = pauseshape[3][36] = ALIVE;
		shape[4][12] = pauseshape[4][12] = ALIVE;
		shape[4][16] = pauseshape[4][16] = ALIVE;
		shape[4][21] = pauseshape[4][21] = ALIVE;
		shape[4][22] = pauseshape[4][22] = ALIVE;
		shape[4][35] = pauseshape[4][35] = ALIVE;
		shape[4][36] = pauseshape[4][36] = ALIVE;
		shape[5][1] = pauseshape[5][1] = ALIVE;
		shape[5][2] = pauseshape[5][2] = ALIVE;
		shape[5][11] = pauseshape[5][11] = ALIVE;
		shape[5][17] = pauseshape[5][17] = ALIVE;
		shape[5][21] = pauseshape[5][21] = ALIVE;
		shape[5][22] = pauseshape[5][22] = ALIVE;
		shape[6][1] = pauseshape[6][1] = ALIVE;
		shape[6][2] = pauseshape[6][2] = ALIVE;
		shape[6][11] = pauseshape[6][11] = ALIVE;
		shape[6][15] = pauseshape[6][15] = ALIVE;
		shape[6][17] = pauseshape[6][17] = ALIVE;
		shape[6][18] = pauseshape[6][18] = ALIVE;
		shape[6][23] = pauseshape[6][23] = ALIVE;
		shape[6][25] = pauseshape[6][25] = ALIVE;
		shape[7][11] = pauseshape[7][11] = ALIVE;
		shape[7][17] = pauseshape[7][17] = ALIVE;
		shape[7][25] = pauseshape[7][25] = ALIVE;
		shape[8][12] = pauseshape[8][12] = ALIVE;
		shape[8][16] = pauseshape[8][16] = ALIVE;
		shape[9][13] = pauseshape[9][13] = ALIVE;
		shape[9][14] = pauseshape[9][14] = ALIVE;
		setShapetemp(shape);
	}
// 初始测试状态：魔鬼行走
	public void setInit_1(){
		for(int i = 0;i < rows; i++) {
			for(int j = 0;j < columns;j++) {
				if((i == 4 && j == 4)||(i == 5 && j == 5)||
					(i == 5 && j == 6)||(i == 6 && j == 4)||
					(i == 6 && j == 5)){
				    shape[i][j] = ALIVE;
				}
				else {
					shape[i][j] = DEAD;
				}
				pauseshape[i][j] = shape[i][j] ;
			}
		}
		setShapetemp(shape);
	}
//	初始化数组为zero[] = {0,0,0,...,0}；
// ****************************************************

	public void setZero() {
		


	}
// ****************************************************
	/*停止，恢复初始状态*/
	public void setStop() {
		setZero();
		shape = zero;
		setShape(shape);
		pauseshape = shape;
	}
	/*****暂停******/
	public void setPause() {
		shape=pauseshape;
		setShapetemp(pauseshape);
	}
	/******交换*******/
	private void setShapetemp(int [][]shape) {
		isChanging = true;
		synchronized (this) {
			for (int i = 0 ; i < rows ; i ++ ) {
				for (int j = 0 ; j < columns ; j ++ ) {
					currentGeneration[i][j] = (shape[i][j] == ALIVE) ? ALIVE : DEAD;
				}
			}
			repaint();
		}
	}
	private void setShape(int [][]shape) {
		isChanging = true;
		synchronized(this) {
			for (int i = 0 ; i < rows ; i ++ ) {
				for (int j = 0 ; j < columns ; j ++ ) {
					currentGeneration[i][j] = (shape[i][j] == ALIVE) ? ALIVE : DEAD;
				}
			}
			isChanging=false;
			this.notifyAll();
		}
	}
// ************************************************************************
/**
 * 计算该位置的细胞下一代生死状态，主要算法设计部分
 * @param x
 * @param y
*/ 
	public void evolve(int x,int y) {
		


	}
// *********************************************************************** 
/**
 * 判断该细胞是不是不再地图中
 * @param x
 * @param y
 * @return flag
 */
	private boolean isVaildCell(int x,int y) {
		return (x >= 0) && (x < rows) && (y >= 0) && (y < columns);
	}

	private void sleep(int x) {
		try {
			Thread.sleep(8*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
}