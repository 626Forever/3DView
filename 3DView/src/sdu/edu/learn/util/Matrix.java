package sdu.edu.learn.util;

public class Matrix {
	private int m_cols;
	private int m_rows;
	private float m_data[];

	public Matrix() {
		m_cols = m_rows = 0;
		m_data = new float[m_cols * m_rows];
	}

	public Matrix(int r, int c) {
		m_cols = c;
		m_rows = r;
		m_data = new float[m_cols * m_rows];
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				m_data[i * m_cols + j] = 0;
			}
		}
	}

	public Matrix(float ar[], int c) {
		m_cols = c;
		m_rows = ar.length / c;
		m_data = new float[ar.length];
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				m_data[i * m_cols + j] = ar[i * m_cols + j];
			}
		}
	}

	// //////////////////////////////////////////
	// debug
	public void showMatrix() {
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				System.out.println(m_data[i * m_cols + j] + "\t");
			}
			System.out.println("\n");
		}
	}

	// ////////////////////////////////////////
	public float getElement(int r, int c) {
		return m_data[r * c + c];
	}

	public void setElement(int r, int c, float e) {
		m_data[r * c + c] = e;
	}
	
	public float[] getData(){
		return m_data;
	}

	public void setMatrix(float ar[]) {
		if (m_cols == 0 || m_rows == 0) {
			return;
		}
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				m_data[i * m_cols + j] = ar[i * m_cols + j];
			}
		}
	}

	public Matrix matrixAdd(Matrix m) {
		Matrix temp = new Matrix(m_rows, m_cols);
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				temp.setElement(i, j,
						this.getElement(i, j) + m.getElement(i, j));
			}
		}
		return temp;
	}

	public Matrix matrixSub(Matrix m) {
		Matrix temp = new Matrix(m_rows, m_cols);
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				temp.setElement(i, j,
						this.getElement(i, j) - m.getElement(i, j));
			}
		}
		return temp;
	}

	public Matrix matrixProduct(Matrix m) {
		Matrix temp = new Matrix(m_rows, m.m_cols);
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m.m_cols; j++) {
				float sum = 0;
				for (int k = 0; k < m_cols; k++) {
					sum += m_data[i * m_cols + k] * m.m_data[k * m.m_rows + j];
				}
				temp.m_data[i * m_rows + j] = sum;
			}
		}
		return temp;
	}

	public Matrix matrixTrans() {
		Matrix temp = new Matrix(m_cols, m_rows);
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				temp.m_data[j * m_rows + i] = m_data[i * m_cols + j];
			}
		}
		return temp;
	}

	public Matrix matrixInv() {
		if (m_cols != m_rows) {
			return this;
		}

		Matrix temp = new Matrix(m_rows, m_cols);
		Matrix tempex = new Matrix(m_rows, 2 * m_cols);
		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_cols; j++) {
				tempex.m_data[i * 2 * m_cols + j] = m_data[i * m_cols + j];
			}
		}
		for (int i = 0; i < m_rows; i++) {
			tempex.m_data[m_cols + i + i * 2 * m_cols] = 1;
		}

		// ///////////////////////////////
		/*
		 * for (int i = 0; i < m_rows; i++) { for (int j = 0; j < i; j++) {
		 * if(tempex.m_data[i*2*m_cols+j]!=0) { for (int k = 0; k < 2*m_cols;
		 * k++) {
		 * tempex.m_data[i*2*m_cols+k]-=tempex.m_data[i*2*m_cols+j]*tempex
		 * .m_data[j*m_cols*2+k]; }
		 * 
		 * } for (int k = 0; k < 2*m_cols; k++) {
		 * tempex.m_data[i*2*m_cols+k]/=tempex.m_data[i*m_cols*2+i]; } } }
		 * 
		 * tempex.showMatrix(); for (int i = m_rows-1; i >=0; i--) { for (int j
		 * = m_cols-1; j >=0; j--) { if(tempex.m_data[i*m_cols*2+j]!=0){ for
		 * (int k = i+1; k < 2*m_cols; k++) {
		 * tempex.m_data[i*2*m_cols+k]-=tempex.m_data[j*m_cols*2+k]; } }
		 * 
		 * } }
		 * 
		 * for (int i = 0; i < m_rows; i++) { for (int j = 0; j < m_cols; j++) {
		 * temp.m_data[i*m_cols+j]=tempex.m_data[i*2*m_cols+j+m_cols]; } }
		 * return temp;
		 */
		for (int i = 0; i < m_rows; i++) {

			for (int j = 0; j < i; j++)//
			{
				double r = tempex.m_data[i * 2 * m_rows + j];
				if (r != 0) {

					for (int k = 2 * m_rows - 1; k >= 0; k--) {
						tempex.m_data[i * 2 * m_cols + k] -= r
								* tempex.m_data[j * 2 * m_cols + k];
					}
				}

			}
			for (int m = 2 * m_rows - 1; m >= i; m--)//
			{
				tempex.m_data[i * 2 * m_rows + m] /= tempex.m_data[i * 2
						* m_rows + i];

			}
		}
		// tempex.showMatrix();
		for (int i = m_rows - 1; i >= 0; i--) {
			for (int j = m_rows - 1; j > i; j--) {
				double s = tempex.m_data[i * 2 * m_rows + j];
				if (s != 0) {
					for (int k = 2 * m_rows - 1; k > i; k--) {
						tempex.m_data[i * 2 * m_rows + k] -= s
								* tempex.m_data[j * 2 * m_rows + k];
					}
				}
			}
		}

		for (int i = 0; i < m_rows; i++) {
			for (int j = 0; j < m_rows; j++) {
				temp.m_data[i * m_rows + j] = tempex.m_data[i * m_rows * 2
						+ m_rows + j];
			}
		}

		return temp;
	}
}
