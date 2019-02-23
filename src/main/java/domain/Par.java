
package domain;

public class Par<T1, T2> {

	private T1	fila;

	private T2	columna;


	public Par(final T1 fila, final T2 columna) {
		this.fila = fila;
		this.columna = columna;
	}

	public T1 getFila() {
		return this.fila;
	}

	public void setFila(final T1 fila) {
		this.fila = fila;
	}

	public T2 getColumna() {
		return this.columna;
	}

	public void setColumna(final T2 columna) {
		this.columna = columna;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Fila: " + this.getFila() + " - Columana: " + this.getColumna();
	}

}
