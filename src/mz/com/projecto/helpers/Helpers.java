package mz.com.projecto.helpers;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Helpers {
	
	public static void mensagemAlertaErro(String mensagemErro) {
		JOptionPane.showMessageDialog(null, mensagemErro, "ATENCAO", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mensagemAlertaInformacao(String mensagemInformacao) {
		JOptionPane.showMessageDialog(null, mensagemInformacao, "ATENCAO", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String formatarData(Date data) {
		return new SimpleDateFormat("dd/MM/yyyy").format(data);
	}
	
	public static String formatarDinheiro(double valor) {
		return new DecimalFormat("#,##0.00").format(valor);
	}

}
